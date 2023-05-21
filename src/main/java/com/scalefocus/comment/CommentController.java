package com.scalefocus.comment;
import com.scalefocus.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class CommentController {


    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/comments")
    public ResponseEntity<List<Comment>> getAllComments() {
        List<Comment> comments = commentService.getAllComments();
        return ResponseEntity.ok(comments);
    }


    @GetMapping("/comments/{commentId}")
    public ResponseEntity<Comment> getCommentById(@PathVariable int commentId) {
        Comment comment = commentService.getCommentById(commentId);
        CommentDto commentDto = new CommentDto(comment.getContent(),comment.getRating(),comment.getEstId(),comment.getUserId());

        return ResponseEntity.ok(comment);
    }

    @PostMapping("/comments")
    public ResponseEntity<Comment> createComment(@RequestBody @Valid CommentRequest commentRequest) {
        Comment comment = commentService.addComment(commentRequest);

        URI location = UriComponentsBuilder.fromUriString("/comments/{commentId}")
                .buildAndExpand(comment.getCommentId())
                .toUri();
        return ResponseEntity.created(location).build();
    }


    @PutMapping("/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(
            @RequestBody @Valid CommentRequest commentRequest,@RequestParam int userId, @PathVariable int commentId,
            @RequestParam(required = false) boolean returnOld) {
        if (userId == commentService.getUserIdFromTheComment(commentId)) {
            CommentDto commentDto = commentService.editComment(commentRequest, commentId);
            return returnOld ? ResponseEntity.ok(commentDto) : ResponseEntity.noContent().build();
        }
        else{
            throw new NotFoundException("It's allowed only to owner of the comment");
        }

    }


    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<CommentDto> deleteComment(@PathVariable int commentId, @RequestParam int userId,
                                                      @RequestParam(required = false) boolean returnOld) {
        if (userId == commentService.getUserIdFromTheComment(commentId)) {
            CommentDto commentDto = commentService.removeComment(commentId);
            return returnOld ? ResponseEntity.ok(commentDto) : ResponseEntity.noContent().build();
        }else{
            throw new NotFoundException("It's allowed only to owner of the comment");
        }
    }

}
