package ru.skillbox.socialnetwork.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skillbox.socialnetwork.data.dto.StorageResponse;

import java.security.Principal;

public interface StorageService {
    StorageResponse upload(String type, MultipartFile file, Principal principal);
}
