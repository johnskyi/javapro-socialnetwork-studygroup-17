package ru.skillbox.socialnetwork.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skillbox.socialnetwork.data.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
