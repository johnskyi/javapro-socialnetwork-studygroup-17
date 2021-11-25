package ru.skillbox.socialnetwork.data.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.socialnetwork.data.entity.Person;
import ru.skillbox.socialnetwork.data.entity.Post;
import ru.skillbox.socialnetwork.data.entity.Tag;

import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;
import java.util.Collection;

@Repository
@Transactional
public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findPostsByAuthor(Person author, Pageable pageable);
    
    Optional<Post> findPostById(long id);
    
    @Query(value = "select p from Post p LEFT JOIN p.tags t WHERE " +
            "(:text is null or (p.textHtml LIKE %:text% OR p.title LIKE %:text% OR p.author.firstName LIKE %:text% OR p.author.lastName LIKE %:text%))" +
            "AND (COALESCE(:tags, null) is null or t in :tags)" +
            "AND (p.time > :date_from AND p.time < :date_to)" +
            "AND (:author is null or p.author.firstName LIKE %:author% or p.author.lastName LIKE %:author%)" +
            "AND (p.isBlocked = false) " +
            "GROUP BY p.id")
    Page<Post> findAllBySearchFilter(@Param("text") String text,
                                     @Param("date_from") LocalDateTime dateFrom,
                                     @Param("date_to") LocalDateTime dateTo,
                                     @Param("author") String author,
                                     @Param("tags") Collection<Tag> tags,
                                     Pageable pageable);

}
