package edu.ada.service.library.model.mapper;

import edu.ada.service.library.model.entity.CommentEntity;
import edu.ada.service.library.model.requestAndResponse.CommentResponseDto;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;





public class CommentMapper {
    public static CommentResponseDto mapEntityToDto(CommentEntity comment) {
        var replies = comment.getReplies();

        return CommentResponseDto
                .builder()
                .id(comment.getId())
                .bookId(comment.getBook().getId())
                .comment_content(comment.getComment_content())
                .comment_author_name(comment.getComment_author_name())
                .replies(replies != null ? mapEntitiesToDtos(replies) : null)
                .build();
    }

    public static List<CommentResponseDto> mapEntitiesToDtos(Iterable<CommentEntity> comments) {
        return StreamSupport.stream(comments.spliterator(), false)
                .map(CommentMapper::mapEntityToDto)
                .collect(Collectors.toList());
    }
}
