package ru.skillbox.socialnetwork.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.skillbox.socialnetwork.controller.StorageController;
import ru.skillbox.socialnetwork.data.dto.StorageResponse;
import ru.skillbox.socialnetwork.service.StorageService;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/storage")
public class StorageControllerImpl implements StorageController {

    private final StorageService storageService;

    @Override
    @PostMapping
    public ResponseEntity<StorageResponse> upload(String type, MultipartFile file, Principal principal) {
        return ResponseEntity.ok(storageService.upload(type, file, principal));
    }
}
