package ru.skillbox.socialnetwork.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.skillbox.socialnetwork.data.dto.AddPostRequest;
import ru.skillbox.socialnetwork.data.dto.Posts.*;
import ru.skillbox.socialnetwork.data.entity.Person;
import ru.skillbox.socialnetwork.data.entity.Post;
import ru.skillbox.socialnetwork.data.entity.Post2Tag;
import ru.skillbox.socialnetwork.data.entity.Tag;
import ru.skillbox.socialnetwork.data.repository.PersonRepo;
import ru.skillbox.socialnetwork.data.repository.Post2TagRepository;
import ru.skillbox.socialnetwork.data.repository.PostRepository;
import ru.skillbox.socialnetwork.data.repository.TagRepository;
import ru.skillbox.socialnetwork.service.PostService;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PersonRepo personRepository;
    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final Post2TagRepository post2TagRepository;

    private final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);

    @Override
    public AddNewPostResponse addNewPost(Long authorId, AddPostRequest addPostRequest, Long publicationTimestamp) {

        Person person = personRepository.findById(authorId).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Post post = new Post();
        post.setAuthor(person);
        post.setTime(publicationTimestamp == null ? LocalDateTime.now() : LocalDateTime.ofEpochSecond(publicationTimestamp, 0, ZoneOffset.UTC));
        post.setTitle(addPostRequest.getTitle());
        post.setTextHtml(addPostRequest.getText());
        post.setBlocked(false);
        postRepository.save(post);

        for (String tagString : addPostRequest.getTags()) {
            Tag tag = tagRepository.findByTag(tagString);
            if(tag == null){
                tag = new Tag(tagString);
                tagRepository.save(tag);
            }
            post2TagRepository.save(new Post2Tag(post, tag));
        }

        return createFullPostResponse(person, post, 0, null);
    }

    @Override
    public GetUserPostsResponse getUserPosts(Long personId, long offset, long limit) {

        Person person = personRepository.findById(personId).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        List<PostDto> posts = new ArrayList<>();

        posts.add(PostDto.builder()
                .id(1L)
                .timestamp(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
                .author(createAuthor(person))
                .title("Title1")
                .text("Text String")
                .isBlocked(false)
                .likes(11)
                .build());

        return GetUserPostsResponse.builder()
                .error("string")
                .timestamp(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
                .total(posts.size())
                .offset(offset)
                .perPage(limit)
                .posts(posts)
                .build();
    }

    private AddNewPostResponse createFullPostResponse(Person person, Post post, int likesCount, ArrayList<Comment> comments) {
        return AddNewPostResponse.builder()
                .error("string")
                .timestamp(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
                .data(AddNewPostResponse.Data.builder()
                        .id(post.getId())
                        .timestamp(post.getTime().toEpochSecond(ZoneOffset.UTC))
                        .author(createAuthor(person))
                        .title(post.getTitle())
                        .text(post.getTextHtml())
                        .isBlocked(post.isBlocked())
                        .likes(likesCount)
                        .comments(comments)
                        .build())
                .build();
    }

    private Author createAuthor(Person person){
        return Author.builder()
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
                .build();
    }
}
