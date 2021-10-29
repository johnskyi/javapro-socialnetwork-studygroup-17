package ru.skillbox.socialnetwork.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skillbox.socialnetwork.data.dto.DeleteTagResponse;
import ru.skillbox.socialnetwork.data.dto.PostTagResponse;
import ru.skillbox.socialnetwork.data.dto.TagListResponse;
import ru.skillbox.socialnetwork.data.entity.Post;
import ru.skillbox.socialnetwork.data.entity.Post2Tag;
import ru.skillbox.socialnetwork.data.entity.Tag;
import ru.skillbox.socialnetwork.data.repository.Post2TagRepository;
import ru.skillbox.socialnetwork.data.repository.PostRepository;
import ru.skillbox.socialnetwork.data.repository.TagRepository;
import ru.skillbox.socialnetwork.exception.PostNotFoundException;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;
    private final Post2TagRepository post2TagRepository;
    private final PostRepository postRepository;

    public TagListResponse getPublicationsTags(long postId) {
        Post post = postRepository.findPostById(postId).orElseThrow(() -> new PostNotFoundException("Post is not exist. Tag list none"));
        return TagListResponse.builder()
                .data(post2TagRepository.findAllByPost(post).stream().map(Post2Tag::getTag).collect(Collectors.toList()))
                .build();
    }

    public PostTagResponse createNewTag(String tagName) {
        Tag tag = new Tag(tagName);
        tagRepository.save(tag);
        return PostTagResponse.builder()
                .timestamp(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
                .data(PostTagResponse.Data.builder().id(tag.getId()).tag(tag.getTag()).build())
                .build();
    }

    public DeleteTagResponse deleteTag(long tagId) {
        tagRepository.findById(tagId).ifPresent(tagRepository::delete);
        return DeleteTagResponse.builder()
                .timestamp(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
                .data(DeleteTagResponse.Data.builder().message("Ok").build())
                .build();
    }
}
