package ru.practicum.comments.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.comments.dto.CommentDtoRequest;
import ru.practicum.comments.dto.CommentDtoResponse;
import ru.practicum.comments.service.CommentService;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/users/comments")
public class CommentsControllerPrivate {

    private final CommentService commentService;

    @Autowired
    public CommentsControllerPrivate(CommentService commentService) {
        this.commentService = commentService;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{commentId}")
    public void deleteCommentPrivate(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
    }

    @PatchMapping("/{commentId}")
    public CommentDtoResponse updateCommentPrivate(@Valid @RequestBody CommentDtoRequest commentDtoRequest,
                                                   @PathVariable Long commentId) {
        return commentService.updateComment(commentDtoRequest, commentId);
    }
}
