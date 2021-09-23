package ru.skillbox.socialnetwork.controller.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.socialnetwork.controller.PlatformController;
import ru.skillbox.socialnetwork.data.dto.LanguageResponse;
import ru.skillbox.socialnetwork.service.PlatformService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/platform")
public class PlatformControllerImpl implements PlatformController {

    private final PlatformService platformService;

    @Override
    @CrossOrigin(allowCredentials = "true", origins = "http://localhost:8080")
    @GetMapping("/languages")
    public ResponseEntity<LanguageResponse> getLanguages() {
        return ResponseEntity.ok(platformService.getLanguage());
    }
}
