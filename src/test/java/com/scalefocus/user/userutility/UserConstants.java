package com.scalefocus.user.userutility;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public final class UserConstants {


    private UserConstants() {
        throw new IllegalStateException();
    }

    public static final int USER_ID = 1;
    public static final String USER_EMAIL = "test@abv.bg";
    public static final String FIRST_NAME = "First";
    public static final String LAST_NAME = "Second";
    public static final Timestamp CREATED_ON= Timestamp.valueOf(LocalDateTime.now());
    public static final Timestamp EDITED_ON = null;

}
