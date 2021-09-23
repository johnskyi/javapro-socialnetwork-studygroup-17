package ru.skillbox.socialnetwork.service.impl;

import org.springframework.stereotype.Service;
import ru.skillbox.socialnetwork.data.dto.LanguageResponse;
import ru.skillbox.socialnetwork.service.PlatformService;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
public class PlatformServiceImpl implements PlatformService {

    @Override
    public LanguageResponse getLanguage() {
        return LanguageResponse.builder()
                .error("string")
                .timestamp(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
                .total(1)
                .offset(0)
                .perPage(1)
                .data(List.of(
                        LanguageResponse.Datum.builder()
                                .id(1)
                                .title("Русский")
                                .build(),
                        LanguageResponse.Datum.builder()
                                .id(2)
                                .title("English")
                                .build()
                ))
                .build();
    }
}
