package ru.skillbox.socialnetwork.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import ru.skillbox.socialnetwork.data.dto.PlatformResponse;

public interface PlatformController {

    @GetMapping("/languages")
    ResponseEntity<PlatformResponse> getLanguages();

    @GetMapping("/countries")
    ResponseEntity<PlatformResponse> getCountries(String country, int offset, int itemPerPage);

    @GetMapping("/cities")
    ResponseEntity<PlatformResponse> getCities(Long countryId, String city, int offset, int itemPerPage);
}
