package ru.practicum.comments.mapper;


import ru.practicum.comments.dto.CommentDtoRequest;
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

    public static Comment toComment(Comment comment, CommentDtoRequest commentDtoRequest) {
        if (commentDtoRequest.getText() != null) comment.setText(commentDtoRequest.getText());
        return comment;
    }
}
