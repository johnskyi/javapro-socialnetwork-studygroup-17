package ru.skillbox.socialnetwork.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.skillbox.socialnetwork.data.dto.likes.LikedResponse;
import ru.skillbox.socialnetwork.data.dto.likes.LikeUsersListResponse;
import ru.skillbox.socialnetwork.data.dto.likes.LikesCountResponse;
import ru.skillbox.socialnetwork.data.entity.*;
import ru.skillbox.socialnetwork.data.repository.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LikesService {

    private static final String TYPE_POST = "Post";
    private static final String TYPE_COMMENT = "Comment";

    private final PersonRepo personRepository;

    private final PostRepository postRepository;
    private final PostCommentsRepository postCommentsRepository;

    private final PostLikesRepository postLikesRepository;
    private final PostCommentsLikesRepository postCommentsLikesRepository;

    private final Logger logger = LoggerFactory.getLogger(LikesService.class);



    public LikedResponse isLikedByPerson(Long personId, Long itemId, String type) {

        boolean isLikedByPerson = false;

        if (type.equals(TYPE_POST)) {

            isLikedByPerson = postLikesRepository.findByPersonIdAndPostId(personId, itemId).isPresent();

        } else if (type.equals(TYPE_COMMENT)) {

            isLikedByPerson = postCommentsLikesRepository.findByPersonIdAndCommentId(personId, itemId).isPresent();

        }

        return LikedResponse.builder()
                .error("string")
                .timestamp(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
                .data(LikedResponse.Data.builder()
                        .likes(isLikedByPerson)
                        .build())
                .build();
    }

    public LikeUsersListResponse getLikeUsersList(Long itemId, String type) {

        List<Long> likesUsersList = new ArrayList<>();

        if (type.equals(TYPE_POST)) {

            likesUsersList = postLikesRepository.findAllByPostId(itemId).stream()
                    .map(postLike -> postLike.getPerson().getId()).collect(Collectors.toList());

        } else if (type.equals(TYPE_COMMENT)) {

            likesUsersList = postCommentsLikesRepository.findAllByCommentId(itemId).stream()
                    .map(postCommentLike -> postCommentLike.getPerson().getId()).collect(Collectors.toList());

        }

        return LikeUsersListResponse.builder()
                .error("string")
                .timestamp(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
                .data(LikeUsersListResponse.Data.builder()
                        .likes(likesUsersList.size())
                        .users(likesUsersList)
                        .build())
                .build();
    }

    public LikeUsersListResponse putLike(long itemId, String type, Principal principal) {
        Person author = personRepository.findByEmail(principal.getName())
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));


        if (type.equals(TYPE_POST)) {

            Post post = postRepository.getById(itemId);

            if(postLikesRepository.findByPersonAndPost(author, post).isEmpty()) {
                postLikesRepository.save(PostLike.builder()
                        .time(LocalDateTime.now(ZoneOffset.UTC))
                        .person(author)
                        .post(post)
                        .build());
            }

        } else if (type.equals(TYPE_COMMENT)) {

            PostComment postComment = postCommentsRepository.getById(itemId);

            if(postCommentsLikesRepository.findByPersonAndComment(author, postComment).isEmpty()) {
                postCommentsLikesRepository.save(PostCommentLike.builder()
                        .time(LocalDateTime.now(ZoneOffset.UTC))
                        .person(author)
                        .comment(postComment)
                        .build());
            }
        }
        return getLikeUsersList(itemId, type);
    }

    public LikesCountResponse deleteLike(Long itemId, String type, Principal principal) {

        Person author = personRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (type.equals(TYPE_POST)) {

            Post post = postRepository.getById(itemId);
            postLikesRepository.findByPersonAndPost(author, post).ifPresent(postLikesRepository::delete);

            return LikesCountResponse.builder()
                    .error("string")
                    .timestamp(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
                    .data(LikesCountResponse.Data.builder()
                            .likes(postLikesRepository.countByPost(post))
                            .build())
                    .build();

        } else if (type.equals(TYPE_COMMENT)) {

            PostComment postComment = postCommentsRepository.getById(itemId);
            postCommentsLikesRepository.findByPersonAndComment(author, postComment).ifPresent(postCommentsLikesRepository::delete);

            return LikesCountResponse.builder()
                    .error("string")
                    .timestamp(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
                    .data(LikesCountResponse.Data.builder()
                            .likes(postCommentsLikesRepository.countByComment(postComment))
                            .build())
                    .build();
        }
        return LikesCountResponse.builder()
                .error("wrong param type")
                .timestamp(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
                .data(LikesCountResponse.Data.builder()
                        .likes(0)
                        .build())
                .build();
    }
}
