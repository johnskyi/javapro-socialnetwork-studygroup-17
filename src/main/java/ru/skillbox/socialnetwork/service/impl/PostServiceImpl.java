package ru.skillbox.socialnetwork.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.skillbox.socialnetwork.data.dto.AddPostRequest;
import ru.skillbox.socialnetwork.data.dto.PostResponse;
import ru.skillbox.socialnetwork.data.entity.Person;
import ru.skillbox.socialnetwork.data.entity.Post;
import ru.skillbox.socialnetwork.data.repository.PersonRepo;
import ru.skillbox.socialnetwork.data.repository.PostRepository;
import ru.skillbox.socialnetwork.exception.PersonNotAuthorized;
import ru.skillbox.socialnetwork.service.PostService;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PersonRepo personRepository;

    private final PostRepository postRepository;

    @Override
    public PostResponse addNewPost(AddPostRequest addPostRequest, Principal principal, Long publicationTimestamp) {

        Person person = findPerson(principal);

        Post post = new Post();
        post.setAuthor(person);
        post.setTime(publicationTimestamp == null ? LocalDateTime.now() : LocalDateTime.ofEpochSecond(publicationTimestamp, 0, ZoneOffset.UTC));
        post.setTitle(addPostRequest.getTitle());
        post.setTextHtml(addPostRequest.getText());
        post.setBlocked(false);
        postRepository.save(post);

        //TODO Tags from addPostRequest

        return createFullPostResponse(person, post, 0, null);
    }

    private PostResponse createFullPostResponse(Person person, Post post, int likesCount, ArrayList<PostResponse.Data.Comment> comments) {
        return PostResponse.builder()
                .error("string")
                .timestamp(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
                .data(PostResponse.Data.builder()
                        .id(post.getId())
                        .timestamp(post.getTime().toEpochSecond(ZoneOffset.UTC))
                        .author(PostResponse.Data.Author.builder()
                                .id(person.getId())
                                .firstName(person.getFirstName())
                                .lastName(person.getLastName())
                                .regDate(person.getRegTime().toEpochSecond(ZoneOffset.UTC))
                                .birthDate(person.getBirthTime().toEpochSecond(ZoneOffset.UTC))
                                .email(person.getEmail())
                                .phone(person.getPhone())
                                .photo(person.getPhoto())
                                .about(person.getAbout())
                                .town(person.getTown())
                                .country(person.getTown().getCountry())
                                .messagePermission(person.getMessagePermission())
                                .lastOnlineTime(person.getLastOnlineTime().toEpochSecond(ZoneOffset.UTC))
                                .isBlocked(person.isBlocked())
                                .build())
                        .title(post.getTitle())
                        .text(post.getTextHtml())
                        .isBlocked(post.isBlocked())
                        .likes(likesCount)
                        .comments(comments)
                        .build())
                .build();
    }

    private Person findPerson(Principal principal) {
        if (Objects.isNull(principal.getName())) {
            throw new PersonNotAuthorized("The Person not authorized");
        }
        return personRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
