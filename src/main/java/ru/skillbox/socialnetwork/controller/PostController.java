package ru.skillbox.socialnetwork.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socialnetwork.data.dto.AddPostRequest;
import ru.skillbox.socialnetwork.data.dto.Posts.*;
import ru.skillbox.socialnetwork.service.PostService;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/api/v1/users/{id}/wall")
    public ResponseEntity<AddNewPostResponse> postNewPost(
            @PathVariable Long id,
            @RequestParam(name = "publish_date", required = false) Long publishDate,
            @RequestBody @Valid AddPostRequest addPostRequest) {

        return ResponseEntity.ok(postService.addNewPost(id, addPostRequest, publishDate));
    }

    @PostMapping("/api/v1/post/{postId}/comments")
    public ResponseEntity<AddCommentResponse> addComment(
            @PathVariable Long postId,
            @RequestBody AddCommentRequest addCommentRequest,
            Principal principal) {

        return ResponseEntity.ok(postService.addComment(postId, addCommentRequest, principal));
    }

    @GetMapping("/api/v1/post/{postId}/comments")
    public ResponseEntity<CommentsResponse> requestComments(
            @PathVariable Long postId,
            @RequestParam(name = "offset", required = false, defaultValue = "0") int offset,
            @RequestParam(name = "itemPerPage", required = false, defaultValue = "20") int limit) {

        return ResponseEntity.ok(postService.commentsForPost(postId, offset, limit));

    }

    @GetMapping("/api/v1/users/{personId}/wall")
    public ResponseEntity<GetUserPostsResponse> getUserPosts(
            @PathVariable Long personId,
            @RequestParam(name = "offset", required = false, defaultValue = "0") int offset,
            @RequestParam(name = "itemPerPage", required = false, defaultValue = "10") int limit) {

        return ResponseEntity.ok(postService.getUserPosts(personId, offset, limit));
    }
}
