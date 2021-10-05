package ru.skillbox.socialnetwork.service;

import ru.skillbox.socialnetwork.data.dto.PlatformResponse;

public interface PlatformService {
    PlatformResponse getLanguage();

    PlatformResponse getCountries(String country, Integer offset, Integer itemPerPage);

    PlatformResponse getCities(Long countryId, String city, int offset, int itemPerPage);
}
