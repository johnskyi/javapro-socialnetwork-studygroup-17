package ru.skillbox.socialnetwork.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.skillbox.socialnetwork.data.dto.AddPostRequest;
import ru.skillbox.socialnetwork.data.dto.PostResponse;
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

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PersonRepo personRepository;
    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final Post2TagRepository post2TagRepository;

    @Override
    public PostResponse addNewPost(Long authorId, AddPostRequest addPostRequest, Long publicationTimestamp) {

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
}
