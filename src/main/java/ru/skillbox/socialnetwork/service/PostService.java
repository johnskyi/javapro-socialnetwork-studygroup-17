package ru.skillbox.socialnetwork.service;

import ru.skillbox.socialnetwork.data.dto.AddPostRequest;
import ru.skillbox.socialnetwork.data.dto.posts.*;

import java.security.Principal;

public interface PostService {
    PostResponse addNewPost(Long authorId, AddPostRequest addPostRequest, Long publicationTimestamp);

    GetUserPostsResponse getUserPosts(Long personId, int offset, int limit);

    AddCommentResponse addComment(Long postId, AddCommentRequest addCommentRequest, Principal principal);

    CommentsResponse commentsForPost(Long postId, int offset, int limit);

    PostResponse getPost(Long postId);

    GetUserPostsResponse searchPosts(String text, String dateFrom, String dateTo, String author, String offset, String itemPerPage, String tags);
}
