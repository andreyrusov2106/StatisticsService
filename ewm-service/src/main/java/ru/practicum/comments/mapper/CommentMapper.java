package ru.practicum.comments.mapper;


import ru.practicum.comments.dto.CommentDtoResponse;
import ru.practicum.comments.model.Comment;

public class CommentMapper {
    public static CommentDtoResponse toCommentDto(Comment comment) {
        return CommentDtoResponse.builder()
                .id(comment.getId())
                .text(comment.getText())
                .authorName(comment.getAuthor().getName())
                .created(comment.getCreatedOn())
                .build();
    }

    public static Comment toComment(Comment comment, CommentDtoResponse commentDtoResponse) {
        if (commentDtoResponse.getId() != null) comment.setId(commentDtoResponse.getId());
        if (commentDtoResponse.getText() != null) comment.setText(commentDtoResponse.getText());
        if (commentDtoResponse.getCreated() != null) comment.setCreatedOn(commentDtoResponse.getCreated());
        return comment;
    }
}
