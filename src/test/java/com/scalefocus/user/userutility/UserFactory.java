package com.scalefocus.user.userutility;

import com.scalefocus.user.User;
import com.scalefocus.user.UserDto;
import com.scalefocus.user.UserRequest;

public final class UserFactory {

    private UserFactory() {
        throw new IllegalStateException();
    }

    public static User getDefaultUser(){
        return new User(UserConstants.USER_ID, UserConstants.USER_EMAIL, UserConstants.FIRST_NAME, UserConstants.LAST_NAME, UserConstants.CREATED_ON, UserConstants.EDITED_ON);
    }

    public static UserDto getDefaultUserDto(){
        return new UserDto(UserConstants.USER_EMAIL, UserConstants.FIRST_NAME, UserConstants.LAST_NAME);
    }

    public static UserRequest getDefaultUserRequest(){
        return new UserRequest(UserConstants.USER_EMAIL, UserConstants.FIRST_NAME, UserConstants.LAST_NAME);
    }

}
