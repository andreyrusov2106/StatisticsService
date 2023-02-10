package ru.practicum.comments.service;


import ru.practicum.comments.dto.CommentDtoResponse;

public interface CommentService {

    CommentDtoResponse createComment(CommentDtoResponse commentDtoResponse, Long userId, Long eventId);

    CommentDtoResponse updateComment(CommentDtoResponse commentDtoResponse, Long userId, Long eventId, Long commentId);

    void deleteComment(Long userId, Long eventId, Long commentId);
}
