package com.scalefocus.comment.utility;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public final class CommentConstants {

    private CommentConstants() {
        throw new IllegalStateException();
    }

    public static final int COMMENT_ID = 1;
    public static final String CONTENT = "Such a beautiful day.";
    public static final double RATING = 5;
    public static final int EST_ID = 1;
    public static final int USER_ID = 1;
    public static final Timestamp CREATED_ON= Timestamp.valueOf(LocalDateTime.now());
    public static final Timestamp EDITED_ON = null;

}
