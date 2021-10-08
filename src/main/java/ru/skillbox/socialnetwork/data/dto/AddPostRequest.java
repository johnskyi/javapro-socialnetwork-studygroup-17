package ru.skillbox.socialnetwork.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ru.skillbox.socialnetwork.annotations.NullOrPattern;

import java.util.List;

@Data
public class AddPostRequest {
    @NullOrPattern(pattern = "{0,30}",
            message = "Заголовок не может быть больше 30 символов")
    private String title;

    @JsonProperty("post_text")
    @NullOrPattern(pattern = "{3,}",
            message = "Текст поста должен содержать хотя бы 3 символа")
    private String text;

    private List<String> tags;

}

