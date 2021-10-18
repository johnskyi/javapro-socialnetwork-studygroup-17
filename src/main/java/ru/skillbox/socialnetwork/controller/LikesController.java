package ru.skillbox.socialnetwork.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socialnetwork.data.dto.Likes.LikedResponse;
import ru.skillbox.socialnetwork.data.dto.Likes.LikeUsersListResponse;
import ru.skillbox.socialnetwork.data.dto.Likes.LikesCountResponse;
import ru.skillbox.socialnetwork.data.dto.Likes.PutLikeRequest;
import ru.skillbox.socialnetwork.service.LikesService;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class LikesController {

    private final LikesService likesService;

    @GetMapping("/api/v1/liked")
    public ResponseEntity<LikedResponse> isLikedByPerson(
            @RequestParam(name = "user_id", required = false) Long personId,
            @RequestParam(name = "item_id") Long itemId,
            @RequestParam(name = "type") String type){

        return ResponseEntity.ok(likesService.isLikedByPerson(personId, itemId, type));

    }

    @GetMapping("/api/v1/likes")
    public ResponseEntity<LikeUsersListResponse> getLikeUsersList(
            @RequestParam(name = "item_id") Long itemId,
            @RequestParam(name = "type") String type){

        return ResponseEntity.ok(likesService.getLikeUsersList(itemId, type));

    }

    @PutMapping("/api/v1/likes")
    public ResponseEntity<LikeUsersListResponse> putLike(
            @RequestBody PutLikeRequest putLikeRequest,
            Principal principal){

        return ResponseEntity.ok(likesService.putLike(putLikeRequest.getItemId(), putLikeRequest.getType(), principal));

    }

    @DeleteMapping("/api/v1/likes")
    public ResponseEntity<LikesCountResponse> deleteLike(
            @RequestParam(name = "item_id") Long itemId,
            @RequestParam(name = "type") String type,
            Principal principal){

        return ResponseEntity.ok(likesService.deleteLike(itemId, type, principal));

    }
}
