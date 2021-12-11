package ru.skillbox.socialnetwork.data.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.skillbox.socialnetwork.data.entity.Friendship;
import ru.skillbox.socialnetwork.data.entity.FriendshipStatusType;
import ru.skillbox.socialnetwork.data.entity.Person;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    Page<Friendship> findByPersonReceiveFriendAndFriendshipStatus_Code (Person srcPerson, FriendshipStatusType code, Pageable paging);

    List<Friendship> findByPersonReceiveFriendAndFriendshipStatus_Code(Person srcPerson, FriendshipStatusType code);

    List<Friendship> findByPersonReceiveFriend(Person srcPerson);

    List<Friendship> findByPersonRequestFriend(Person dstPerson);

    @Query(value = "select distinct F.personReceiveFriend from #{#entityName} F where F.personRequestFriend in :friends and F.friendshipStatus = 3 and F.personReceiveFriend not in :known")
    Page<Person> findNewRecs(List<Person> friends, List<Person> known, Pageable paging);

    @Query(value = "select F from #{#entityName} F where F.personRequestFriend = ?1 and F.friendshipStatus = ?2 and upper(F.personRequestFriend.firstName) like concat('%',upper(?3),'%') ")
    Page<Friendship> findByPersonReceiveFriendAndFriendshipStatus_CodeAndPersonReceiveFriend_FirstName(Person dstPerson, FriendshipStatusType code, String name, Pageable paging);

    Optional<Friendship> findByPersonReceiveFriendAndPersonRequestFriend(Person dstPerson, Person srcPerson);
}
