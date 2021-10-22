package ru.skillbox.socialnetwork.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skillbox.socialnetwork.data.entity.FriendshipStatus;
import ru.skillbox.socialnetwork.data.entity.FriendshipStatusType;

import java.util.Optional;

@Repository
public interface FriendshipStatusRepository extends JpaRepository<FriendshipStatus, Long> {
    Optional<FriendshipStatus> findByCode(FriendshipStatusType code);
}
