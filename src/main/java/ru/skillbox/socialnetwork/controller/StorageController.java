package ru.skillbox.socialnetwork.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import ru.skillbox.socialnetwork.data.dto.StorageResponse;

import java.security.Principal;

public interface StorageController {

    @PostMapping
    ResponseEntity<StorageResponse> upload(String type, MultipartFile file, Principal principal);
}
