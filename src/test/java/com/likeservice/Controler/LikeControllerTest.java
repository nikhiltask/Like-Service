package com.likeservice.Controler;

import com.likeservice.Model.Like;
import com.likeservice.Service.LikeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@WebMvcTest(LikeController.class)
class LikeControllerTest {

    @Autowired
    private  LikeController likeController;

    @MockBean
    LikeService likeService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void deleteLikeID() {
    }

    @Test
    void likesPage() {
    }

    @Test
    void countLikes() {
    }

    @Test
    void likeDetailsByID() {
    }

    @Test
    void likeCreate() {



    }
}