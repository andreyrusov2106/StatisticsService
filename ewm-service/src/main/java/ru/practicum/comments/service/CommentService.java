package ru.practicum.comments.service;


import ru.practicum.comments.dto.CommentDtoRequest;
import ru.practicum.comments.dto.CommentDtoResponse;

public interface CommentService {

    CommentDtoResponse createComment(CommentDtoRequest commentDtoRequest, Long userId, Long eventId);

    CommentDtoResponse updateComment(CommentDtoRequest commentDtoRequest, Long userId, Long eventId, Long commentId);

    void deleteComment(Long userId, Long eventId, Long commentId);
}
