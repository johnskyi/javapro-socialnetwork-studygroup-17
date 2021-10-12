package ru.skillbox.socialnetwork.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skillbox.socialnetwork.data.entity.Post2Tag;

public interface Post2TagRepository extends JpaRepository<Post2Tag, Long> {
}
