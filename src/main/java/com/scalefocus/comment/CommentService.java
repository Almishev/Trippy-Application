package com.scalefocus.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class CommentService {
    private final CommentAccessor commentAccessor;

    @Autowired
    public CommentService(CommentAccessor commentAccessor, CommentMapper commentMapper) {

        this.commentAccessor = commentAccessor;
    }

    public List<Comment> getAllComments() {
        List<Comment> comments = new ArrayList<>();
            comments = commentAccessor.readAllComments();

        return comments;
    }

    public Comment getCommentById(int id) {
        return commentAccessor.readCommentById(id);
    }

    public Comment addComment(CommentRequest commentRequest) {
        Comment comment = new Comment(commentRequest.getContent(),commentRequest.getRating(),commentRequest.getEstId(),commentRequest.getUserId());
        comment = commentAccessor.addComment(comment);
        return comment;
    }

    public CommentDto editComment(CommentRequest commentRequest, int id) {

        Comment oldComment = getCommentById(id);
        commentAccessor.updateComment(new Comment(id, commentRequest.getContent(),commentRequest.getRating(),commentRequest.getEstId(),commentRequest.getUserId(),oldComment.getCreatedOn(),Timestamp.valueOf(LocalDateTime.now())));

        return new CommentDto(oldComment.getContent(), oldComment.getRating(), oldComment.getEstId(), oldComment.getUserId());
    }

    public CommentDto removeComment(int id) {


        Comment oldComment = getCommentById(id);
        commentAccessor.deleteComment(id);
        return new CommentDto(oldComment.getContent(), oldComment.getRating(), oldComment.getEstId(), oldComment.getUserId());
    }

    public int getUserIdFromTheComment(int commentId){
        return commentAccessor.getUserIdFromComments(commentId);
    }
}
