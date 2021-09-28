package ru.skillbox.socialnetwork.controller.impl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.socialnetwork.controller.PlatformController;
import ru.skillbox.socialnetwork.data.dto.PlatformResponse;
import ru.skillbox.socialnetwork.service.PlatformService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/platform")
@Tag(name = "Работа с платформой")
public class PlatformControllerImpl implements PlatformController {

    private final PlatformService platformService;

    @Override
    @CrossOrigin(allowCredentials = "true", origins = {"http://localhost:8080", "http://127.0.0.1:8080", "http://45.134.255.54"})
    @GetMapping("/languages")
    @Operation(summary = "Получение языков")
    public ResponseEntity<PlatformResponse> getLanguages() {
        return ResponseEntity.ok(platformService.getLanguage());
    }

    @Override
    @GetMapping("/countries")
    @Operation(summary = "Получение стран")
    public ResponseEntity<PlatformResponse> getCountries(String country, int offset, int itemPerPage) {
        return ResponseEntity.ok(platformService.getCountries(country, offset, itemPerPage));
    }

    @Override
    @GetMapping("/cities")
    @Operation(summary = "Получение городов")
    public ResponseEntity<PlatformResponse> getCities(Long countryId, String city, int offset, int itemPerPage) {
        return ResponseEntity.ok(platformService.getCities(countryId, city, offset, itemPerPage));
    }
}
