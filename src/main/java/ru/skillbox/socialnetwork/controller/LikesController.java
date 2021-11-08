package ru.skillbox.socialnetwork.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socialnetwork.data.dto.likes.LikedResponse;
import ru.skillbox.socialnetwork.data.dto.likes.LikeUsersListResponse;
import ru.skillbox.socialnetwork.data.dto.likes.LikesCountResponse;
import ru.skillbox.socialnetwork.data.dto.likes.PutLikeRequest;
import ru.skillbox.socialnetwork.service.LikesService;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@Api(tags = "Работа с Лайками")
@Slf4j
public class LikesController {

    private final LikesService likesService;

    @GetMapping("/api/v1/liked")
    @ApiOperation(value="Был ли поставлен лайк пользователем")
    public ResponseEntity<LikedResponse> isLikedByPerson(
            @RequestParam(name = "user_id", required = false) Long personId,
            @RequestParam(name = "item_id") Long itemId,
            @RequestParam(name = "type") String type){
        log.info("GET /api/v1/liked " + personId);
        return ResponseEntity.ok(likesService.isLikedByPerson(personId, itemId, type));

    }

    @GetMapping("/api/v1/likes")
    @ApiOperation(value="Получить список пользователей оставивших лайк")
    public ResponseEntity<LikeUsersListResponse> getLikeUsersList(
            @RequestParam(name = "item_id") Long itemId,
            @RequestParam(name = "type") String type){
        log.info("GET /api/v1/likes" + itemId);
        return ResponseEntity.ok(likesService.getLikeUsersList(itemId, type));

    }

    @PutMapping("/api/v1/likes")
    @ApiOperation(value="Поставить лайк")
    public ResponseEntity<LikeUsersListResponse> putLike(
            @RequestBody PutLikeRequest putLikeRequest,
            Principal principal){

        log.info("PUT /api/v1/like " + principal.getName());
        return ResponseEntity.ok(likesService.putLike(putLikeRequest.getItemId(), putLikeRequest.getType(), principal));

    }

    @DeleteMapping("/api/v1/likes")
    @ApiOperation(value="Убрать лайк")
    public ResponseEntity<LikesCountResponse> deleteLike(
            @RequestParam(name = "item_id") Long itemId,
            @RequestParam(name = "type") String type,
            Principal principal){
        log.info("DELETE /api/v1/likes " + principal.getName());

        return ResponseEntity.ok(likesService.deleteLike(itemId, type, principal));

    }
}
