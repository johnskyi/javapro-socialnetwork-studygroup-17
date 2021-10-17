package ru.skillbox.socialnetwork.data.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.skillbox.socialnetwork.data.entity.Person;
import ru.skillbox.socialnetwork.data.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findPostsByAuthor(Person author, Pageable pageable);

}
