package ru.skillbox.socialnetwork.data.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.skillbox.socialnetwork.data.entity.Person;
import ru.skillbox.socialnetwork.data.entity.Post;

import java.time.LocalDateTime;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findPostsByAuthor(Person author, Pageable pageable);

    @Query(value = "select p from Post p JOIN p.author a " +
            "WHERE (:text is null or (p.textHtml LIKE %:text% OR p.title LIKE %:text% OR a.firstName LIKE %:text%))" +
            "AND (p.time > :date_from AND p.time < :date_to)" +
            "AND (:author is null or a.firstName = :author)" +
            "AND (p.isBlocked = true)")
    Page<Post> findAllBySearchFilter(@Param("text") String text,
                                     @Param("date_from") LocalDateTime dateFrom,
                                     @Param("date_to") LocalDateTime dateTo,
                                     @Param("author") String author,
                                     Pageable pageable);

}
