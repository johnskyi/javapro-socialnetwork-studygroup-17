package ru.skillbox.socialnetwork.controller;

import org.springframework.http.ResponseEntity;
import ru.skillbox.socialnetwork.data.dto.PlatformResponse;

public interface PlatformController {

    ResponseEntity<PlatformResponse> getLanguages ();
    ResponseEntity<PlatformResponse> getCountries (String country, int offset, int itemPerPage);
    ResponseEntity<PlatformResponse> getCities (Long countryId, String city, int offset, int itemPerPage);
}
