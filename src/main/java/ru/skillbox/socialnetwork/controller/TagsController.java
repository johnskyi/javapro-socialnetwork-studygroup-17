package ru.skillbox.socialnetwork.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socialnetwork.data.dto.DeleteTagResponse;
import ru.skillbox.socialnetwork.data.dto.Likes.LikedResponse;
import ru.skillbox.socialnetwork.data.dto.PostTagResponse;
import ru.skillbox.socialnetwork.data.dto.TagListResponse;
import ru.skillbox.socialnetwork.service.TagService;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/tags/")
@Api(tags = "Действия с тэгами")
public class TagsController {

    private final TagService tagService;


    @GetMapping
    @ApiOperation(value="Получить тэги публикации")
    public ResponseEntity<TagListResponse> getTagsForPublication(
            @PathVariable Long postId
            /*
            ,
            @RequestParam(name = "offset", required = false, defaultValue = "0") int offset,
            @RequestParam(name = "itemPerPage", required = false, defaultValue = "20") int limit)

             */
    ){

        log.info("GET /api/v1/tags/ " + postId);
        return ResponseEntity.ok(tagService.getPublicationsTags(postId));
    }

    @PostMapping
    @ApiOperation(value="Создать тэг")
    public ResponseEntity<PostTagResponse> createTag(@RequestParam(name = "tag") String tagName){

        log.info("POST /api/v1/tags/ " + tagName);
        return ResponseEntity.ok(tagService.createNewTag(tagName));
    }

    @DeleteMapping
    @ApiOperation(value="Удалить тэг")
    public ResponseEntity<DeleteTagResponse> deleteTag(@RequestParam(name = "id") long tagId){
        log.info("DELETE /api/v1/tags/ " + tagId);
        return ResponseEntity.ok(tagService.deleteTag(tagId));
    }
}
