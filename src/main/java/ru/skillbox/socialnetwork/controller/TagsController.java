package ru.skillbox.socialnetwork.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socialnetwork.data.dto.DeleteTagResponse;
import ru.skillbox.socialnetwork.data.dto.Likes.LikedResponse;
import ru.skillbox.socialnetwork.data.dto.PostTagResponse;
import ru.skillbox.socialnetwork.data.dto.TagListResponse;
import ru.skillbox.socialnetwork.service.TagService;

@RestController
@RequiredArgsConstructor

@RequestMapping("/api/v1/tags/")
public class TagsController {

    private final TagService tagService;


    @GetMapping
    public ResponseEntity<TagListResponse> getTagsForPublication(
            @PathVariable Long postId
            /*
            ,
            @RequestParam(name = "offset", required = false, defaultValue = "0") int offset,
            @RequestParam(name = "itemPerPage", required = false, defaultValue = "20") int limit)

             */
    ){

        return ResponseEntity.ok(tagService.getPublicationsTags(postId));
    }

    @PostMapping
    public ResponseEntity<PostTagResponse> createTag(@RequestParam(name = "tag") String tagName){
        return ResponseEntity.ok(tagService.createNewTag(tagName));
    }

    @DeleteMapping
    public ResponseEntity<DeleteTagResponse> deleteTag(@RequestParam(name = "id") long tagId){
        return ResponseEntity.ok(tagService.deleteTag(tagId));
    }
}
