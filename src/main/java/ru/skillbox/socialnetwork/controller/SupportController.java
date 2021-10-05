package ru.skillbox.socialnetwork.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.socialnetwork.data.dto.SupportMessageRequest;
import ru.skillbox.socialnetwork.service.SupportService;

@RestController
public class SupportController {
    private final SupportService supportService;

    public SupportController(SupportService supportService) {
        this.supportService = supportService;
    }


    @PostMapping("/api/v1/support")
    public ResponseEntity<?> supportMessage(@RequestBody SupportMessageRequest request){
       return supportService.supportMessage(request);
    }

}
