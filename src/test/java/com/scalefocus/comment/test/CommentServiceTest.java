package com.scalefocus.comment.test;

import com.scalefocus.comment.*;
import com.scalefocus.town.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static com.scalefocus.comment.utility.CommentConstants.COMMENT_ID;
import static com.scalefocus.comment.utility.CommentConstants.CONTENT;
import static com.scalefocus.comment.utility.CommentFactory.getDefaultComment;
import static com.scalefocus.comment.utility.CommentFactory.getDefaultCommentRequest;
import static com.scalefocus.town.townutility.TownConstants.TOWN_ID;
import static com.scalefocus.town.townutility.TownConstants.TOWN_NAME;
import static com.scalefocus.town.townutility.TownFactory.getDefaultTown;
import static com.scalefocus.town.townutility.TownFactory.getDefaultTownRequest;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CommentServiceTest {

    @Mock
    private CommentAccessor accessor;
    @Mock
    private TownMapper mapper;

    @InjectMocks
    private CommentService service;

    @Test
    public void getAllCommentsSuccess(){
        List<Comment> comments = Collections.singletonList(getDefaultComment());
        when(accessor.readAllComments()).thenReturn(comments);
        List<Comment> result = service.getAllComments();
        assertEquals(result,comments);
    }

    @Test
    public void  saveComment(){
        Comment comment = getDefaultComment();
        CommentRequest commentRequest = getDefaultCommentRequest();

        assertEquals(accessor.addComment(comment),service.addComment(commentRequest));
    }

    @Test
    public void getCommendByIdTest(){
        Comment comment = getDefaultComment();
        when(accessor.readCommentById(comment.getCommentId())).thenReturn(comment);
        assertEquals(service.getCommentById(1),comment);

    }

    @Test
    public void deleteCommentNoExceptionsSuccess() {
        when(service.getCommentById(COMMENT_ID)).thenReturn(getDefaultComment());

        service.removeComment(1);

        verify(accessor, times(1)).readCommentById(COMMENT_ID);
        verify(accessor, times(1)).deleteComment(COMMENT_ID);
    }



}