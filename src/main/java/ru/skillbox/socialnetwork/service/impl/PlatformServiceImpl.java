package ru.skillbox.socialnetwork.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.skillbox.socialnetwork.data.dto.PlatformResponse;
import ru.skillbox.socialnetwork.data.entity.Country;
import ru.skillbox.socialnetwork.data.entity.Town;
import ru.skillbox.socialnetwork.data.repository.CountryRepository;
import ru.skillbox.socialnetwork.data.repository.TownRepository;
import ru.skillbox.socialnetwork.service.PlatformService;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
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
        log.info("country {}, offset {}, itemPerPage {}", country, offset, itemPerPage);
        List<Country> pages = countryRepository.findAll();
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

    @Override
    public PlatformResponse getCities(Long countryId, String city, int offset, int itemPerPage) {
//        Pageable pageable = PageRequest.of(offset, itemPerPage);
//        Page<Town> pages = townRepository.findTownsByQuery(city.equals("") ? null : city,
//                countryId,
//                pageable);
        List<Town> pages = townRepository.findAllByCountry_Id(countryId, city.equals("") ? null : city);
        return PlatformResponse.builder()
                .error("string")
                .timestamp(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
                .offset(offset)
                .perPage(itemPerPage)
                .total(pages.size())
                .data(pages.stream()
                        .map(c -> PlatformResponse.Datum.builder()
                                .id(c.getId())
                                .title(c.getName())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
