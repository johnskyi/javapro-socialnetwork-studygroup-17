package ru.skillbox.socialnetwork.data.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.skillbox.socialnetwork.data.entity.PostComment;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PostCommentsRepository extends JpaRepository<PostComment, Long> {
    List<PostComment> findAllByPostId(long id);

    Page<PostComment> findByPostId(long id, Pageable pageable);

    List<PostComment> findAllByParent_Id(long parentId);

    @Query(value = "select p FROM PostComment p WHERE p.time >= :date_from and p.time <= :date_to")
    List<PostComment> findAllByTimeBetweenDates(@Param("date_from") LocalDateTime dateFrom,
                                                @Param("date_to") LocalDateTime dateTo);

    //Optional<PostComment> findByPersonIdAndCommentId(Long personId, Long itemId);
}
