package ru.practicum.comments.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.comments.dto.CommentDtoRequest;
import ru.practicum.comments.dto.CommentDtoResponse;
import ru.practicum.comments.mapper.CommentMapper;
import ru.practicum.comments.model.Comment;
import ru.practicum.comments.repository.CommentRepository;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.exceptions.ResourceNotFoundException;
import ru.practicum.user.repository.UserRepository;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    private final CommentRepository commentRepository;


    @Override
    public CommentDtoResponse createComment(CommentDtoRequest commentDtoRequest, Long userId, Long itemId) {
        Comment newComment = CommentMapper.toComment(new Comment(), commentDtoRequest);
        newComment.setCreatedOn(LocalDateTime.now());
        var owner = userRepository.findById(userId);
        if (owner.isEmpty()) {
            throw new ResourceNotFoundException(String.format("User with id=%d not found", userId));
        } else {
            newComment.setAuthor(owner.get());
        }
        var event = eventRepository.findById(itemId);
        if (event.isEmpty()) {
            throw new ResourceNotFoundException(String.format("Item with id=%d not found", userId));
        }
        Comment createdComment = commentRepository.save(newComment);
        event.get().addComment(createdComment);
        eventRepository.save(event.get());
        log.info("Comment created" + createdComment);
        return CommentMapper.toCommentDto(createdComment);
    }


    @Override
    public CommentDtoResponse updateComment(CommentDtoRequest commentDtoRequest, Long userId, Long itemId, Long commentId) {
        var owner = userRepository.findById(userId);
        if (owner.isEmpty()) {
            throw new ResourceNotFoundException(String.format("User with id=%d not found", userId));
        }
        var event = eventRepository.findById(itemId);
        if (event.isEmpty()) {
            throw new ResourceNotFoundException(String.format("Item with id=%d not found", userId));
        }
        var commentOptional = event.get().getComments().stream()
                .filter(comment -> (comment.getId().equals(commentId) && comment.getAuthor().getId().equals(userId))).findFirst();
        if (commentOptional.isEmpty()) {
            throw new ResourceNotFoundException(String.format("Comment with id=%d not found", userId));
        }
        var comment = commentOptional.get();
        CommentMapper.toComment(comment, commentDtoRequest);

        Comment updatedComment = commentRepository.save(comment);
        log.info("Comment created" + updatedComment);

        return CommentMapper.toCommentDto(updatedComment);
    }

    @Override
    public void deleteComment(Long userId, Long eventId, Long commentId) {
        var owner = userRepository.findById(userId);
        if (owner.isEmpty()) {
            throw new ResourceNotFoundException(String.format("User with id=%d not found", userId));
        }
        var event = eventRepository.findById(eventId);
        if (event.isEmpty()) {
            throw new ResourceNotFoundException(String.format("Event with id=%d not found", eventId));
        }
        var commentOptional = event.get().getComments().stream()
                .filter(comment -> (comment.getId().equals(commentId) && comment.getAuthor().getId().equals(userId))).findFirst();
        if (commentOptional.isEmpty()) {
            throw new ResourceNotFoundException(String.format("Comment with id=%d not found", commentId));
        }
        var comment = commentOptional.get();
        event.get().getComments().remove(comment);
        commentRepository.delete(comment);
        log.info(String.format("Comment with id=%d deleted", commentId));
    }

}
