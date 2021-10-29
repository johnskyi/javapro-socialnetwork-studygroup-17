package ru.skillbox.socialnetwork.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.skillbox.socialnetwork.data.dto.AddPostRequest;
import ru.skillbox.socialnetwork.data.dto.Posts.*;
import ru.skillbox.socialnetwork.data.entity.*;
import ru.skillbox.socialnetwork.data.repository.*;
import ru.skillbox.socialnetwork.exception.PostNotFoundException;
import ru.skillbox.socialnetwork.service.PostService;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PersonRepo personRepository;
    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final Post2TagRepository post2TagRepository;
    private final PostCommentsRepository postCommentsRepository;
    private final PostLikesRepository postLikesRepository;

    private final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);

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

    @Override
    public PostResponse getPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException("Post " + postId + " not found"));
        Person person = personRepository.findById(post.getAuthor().getId()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Integer likes = postLikesRepository.countByPost(post);
        List<CommentDto> commentDtoList = new ArrayList<>();
        for (PostComment postComment : postCommentsRepository.findAllByPostId(postId)) {
            commentDtoList.add(new CommentDto(postComment));
        }

        return createFullPostResponse(person, post, likes, commentDtoList);
    }

    @Override
    public GetUserPostsResponse getUserPosts(Long personId, int offset, int limit) {

        Person person = personRepository.findById(personId).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        List<PostDto> posts = new ArrayList<>();

        for (Post post : postRepository.findPostsByAuthor(person, PageRequest.of(offset/limit, limit))) {
            List<CommentDto> comments = new ArrayList<>();
            for (PostComment postComment : postCommentsRepository.findAllByPostId(post.getId())) {
                comments.add(new CommentDto(postComment));
            }
            posts.add(new PostDto(
                    post,
                    postLikesRepository.countByPost(post),
                    comments
            ));
        }

        return GetUserPostsResponse.builder()
                .error("string")
                .timestamp(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
                .total(posts.size())
                .offset(offset)
                .perPage(limit)
                .posts(posts)
                .build();
    }

    @Override
    public AddCommentResponse addComment(Long postId, AddCommentRequest addCommentRequest, Principal principal) {

        Person author = personRepository.findByEmail(principal.getName()).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        PostComment newComment = PostComment.builder()
                .time(LocalDateTime.now(ZoneOffset.UTC))
                .post(postRepository.getById(postId))
                .author(author)
                .text(addCommentRequest.getText())
                .isBlocked(false)
                .build();
        if(addCommentRequest.getParentId() != null) {
            newComment.setParent(postCommentsRepository.getById(addCommentRequest.getParentId()));
        }

        postCommentsRepository.save(newComment);

        return AddCommentResponse.builder()
                .error("string")
                .timestamp(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
                .data(AddCommentResponse.Data.builder()
                        .id(newComment.getId())
                        .parentId(newComment.getParent() != null ? newComment.getParent().getId() : null)
                        .postId(String.valueOf(newComment.getPost().getId()))
                        .timestamp(newComment.getTime().toEpochSecond(ZoneOffset.UTC))
                        .authorId(author.getId())
                        .commentText(newComment.getText())
                        .isBlocked(newComment.isBlocked())
                        .build())
                .build();
    }

    @Override
    public CommentsResponse commentsForPost(Long postId, int offset, int limit) {

        List<CommentDto> comments = new ArrayList<>();

        for (PostComment postComment : postCommentsRepository.findByPostId(postId, PageRequest.of(offset/limit, limit))) {
            comments.add(new CommentDto(postComment));
        }

        return CommentsResponse.builder()
                .error("string")
                .timestamp(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
                .total(comments.size())
                .offset(offset)
                .perPage(limit)
                .comments(comments)
                .build();
    }

    @Override
    public GetUserPostsResponse searchPosts(String text, String dateFrom, String dateTo, String author, String offset, String itemPerPage, String tags) {

        Page<Post> posts = postRepository.findAllBySearchFilter(
                text.isEmpty() ? null : text,
                dateFrom.isEmpty() ? LocalDateTime.now().minusYears(10) : LocalDateTime.parse(dateFrom.substring(0, dateFrom.indexOf(" "))),
                dateTo.isEmpty() ? LocalDateTime.now() : LocalDateTime.parse(dateTo.substring(0, dateTo.indexOf(" "))),
                author.isEmpty() ? null : author,
                Arrays.stream(tags.split(",")).map(tagRepository::findByTag).collect(Collectors.toList()),
                PageRequest.of(Integer.parseInt(offset), Integer.parseInt(itemPerPage)));

        return GetUserPostsResponse.builder()
                .error("string")
                .timestamp(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
                .total(posts.getTotalElements())
                .offset(Long.parseLong(offset))
                .perPage(Long.parseLong(itemPerPage))
                .posts(posts.map(post ->
                        PostDto.builder()
                                .id(post.getId())
                                .time(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
                                .author(new AuthorDto(post.getAuthor()))
                                .title(post.getTitle())
                                .text(post.getTextHtml())
                                .isBlocked(post.isBlocked())
                                .likes(post.getLikes().size())
                                .comments(post.getComments().stream()
                                        .map(CommentDto::new)
                                        .collect(Collectors.toList()))
                                .build()).toList())
                .build();
    }

    private PostResponse createFullPostResponse(Person person, Post post, int likesCount, List<CommentDto> comments) {
        return PostResponse.builder()
                .error("string")
                .timestamp(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
                .data(PostResponse.Data.builder()
                        .id(post.getId())
                        .timestamp(post.getTime().toEpochSecond(ZoneOffset.UTC))
                        .author(new AuthorDto(person))
                        .title(post.getTitle())
                        .text(post.getTextHtml())
                        .isBlocked(post.isBlocked())
                        .likes(likesCount)
                        .comments(comments)
                        .build())
                .build();
    }
}
