package ru.skillbox.socialnetwork.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skillbox.socialnetwork.data.entity.*;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostCommentsLikesRepository extends JpaRepository<PostCommentLike, Long> {

    Integer countByComment(PostComment comment);

    Integer countByPerson(Person person);

    Optional<PostCommentLike> findByPersonIdAndCommentId(long personId, long commentId);

    Optional<PostCommentLike> findByPerson(Person person);

    Optional<PostCommentLike> findByPersonAndComment(Person person, PostComment comment);

    List<PostCommentLike>findAllByCommentId(long commentId);
}
