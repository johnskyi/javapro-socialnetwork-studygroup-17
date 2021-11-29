package ru.skillbox.socialnetwork.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.skillbox.socialnetwork.data.dto.DefaultResponseWithPagination;
import ru.skillbox.socialnetwork.data.dto.posts.PostDto;
import ru.skillbox.socialnetwork.data.entity.Post;
import ru.skillbox.socialnetwork.data.repository.PostRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedService {

    private final PostRepository postRepository;

    @Autowired
    public FeedService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public ResponseEntity<?> getFeed(String name, int offset, int itemPerPage) {
        Page<Post> allPosts = postRepository.findAll(getPagination(offset, itemPerPage));
        if (allPosts.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new DefaultResponseWithPagination());
        }
        List<PostDto> postDtoList = allPosts.stream()
                .map(this::toDto)
                .sorted(Comparator.comparing(PostDto::getTime).reversed())
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(
                getDefaultResponseWithPagination(postDtoList, offset, itemPerPage));

    }

    private Pageable getPagination(int offset, int itemPerPage) {
        return PageRequest.of(offset / itemPerPage, itemPerPage);
    }

    private PostDto toDto(Post post) {
        return new PostDto(post);
    }

    private DefaultResponseWithPagination getDefaultResponseWithPagination(
            List<PostDto> listPostsEntityResponses, int offset, int itemPerPage) {
        return new DefaultResponseWithPagination(
                listPostsEntityResponses.size(),
                offset,
                itemPerPage,
                listPostsEntityResponses);
    }
}
