package com.scalefocus.comment.test;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scalefocus.comment.Comment;
import com.scalefocus.comment.CommentController;
import com.scalefocus.comment.CommentService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static com.scalefocus.comment.utility.CommentConstants.*;
import static com.scalefocus.comment.utility.CommentConstants.USER_ID;
import static com.scalefocus.comment.utility.CommentFactory.*;
import static com.scalefocus.town.townutility.TownConstants.TOWN_ID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
public class CommentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CommentService service;

    @InjectMocks
    private CommentController controller;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
    }

    @Test
    public void getAllCommentsSingleUserSuccess() throws Exception {
        when(service.getAllComments()).thenReturn(Collections.<Comment>singletonList(getDefaultComment()));

        mockMvc.perform(get("/comments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].commentId").value(COMMENT_ID))
                .andExpect(jsonPath("$[0].content").value(CONTENT))
                .andExpect(jsonPath("$[0].rating").value(RATING))
                .andExpect(jsonPath("$[0].estId").value(EST_ID))
                .andExpect(jsonPath("$[0].userId").value(USER_ID));
    }
    @Test
    public void getAllCommentsEmptyListSuccess() throws Exception {
        when(service.getAllComments()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/comments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$[0]").doesNotExist());
    }

    @Test
    public void getCommentsByIdNoExceptionsSuccess() throws Exception {
        when(service.getCommentById(COMMENT_ID)).thenReturn(getDefaultComment());

        mockMvc.perform(get("/comments/" +COMMENT_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.commentId").value(COMMENT_ID))
                .andExpect(jsonPath("$.content").value(CONTENT))
                .andExpect(jsonPath("$.rating").value(RATING))
                .andExpect(jsonPath("$.estId").value(EST_ID))
                .andExpect(jsonPath("$.userId").value(USER_ID));

    }

    @Test
    public void createCommentNoExceptionsSuccess() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(getDefaultCommentRequest());

        when(service.addComment(any())).thenReturn(getDefaultComment());
        mockMvc.perform(post("/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/comments/1"));
    }

    @Test
    public void updateCommentNotAcceptable() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(getDefaultCommentRequest());

        when(service.editComment(any(), eq(COMMENT_ID))).thenReturn(getDefaultCommentDto());
        when(service.getUserIdFromTheComment(COMMENT_ID)).thenReturn(1);

        mockMvc.perform(put("/comments/" + COMMENT_ID)
                        .queryParam("returnOld", "true").queryParam("userId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotAcceptable())
                .andExpect(jsonPath("$.commentId").doesNotExist())
                .andExpect(jsonPath("$.content").doesNotExist());
    }

    @Test
    public void deleteCommentRequestedResponseNotAcceptable() throws Exception {
        when(service.removeComment(COMMENT_ID)).thenReturn(getDefaultCommentDto());
        when(service.getUserIdFromTheComment(COMMENT_ID)).thenReturn(1);

        mockMvc.perform(delete("/comments/" + TOWN_ID)
                        .queryParam("returnOld", "true").queryParam("userId","1"))
                .andExpect(status().isNotAcceptable());
    }


}

