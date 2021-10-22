package ru.skillbox.socialnetwork.data.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.skillbox.socialnetwork.data.entity.Friendship;
import ru.skillbox.socialnetwork.data.entity.FriendshipStatus;
import ru.skillbox.socialnetwork.data.entity.FriendshipStatusType;
import ru.skillbox.socialnetwork.data.entity.Person;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    Page<Friendship> findByPersonReceiveFriendAndFriendshipStatus_Code (Person dstPerson, FriendshipStatusType code, Pageable paging);

    //@Query(value = "select F.personReceiveFriend from #{#entityName} F where F.personRequestFriend = :srcPerson and F.friendshipStatus = :code")
    List<Person> findByPersonRequestFriendAndAndFriendshipStatus_Code(Person srcPerson, FriendshipStatusType code);

    @Query(value = "select F.personReceiveFriend from #{#entityName} F where F.personRequestFriend = :srcPerson")
    List<Person> findBySrcPerson(Person srcPerson);

    @Query(value = "select distinct F.personReceiveFriend from #{#entityName} F where F.personRequestFriend in :friends and F.friendshipStatus = 'FRIEND' and F.personReceiveFriend not in :known")
    Page<Person> findNewRecs(List<Person> friends, List<Person> known, Pageable paging);

    @Query(value = "select F from #{#entityName} F where F.personReceiveFriend = ?1 and F.friendshipStatus = ?3 and upper(F.personRequestFriend.firstName) like concat('%',upper(?2),'%') ")
    Page<Friendship> findByPersonReceiveFriendAndFriendshipStatus_CodeAndAndPersonRequestFriend_FirstName(Person dstPerson, String name, FriendshipStatusType code, Pageable paging);

    Optional<Friendship> findByPersonReceiveFriendAndPersonRequestFriend(Person dstPerson, Person srcPerson);
}
