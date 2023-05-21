package com.scalefocus.user.usertest;

import com.scalefocus.user.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.scalefocus.user.userutility.UserConstants.USER_EMAIL;
import static com.scalefocus.user.userutility.UserConstants.USER_ID;

import java.util.Collections;
import java.util.List;

import static com.scalefocus.user.userutility.UserFactory.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserAccessor accessor;

    @InjectMocks
    private UserService service;

    @Test
    public void addAUserSuccess(){
        User user = getDefaultUser();
        UserRequest userRequest = getDefaultUserRequest();
        when(accessor.isEmailExist(USER_EMAIL)).thenReturn(false);
        assertEquals(accessor.addUser(user),service.addUser(userRequest));
    }

    @Test
    public void getAllUsersSuccess(){
        List<User> users = Collections.singletonList(getDefaultUser());

        when(accessor.readAllUsers()).thenReturn(users);

        List<User> result = service.getAllUsers();
        assertEquals(result,users);
    }

    @Test
    public void getUserByIdSuccess(){

        User user = getDefaultUser();
        when(accessor.readUserById(user.getId())).thenReturn(user);
        assertEquals(service.getUserById(1),user);

    }


    @Test
    public void editUserNoExceptionsSuccess() {
       User user = getDefaultUser();
       when(accessor.readUserById(user.getId())).thenReturn(user);
       service.updateUser(user.getId(),user.getEmail(),"Nikofor","Columb");
       assertEquals("Nikofor",user.getfName());

    }

    @Test
    public void deleteUserNoExceptionsSuccess() {
        when(service.getUserById(USER_ID)).thenReturn(getDefaultUser());

        service.removeUser(USER_ID);

        verify(accessor, times(1)).readUserById(USER_ID);
        verify(accessor, times(1)).deleteUser(USER_ID);
    }

}

