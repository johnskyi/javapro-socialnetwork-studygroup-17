package ru.skillbox.socialnetwork.controller.impl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.socialnetwork.controller.PlatformController;
import ru.skillbox.socialnetwork.data.dto.PlatformResponse;
import ru.skillbox.socialnetwork.service.PlatformService;

@RestController
@RequiredArgsConstructor
@Api(tags = "Работа с платформой")
@RequestMapping("/api/v1/platform")
public class PlatformControllerImpl implements PlatformController {

    private Logger logger = LoggerFactory.getLogger(PlatformControllerImpl.class);
    private final PlatformService platformService;

    @Override
    @ApiOperation("Получение списка языков")
    public ResponseEntity<PlatformResponse> getLanguages() {
        return ResponseEntity.ok(platformService.getLanguage());
    }

    @Override
    @ApiOperation("Получение списка стран")
    public ResponseEntity<PlatformResponse> getCountries(@RequestParam(value = "country", defaultValue = "") String country,
                                                         Integer offset,
                                                         Integer itemPerPage) {
        logger.info("GET /api/v1/platform/countries");
        logger.info("country {}, offset {}, itemPerPage {}", country, offset, itemPerPage);
        return ResponseEntity.ok(platformService.getCountries(country, offset, itemPerPage));
    }
    ///api/v1/platform/getAllCountriesWithTowns
    @Override
    @ApiOperation("Получение списка городов")
    public ResponseEntity<PlatformResponse> getCities(@RequestParam("country_id") Long countryId,
                                                      @RequestParam(value = "city", defaultValue = "") String city,
                                                      @RequestParam(value = "offset", defaultValue = "0") int offset,
                                                      @RequestParam(value = "itemPerPage", defaultValue = "20") int itemPerPage) {
        return ResponseEntity.ok(platformService.getCities(countryId, city, offset, itemPerPage));
    }
}
