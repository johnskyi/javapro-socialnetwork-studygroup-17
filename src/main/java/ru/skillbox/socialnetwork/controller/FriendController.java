package ru.skillbox.socialnetwork.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socialnetwork.data.dto.*;
import ru.skillbox.socialnetwork.data.entity.FriendshipStatusType;
import ru.skillbox.socialnetwork.service.FriendService;

@RestController
public class FriendController {

    private final FriendService friendService;

    @Autowired
    public FriendController(FriendService friendService) {
        this.friendService = friendService;
    }

    @GetMapping("/api/v1/friends")
    public ResponseEntity<FriendResponse> getFriends(
            @RequestParam(required = false) String name,
            @RequestParam(required = false, defaultValue = "0") Integer offset,
            @RequestParam(required = false, defaultValue = "20") Integer itemPerPage) {

        return ResponseEntity.ok(friendService.getFriends(name, offset, itemPerPage, FriendshipStatusType.FRIEND));
    }

    @DeleteMapping("/api/v1/friends/{id}")
    public ResponseEntity<ErrorTimeDataResponse> delete(@PathVariable Long id) {

        friendService.deleteFriend(id);
        return ResponseEntity.ok(new ErrorTimeDataResponse(new MessageResponse()));
    }

    @GetMapping("/api/v1/friends/request")
    public ResponseEntity<FriendResponse> getRequests(
            @RequestParam(required = false) String name,
            @RequestParam(required = false, defaultValue = "0") Integer offset,
            @RequestParam(required = false, defaultValue = "20") Integer itemPerPage) {

        return ResponseEntity.ok(friendService.getFriends(name, offset, itemPerPage, FriendshipStatusType.REQUEST));
    }

    @GetMapping("/api/v1/friends/recommendations")
    public ResponseEntity<FriendResponse> recommendations(
            @RequestParam(required = false, defaultValue = "0") Integer offset,
            @RequestParam(required = false, defaultValue = "20") Integer itemPerPage) {

        return ResponseEntity.ok(friendService.getRecommendations(offset, itemPerPage));
    }

}
