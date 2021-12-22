package ru.skillbox.socialnetwork.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skillbox.socialnetwork.data.entity.Person;
import ru.skillbox.socialnetwork.data.entity.Post;
import ru.skillbox.socialnetwork.data.entity.PostLike;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostLikesRepository extends JpaRepository<PostLike, Long> {

    Integer countByPost(Post post);

    Integer countByPerson(Person person);

    Optional<PostLike> findByPersonIdAndPostId(long personId, long postId);

    Optional<PostLike> findByPerson(Person person);

    Optional<PostLike> findByPersonAndPost(Person person, Post post);

    List<PostLike>findAllByPostId(long postId);
}
