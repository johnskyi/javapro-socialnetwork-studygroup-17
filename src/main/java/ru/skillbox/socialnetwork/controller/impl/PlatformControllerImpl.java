package ru.skillbox.socialnetwork.controller.impl;

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
public class PlatformControllerImpl implements PlatformController {

    private final PlatformService platformService;

    @Override
    @CrossOrigin(allowCredentials = "true", origins = {"http://localhost:8080", "http://127.0.0.1:8080", "http://45.134.255.54:5000/"})
    @GetMapping("/languages")
    public ResponseEntity<PlatformResponse> getLanguages() {
        return ResponseEntity.ok(platformService.getLanguage());
    }

    @Override
    @GetMapping("/countries")
    @CrossOrigin(allowCredentials = "true", origins = {"http://localhost:8080", "http://127.0.0.1:8080", "http://45.134.255.54:5000/"})
    public ResponseEntity<PlatformResponse> getCountries(String country, int offset, int itemPerPage) {
        return ResponseEntity.ok(platformService.getCountries(country, offset, itemPerPage));
    }

    @Override
    @GetMapping("/cities")
    @CrossOrigin(allowCredentials = "true", origins = {"http://localhost:8080", "http://127.0.0.1:8080", "http://45.134.255.54:5000/"})
    public ResponseEntity<PlatformResponse> getCities(Long countryId, String city, int offset, int itemPerPage) {
        return ResponseEntity.ok(platformService.getCities(countryId, city, offset, itemPerPage));
    }
}
