package com.scalefocus.comment.utility;

import com.scalefocus.comment.Comment;
import com.scalefocus.comment.CommentDto;
import com.scalefocus.comment.CommentRequest;
import com.scalefocus.user.User;
import com.scalefocus.user.UserDto;
import com.scalefocus.user.UserRequest;
import com.scalefocus.user.userutility.UserConstants;

import static com.scalefocus.comment.utility.CommentConstants.*;

public final class CommentFactory {

    private CommentFactory() {
        throw new IllegalStateException();
    }

    public static Comment getDefaultComment(){
        return new Comment(COMMENT_ID,CONTENT,RATING,EST_ID,USER_ID,CREATED_ON,EDITED_ON);

    }

    public static CommentDto getDefaultCommentDto(){
        return new CommentDto(CONTENT,RATING,EST_ID, USER_ID);
    }


    public static CommentRequest getDefaultCommentRequest(){
        return new CommentRequest(CONTENT,RATING,EST_ID,USER_ID);
    }


}
