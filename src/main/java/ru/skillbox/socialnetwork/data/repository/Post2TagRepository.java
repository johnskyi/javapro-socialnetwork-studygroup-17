package ru.skillbox.socialnetwork.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skillbox.socialnetwork.data.entity.Post;
import ru.skillbox.socialnetwork.data.entity.Post2Tag;

import java.util.List;

public interface Post2TagRepository extends JpaRepository<Post2Tag, Long> {
    List<Post2Tag> findAllByPost(Post post);
}
