package ru.skillbox.socialnetwork.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.skillbox.socialnetwork.data.dto.FriendResponse;
import ru.skillbox.socialnetwork.data.dto.PersonResponse;
import ru.skillbox.socialnetwork.data.entity.*;
import ru.skillbox.socialnetwork.data.repository.*;
import ru.skillbox.socialnetwork.exception.CustomExceptionBadRequest;
import ru.skillbox.socialnetwork.exception.PersonNotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class FriendService {

    private final FriendshipRepository friendshipRepository;
    private final PersonRepo personRepository;
    private final PersonService personService;
    private final FriendshipStatusRepository friendshipStatusRepository;
    private final NotificationRepository notificationRepository;
    private final NotificationTypeRepository notificationTypeRepository;

    public FriendService(FriendshipRepository friendshipRepository, PersonRepo personRepository, PersonService personService, FriendshipStatusRepository friendshipStatusRepository, NotificationRepository notificationRepository, NotificationTypeRepository notificationTypeRepository) {
        this.friendshipRepository = friendshipRepository;
        this.personRepository = personRepository;
        this.personService = personService;
        this.friendshipStatusRepository = friendshipStatusRepository;
        this.notificationRepository = notificationRepository;
        this.notificationTypeRepository = notificationTypeRepository;
    }

    public FriendResponse getFriends(String name, Integer offset, Integer itemPerPage,
                                     FriendshipStatusType friendStatus) {
        Person currentPerson = personService.getCurrentUser();
        Pageable paging = PageRequest.of(offset / itemPerPage,
                itemPerPage,
                Sort.by(Sort.Direction.ASC, "personRequestFriend.lastName"));

        Page<Friendship> friendPage;
        if (name == null || name.isEmpty())
            friendPage = friendshipRepository.findByPersonReceiveFriendAndFriendshipStatus_Code(currentPerson, friendStatus, paging);
        else
            friendPage = friendshipRepository
                    .findByPersonReceiveFriendAndFriendshipStatus_CodeAndAndPersonRequestFriend_FirstName(currentPerson, name, friendStatus, paging);

        return new FriendResponse(
                friendPage.getTotalElements(),
                offset,
                itemPerPage,
                convertFriendshipPageToPersonList(friendPage));
    }

    public void deleteFriend(Long dstPersonId) {
        Person currentPerson = personService.getCurrentUser();
        Person dstPerson = personRepository.findById(dstPersonId).orElseThrow(() -> new PersonNotFoundException(dstPersonId));
        if (friendshipRepository.findByPersonReceiveFriendAndPersonRequestFriend(currentPerson, dstPerson).isEmpty())
            return;
        Friendship friendshipOut = friendshipRepository.findByPersonReceiveFriendAndPersonRequestFriend(currentPerson, dstPerson).get();
        if (friendshipOut.getFriendshipStatus().getCode().equals(FriendshipStatusType.REQUEST) || friendshipOut.getFriendshipStatus().getCode().equals(FriendshipStatusType.SUBSCRIBED)) {
            friendshipRepository.delete(friendshipOut);
        } else if (friendshipOut.getFriendshipStatus().getCode().equals(FriendshipStatusType.FRIEND)) {
            friendshipOut.setFriendshipStatus(new FriendshipStatus(LocalDateTime.now(),"",FriendshipStatusType.DECLINED));
            friendshipRepository.save(friendshipOut);
            if (friendshipRepository.findByPersonReceiveFriendAndPersonRequestFriend(currentPerson, dstPerson).isPresent()) {
                Friendship friendshipIn = friendshipRepository.findByPersonReceiveFriendAndPersonRequestFriend(currentPerson, dstPerson).get();
                friendshipIn.setFriendshipStatus(friendshipStatusRepository.findByCode(FriendshipStatusType.SUBSCRIBED).get());
                friendshipRepository.save(friendshipIn);
            }
        }
    }

    public FriendResponse getRecommendations(Integer offset, Integer itemPerPage) {
        Person currentPerson = personService.getCurrentUser();
        Pageable paging = PageRequest.of(offset / itemPerPage,
                itemPerPage,
                Sort.by(Sort.Direction.ASC, "personReceiveFriend.lastName"));

        List<Friendship> friendships = friendshipRepository.findByPersonReceiveFriendAndFriendshipStatus_Code(currentPerson, FriendshipStatusType.FRIEND);
        List<Person> friends =  friendships.stream().map(friendship -> friendship.getPersonRequestFriend()).collect(Collectors.toList());
        List<Friendship> friendships_known = friendshipRepository.findByPersonReceiveFriend(currentPerson);
        List<Person> known = friendships_known.stream().map(friendship -> friendship.getPersonRequestFriend()).collect(Collectors.toList());
        known.add(currentPerson);
        Page<Person> recommendedPersons = null;
        if (!friends.isEmpty()) {
            recommendedPersons = friendshipRepository.findNewRecs(friends, known, paging);
        }
        if (recommendedPersons == null || recommendedPersons.isEmpty()) {
            paging = PageRequest.of(offset / itemPerPage,
                    itemPerPage,
                    Sort.by(Sort.Direction.ASC, "lastName"));
            recommendedPersons = personRepository.findRandomRecs(known, paging);
        }

        return new FriendResponse(
                recommendedPersons.getTotalElements(),
                offset,
                itemPerPage,
                convertPersonPageToList(recommendedPersons));
    }

    public void addFriend(Long dstPersonId) {
        Person currentPerson = personService.getCurrentUser();
        if (currentPerson.getId() == dstPersonId) {
            throw new CustomExceptionBadRequest("Запрос на добавление себя");
        }
        Person dstPerson = personRepository.findById(dstPersonId).orElseThrow(() -> new PersonNotFoundException(dstPersonId));
        Friendship friendshipOut = friendshipRepository.findByPersonReceiveFriendAndPersonRequestFriend(currentPerson, dstPerson).orElse(new Friendship());
        if (friendshipOut.getFriendshipStatus() != null &&
                (//friendshipOut.getFriendshipStatus().getCode().equals(FriendshipStatusType.REQUEST) ||
                        friendshipOut.getFriendshipStatus().getCode().equals(FriendshipStatusType.FRIEND) ||
                        friendshipOut.getFriendshipStatus().getCode().equals(FriendshipStatusType.SUBSCRIBED))
        ) {
            throw new CustomExceptionBadRequest("Повторный запрос");
        }
        friendshipOut.setPersonRequestFriend(dstPerson);
        friendshipOut.setPersonReceiveFriend(currentPerson);
        if (friendshipRepository.findByPersonReceiveFriendAndPersonRequestFriend(currentPerson, dstPerson).isEmpty()) {
            friendshipOut.setFriendshipStatus(friendshipStatusRepository.findByCode(FriendshipStatusType.REQUEST).get());
        } else {
            Friendship friendshipIn = friendshipRepository.findByPersonReceiveFriendAndPersonRequestFriend(currentPerson, dstPerson).get();
            if (friendshipIn.getFriendshipStatus().getCode().equals(FriendshipStatusType.REQUEST) || friendshipIn.getFriendshipStatus().getCode().equals(FriendshipStatusType.SUBSCRIBED)) {
                friendshipIn.setFriendshipStatus(friendshipStatusRepository.findByCode(FriendshipStatusType.FRIEND).get());
                friendshipOut.setFriendshipStatus(friendshipStatusRepository.findByCode(FriendshipStatusType.FRIEND).get());
                friendshipRepository.save(friendshipIn);
            } else if (friendshipIn.getFriendshipStatus().getCode().equals(FriendshipStatusType.DECLINED)) {
                friendshipOut.setFriendshipStatus(friendshipStatusRepository.findByCode(FriendshipStatusType.SUBSCRIBED).get());
            } else if (friendshipIn.getFriendshipStatus().getCode().equals(FriendshipStatusType.BLOCKED)) {
                throw new CustomExceptionBadRequest("Friendship request prohibited by destination user");
            }
        }
        friendshipRepository.save(friendshipOut);
        notificationRepository.save(
                new Notification(
                    //notificationTypeRepository.findById(4L).get(),
                        NotificationType1.FRIEND_REQUEST,
                    LocalDateTime.now(),
                    dstPerson,
                    friendshipOut.getId(),
                    dstPerson.getEmail()
                )
        );
    }

    private List<PersonResponse.Data> convertPersonPageToList(Page<Person> page) {
        List<PersonResponse.Data> personResponseList = new ArrayList<>();
        page.forEach(person -> personResponseList.add(convertPersonToResponse(person)));
        return personResponseList;
    }

    private List<PersonResponse.Data> convertFriendshipPageToPersonList(Page<Friendship> friendships) {
        List<PersonResponse.Data> personResponseList = new ArrayList<>();
        friendships.forEach(friendship -> personResponseList.add(convertPersonToResponse(friendship.getPersonRequestFriend())));
        return personResponseList;
    }


    private PersonResponse.Data convertPersonToResponse(Person person) {
        return new PersonResponse.Data(person);
    }
}
