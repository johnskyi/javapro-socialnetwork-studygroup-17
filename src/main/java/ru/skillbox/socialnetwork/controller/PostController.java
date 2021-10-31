package ru.skillbox.socialnetwork.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socialnetwork.data.dto.AddPostRequest;
import ru.skillbox.socialnetwork.data.dto.Posts.*;
import ru.skillbox.socialnetwork.service.PostService;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@Api(tags = "Работа с публикациями")
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;

    @PostMapping("/api/v1/users/{id}/wall")
    @ApiOperation(value="Добавление публикации на стену пользователя")
    public ResponseEntity<PostResponse> postNewPost(
            @PathVariable Long id,
            @RequestParam(name = "publish_date", required = false) Long publishDate,
            @RequestBody @Valid AddPostRequest addPostRequest) {

        log.info("POST /api/v1/users/{id}/wall  " + id);
        return ResponseEntity.ok(postService.addNewPost(id, addPostRequest, publishDate));
    }

    @PostMapping("/api/v1/post/{postId}/comments")
    @ApiOperation(value="Создание комментария к публикации")
    public ResponseEntity<AddCommentResponse> addComment(
            @PathVariable Long postId,
            @RequestBody AddCommentRequest addCommentRequest,
            Principal principal) {
        log.info("POST /api/v1/post/{postId}/comments " + principal.getName());

        return ResponseEntity.ok(postService.addComment(postId, addCommentRequest, principal));
    }

    @GetMapping("/api/v1/post/{postId}")
    @ApiOperation(value="Получение публикации по ID")
    public ResponseEntity<PostResponse> getPostById(@PathVariable Long postId) {

        log.info("GET /api/v1/post/{postId} " + postId);
        return ResponseEntity.ok(postService.getPost(postId));

    }

    @GetMapping("/api/v1/post/{postId}/comments")
    @ApiOperation(value="Получение комментариев на публикации")
    public ResponseEntity<CommentsResponse> requestComments(
            @PathVariable Long postId,
            @RequestParam(name = "offset", required = false, defaultValue = "0") int offset,
            @RequestParam(name = "itemPerPage", required = false, defaultValue = "20") int limit) {

        log.info("GET /api/v1/post/{postId}/comments " + postId);
        return ResponseEntity.ok(postService.commentsForPost(postId, offset, limit));

    }

    @GetMapping("/api/v1/users/{personId}/wall")
    @ApiOperation(value="Получение записей на стене пользователя")
    public ResponseEntity<GetUserPostsResponse> getUserPosts(
            @PathVariable Long personId,
            @RequestParam(name = "offset", required = false, defaultValue = "0") int offset,
            @RequestParam(name = "itemPerPage", required = false, defaultValue = "10") int limit) {

        log.info("GET /api/v1/users/{personId}/wall " + personId);
        return ResponseEntity.ok(postService.getUserPosts(personId, offset, limit));
    }

    @GetMapping("/api/v1/post")
    @ApiOperation(value="Поиск публикации")
    public ResponseEntity<GetUserPostsResponse> searchPosts(@RequestParam(value = "text", defaultValue = "") String text,
                                                            @RequestParam(value = "date_from", defaultValue = "") String dateFrom,
                                                            @RequestParam(value = "date_to", defaultValue = "") String dateTo,
                                                            @RequestParam(value = "author", defaultValue = "") String author,
                                                            @RequestParam(value = "offset", defaultValue = "0") String offset,
                                                            @RequestParam(value = "itemPerPage", defaultValue = "20") String itemPerPage){
        return ResponseEntity.ok(postService.searchPosts(text, dateFrom, dateTo, author, offset, itemPerPage));
    }

}
