package ru.practicum.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.comment.dto.CommentResponseDto;
import ru.practicum.comment.dto.NewCommentDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/user/{userId}/events/{eventId}/comment/")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponseDto createComment(@PathVariable @Valid @Positive Long userId,
                                            @PathVariable @Valid @Positive Long eventId,
                                            @RequestBody @Validated NewCommentDto newCommentDto) {
        return commentService.createComment(userId, eventId, newCommentDto);
    }

    @GetMapping("/events/{eventId}/comments")
    public List<CommentResponseDto> getEventComments(@PathVariable @Valid @Positive Long eventId,
                                                     @RequestParam(defaultValue = "0") int from,
                                                     @RequestParam(defaultValue = "10") int size) {
        return commentService.getEventComments(eventId, from, size);
    }

    @GetMapping("/comment/{commentId}")
    public CommentResponseDto getCommentById(@PathVariable @Valid @Positive Long commentId) {
        return commentService.getCommentById(commentId);
    }

    @PatchMapping("/user/{userId}/comment/{commentId}")
    public CommentResponseDto updateComment(@PathVariable @Valid @Positive Long userId,
                                            @PathVariable @Valid @Positive Long commentId,
                                            @RequestBody @Validated NewCommentDto newCommentDto) {
        return commentService.updateComment(userId, commentId, newCommentDto);
    }

    @DeleteMapping("/user/{userId}/comment/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable @Valid @Positive Long userId,
                              @PathVariable @Valid @Positive Long commentId) {
        commentService.deleteComment(userId, commentId);
    }

    @PatchMapping("/admin/comment/{commentId}")
    public CommentResponseDto updateCommentStatusByAdmin(@PathVariable @Valid @Positive Long commentId,
                                                         @RequestParam boolean isConfirm) {
        return commentService.updateCommentStatusByAdmin(commentId, isConfirm);
    }
}