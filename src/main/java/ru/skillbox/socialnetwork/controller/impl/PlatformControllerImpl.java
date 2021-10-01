package ru.skillbox.socialnetwork.controller.impl;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.socialnetwork.controller.PlatformController;
import ru.skillbox.socialnetwork.data.dto.PlatformResponse;
import ru.skillbox.socialnetwork.service.PlatformService;
import ru.skillbox.socialnetwork.service.impl.PlatformServiceImpl;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/platform")
public class PlatformControllerImpl implements PlatformController {

    private Logger logger = LoggerFactory.getLogger(PlatformControllerImpl.class);
    private final PlatformService platformService;

    @Override
    public ResponseEntity<PlatformResponse> getLanguages() {
        return ResponseEntity.ok(platformService.getLanguage());
    }

    @Override
    public ResponseEntity<PlatformResponse> getCountries(String country, Integer offset, Integer itemPerPage) {
        logger.info("GET /api/v1/platform/countries");
        logger.info("country {}, offset {}, itemPerPage {}", country, offset, itemPerPage);
        return ResponseEntity.ok(platformService.getCountries(country, offset, itemPerPage));
    }

    @Override
    public ResponseEntity<PlatformResponse> getCities(Long countryId, String city, int offset, int itemPerPage) {
        return ResponseEntity.ok(platformService.getCities(countryId, city, offset, itemPerPage));
    }
}
