package ru.skillbox.socialnetwork.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skillbox.socialnetwork.data.entity.PostComment;

import java.util.List;

public interface PostCommentsRepository extends JpaRepository<PostComment, Long> {
    List<PostComment> findAllByPostId(long id);

}
