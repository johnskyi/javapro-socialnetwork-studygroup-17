package ru.skillbox.socialnetwork.data.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.skillbox.socialnetwork.data.entity.PostComment;

import java.util.List;
import java.util.Optional;

public interface PostCommentsRepository extends JpaRepository<PostComment, Long> {
    List<PostComment> findAllByPostId(long id);

    Page<PostComment> findByPostId(long id, Pageable pageable);

    List<PostComment> findAllByParent_Id(long parentId);

    //Optional<PostComment> findByPersonIdAndCommentId(Long personId, Long itemId);
}
