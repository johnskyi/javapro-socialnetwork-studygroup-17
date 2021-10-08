package ru.skillbox.socialnetwork.service;

import ru.skillbox.socialnetwork.data.dto.AddPostRequest;
import ru.skillbox.socialnetwork.data.dto.PostResponse;

import java.security.Principal;

public interface PostService {
    PostResponse addNewPost(AddPostRequest addPostRequest, Principal principal, Long publicationTimestamp);
}
