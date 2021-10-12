package ru.skillbox.socialnetwork.service;

import ru.skillbox.socialnetwork.data.dto.AddPostRequest;
import ru.skillbox.socialnetwork.data.dto.PostResponse;

public interface PostService {
    PostResponse addNewPost(Long authorId, AddPostRequest addPostRequest, Long publicationTimestamp);
}
