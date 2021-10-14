package ru.skillbox.socialnetwork.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skillbox.socialnetwork.data.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
