package ru.skillbox.socialnetwork.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "Работа с Лайками")
public class LikesController {

    private final LikesService likesService;

    @GetMapping("/api/v1/liked")
    @ApiOperation(value="Был ли поставлен лайк пользователем")
    public ResponseEntity<LikedResponse> isLikedByPerson(
            @RequestParam(name = "user_id", required = false) Long personId,
            @RequestParam(name = "item_id") Long itemId,
            @RequestParam(name = "type") String type){

        return ResponseEntity.ok(likesService.isLikedByPerson(personId, itemId, type));

    }

    @GetMapping("/api/v1/likes")
    @ApiOperation(value="Получить список пользователей оставивших лайк")
    public ResponseEntity<LikeUsersListResponse> getLikeUsersList(
            @RequestParam(name = "item_id") Long itemId,
            @RequestParam(name = "type") String type){

        return ResponseEntity.ok(likesService.getLikeUsersList(itemId, type));

    }

    @PutMapping("/api/v1/likes")
    @ApiOperation(value="Поставить лайк")
    public ResponseEntity<LikeUsersListResponse> putLike(
            @RequestBody PutLikeRequest putLikeRequest,
            Principal principal){

        return ResponseEntity.ok(likesService.putLike(putLikeRequest.getItemId(), putLikeRequest.getType(), principal));

    }

    @DeleteMapping("/api/v1/likes")
    @ApiOperation(value="Убрать лайк")
    public ResponseEntity<LikesCountResponse> deleteLike(
            @RequestParam(name = "item_id") Long itemId,
            @RequestParam(name = "type") String type,
            Principal principal){

        return ResponseEntity.ok(likesService.deleteLike(itemId, type, principal));

    }
}
