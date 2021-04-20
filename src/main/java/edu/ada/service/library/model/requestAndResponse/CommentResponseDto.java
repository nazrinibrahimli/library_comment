package edu.ada.service.library.model.requestAndResponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentResponseDto {
    private String id;
    private String comment_author_name;
    private String comment_content;
    private Long bookId;
    private List<CommentResponseDto> replies;
}