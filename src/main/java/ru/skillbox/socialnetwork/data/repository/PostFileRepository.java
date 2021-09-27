package ru.skillbox.socialnetwork.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skillbox.socialnetwork.data.entity.File;

public interface PostFileRepository extends JpaRepository<File, Long> {
}
