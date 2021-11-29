package ru.skillbox.socialnetwork.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.socialnetwork.service.FeedService;

@RestController
@Api(tags = "Работа с аккаунтом")
@Slf4j
public class FeedController {

    @Autowired
    private final FeedService feedService;

    public FeedController(FeedService feedService) {
        this.feedService = feedService;
    }

    @CrossOrigin(allowCredentials = "true", origins = "http://127.0.0.1:8080")
    @GetMapping("/api/v1/feeds")
    @ApiOperation(value = "Работа с лентой новостей")
    public ResponseEntity<?> feed(@RequestParam(name = "name", required = false) String name,
                                  @RequestParam(name = "offset", defaultValue = "0") int offset,
                                  @RequestParam(name = "itemPerPage", defaultValue = "20") int itemPerPage) {
        log.info("GET /api/v1/feeds " + name);
        return feedService.getFeed(name, offset, itemPerPage);
    }
}
