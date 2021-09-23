package ru.skillbox.socialnetwork.controller;

import org.springframework.http.ResponseEntity;
import ru.skillbox.socialnetwork.data.dto.LanguageResponse;

public interface PlatformController {

    ResponseEntity<LanguageResponse> getLanguages ();
}
