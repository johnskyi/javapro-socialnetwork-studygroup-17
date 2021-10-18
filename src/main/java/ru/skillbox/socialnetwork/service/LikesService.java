package ru.skillbox.socialnetwork.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.skillbox.socialnetwork.data.dto.Likes.LikedResponse;
import ru.skillbox.socialnetwork.data.dto.Likes.LikeUsersListResponse;
import ru.skillbox.socialnetwork.data.dto.Likes.LikesCountResponse;
import ru.skillbox.socialnetwork.data.entity.Person;
import ru.skillbox.socialnetwork.data.entity.Post;
import ru.skillbox.socialnetwork.data.entity.PostLike;
import ru.skillbox.socialnetwork.data.repository.*;

import javax.swing.text.html.Option;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LikesService {

    private final PersonRepo personRepository;
    private final PostLikesRepository postLikesRepository;
    private final PostRepository postRepository;

    private final Logger logger = LoggerFactory.getLogger(LikesService.class);

    public LikedResponse isLikedByPerson(Long personId, Long itemId, String type) {
        return LikedResponse.builder()
                .error("string")
                .timestamp(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
                .data(LikedResponse.Data.builder()
                        .likes(postLikesRepository.findByPersonIdAndPostId(personId, itemId).isPresent())
                        .build())
                .build();
    }

    public LikeUsersListResponse getLikeUsersList(Long itemId, String type) {

        List<Long> likesUsersList = postLikesRepository.findAllByPostId(itemId).stream()
                .map(postLike -> postLike.getPerson().getId()).collect(Collectors.toList());

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
        Post post = postRepository.getById(itemId);

        if(postLikesRepository.findByPersonAndPost(author, post).isEmpty()) {
            postLikesRepository.save(PostLike.builder()
                    .time(LocalDateTime.now(ZoneOffset.UTC))
                    .person(author)
                    .post(post)
                    .build());
        }

        return getLikeUsersList(itemId, type);
    }

    public LikesCountResponse deleteLike(Long itemId, String type, Principal principal) {
        Post post = postRepository.getById(itemId);

        Person author = personRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        postLikesRepository.findByPersonAndPost(author, post).ifPresent(postLikesRepository::delete);

        return LikesCountResponse.builder()
                .error("string")
                .timestamp(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
                .data(LikesCountResponse.Data.builder()
                        .likes(postLikesRepository.countByPost(post))
                        .build())
                .build();
    }
}
