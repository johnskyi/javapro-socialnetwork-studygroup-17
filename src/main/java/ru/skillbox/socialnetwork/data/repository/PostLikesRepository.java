package ru.skillbox.socialnetwork.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skillbox.socialnetwork.data.entity.Person;
import ru.skillbox.socialnetwork.data.entity.Post;
import ru.skillbox.socialnetwork.data.entity.PostLike;

public interface PostLikesRepository extends JpaRepository<PostLike, Long> {

    Integer countByPost(Post post);

    Integer countByPerson(Person person);
}
