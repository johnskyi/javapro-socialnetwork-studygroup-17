package ru.skillbox.socialnetwork.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skillbox.socialnetwork.data.entity.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Tag findByTag(String tag);
}
