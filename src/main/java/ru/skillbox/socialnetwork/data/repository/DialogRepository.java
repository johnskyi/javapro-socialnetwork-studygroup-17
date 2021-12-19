package ru.skillbox.socialnetwork.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.skillbox.socialnetwork.data.entity.Dialog;
import ru.skillbox.socialnetwork.data.entity.Person;

import java.util.List;
import java.util.Optional;

@Repository
public interface DialogRepository extends JpaRepository<Dialog, Long> {

    @Query("select d from Dialog d where d.author = :author and d.recipient = :recipient")
    Optional<Dialog> findByParticipants(@Param("author") Person author, @Param("recipient") Person recipient);

    @Query("select d from Dialog d where d.author = :author or d.recipient = :author")
    List<Dialog> findAllUserDialogs(@Param("author") Person author);
}
