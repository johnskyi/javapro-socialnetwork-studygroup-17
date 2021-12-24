package ru.skillbox.socialnetwork.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skillbox.socialnetwork.data.entity.Dialog;
import ru.skillbox.socialnetwork.data.entity.Person;

import java.util.List;
import java.util.Optional;

@Repository
public interface DialogRepository extends JpaRepository<Dialog, Long> {


    Optional<Dialog> findByAuthorAndRecipient( Person author, Person recipient);

    List<Dialog> findAllByAuthorOrRecipient( Person author, Person recipient);
}
