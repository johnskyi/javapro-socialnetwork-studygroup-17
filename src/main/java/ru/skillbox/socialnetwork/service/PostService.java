package ru.skillbox.socialnetwork.service;

import ru.skillbox.socialnetwork.data.dto.AddPostRequest;
import ru.skillbox.socialnetwork.data.dto.Posts.AddNewPostResponse;
import ru.skillbox.socialnetwork.data.dto.Posts.GetUserPostsResponse;

public interface PostService {
    AddNewPostResponse addNewPost(Long authorId, AddPostRequest addPostRequest, Long publicationTimestamp);

    GetUserPostsResponse getUserPosts(Long personId, long offset, long limit);
}
