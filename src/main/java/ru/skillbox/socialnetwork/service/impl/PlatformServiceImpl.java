package ru.skillbox.socialnetwork.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.skillbox.socialnetwork.data.dto.PlatformResponse;
import ru.skillbox.socialnetwork.data.entity.Country;
import ru.skillbox.socialnetwork.data.entity.Platform;
import ru.skillbox.socialnetwork.data.entity.Town;
import ru.skillbox.socialnetwork.data.repository.CountryRepository;
import ru.skillbox.socialnetwork.data.repository.TownRepository;
import ru.skillbox.socialnetwork.service.PlatformService;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlatformServiceImpl implements PlatformService {

    private Logger logger = LoggerFactory.getLogger(PlatformServiceImpl.class);

    private final CountryRepository countryRepository;
    private final TownRepository townRepository;

    @Override
    public PlatformResponse getLanguage() {
        return PlatformResponse.builder()
                .error("string")
                .timestamp(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
                .total(1)
                .offset(0)
                .perPage(1)
                .data(List.of(
                        PlatformResponse.Datum.builder()
                                .id(1L)
                                .title("Русский")
                                .build(),
                        PlatformResponse.Datum.builder()
                                .id(2L)
                                .title("English")
                                .build()
                ))
                .build();
    }

    @Override
    public PlatformResponse getCountries(String country, Integer offset, Integer itemPerPage) {
        logger.info("country {}, offset {}, itemPerPage {}", country, offset, itemPerPage);
  //      Pageable pageable = PageRequest.of(offset, itemPerPage);
 //       Page<Country> pages = countryRepository.findCountriesByQuery(country, pageable);
        List<Country> pages = countryRepository.findAll();
        return createPlatformResponse(pages);
    }

    @Override
    public PlatformResponse getCities(Long countryId, String city, int offset, int itemPerPage) {
        Pageable pageable = PageRequest.of(offset, itemPerPage);
        Page<Town> pages = townRepository.findTownsByQuery(city, countryId, pageable);
        return createPlatformResponse(pages, offset, itemPerPage);
    }

    private PlatformResponse createPlatformResponse(List<Country> pages) {
        return PlatformResponse.builder()
                .error("string")
                .timestamp(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
                .total(0)
                .offset(0)
                .perPage(0)
                .data(pages.stream()
                        .map(c -> PlatformResponse.Datum.builder()
                                .id(c.getId())
                                .title(c.getName())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    private PlatformResponse createPlatformResponse(Page<? extends Platform> pages, int offset, int itemPerPage) {
        return PlatformResponse.builder()
                .error("string")
                .timestamp(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
                .total(pages.getTotalPages())
                .offset(offset)
                .perPage(itemPerPage)
                .data(pages.stream()
                        .map(c -> PlatformResponse.Datum.builder()
                                .id(c.getId())
                                .title(c.getName())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
