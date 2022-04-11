package com.likeservice.Controler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.likeservice.Enum.BloodGroup;
import com.likeservice.Enum.Gender;
import com.likeservice.Model.Like;
import com.likeservice.Model.LikeDto;
import com.likeservice.Model.User;
import com.likeservice.Service.LikeService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@WebMvcTest(LikeController.class)
class LikeControllerTest {

    @MockBean
    LikeService likeService;

    @Autowired
    MockMvc mockMvc;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private User createOneUser() throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date c= sdf.parse("2015-05-26");
        User user1 = new User();

        user1.setUserID("123");
        user1.setFirstName("FirstID");
        user1.setMiddleName("J");
        user1.setLastName("S");
        user1.setPhoneNumber("9090909090");
        user1.setEmail("nikhil@mail.com");
        user1.setDateOfBirth(c);
        user1.setEmployeeNumber("12345");
        user1.setBloodGroup(BloodGroup.A_POS);
        user1.setGender(Gender.MALE);
        user1.setAddress("Pune");
        return user1;
    }

    private Like createDummyLike(){
        return new Like("1","12","123",null);

    }
    private LikeDto createDummyLikeDTO() throws ParseException {
        return new LikeDto("1","12",createOneUser(),null);

    }
    private List<Like> createListLike(){
        List<Like> likeList =new ArrayList<>();
        Like like1= new Like("1","12","123",null);
        Like like2=new Like("2","12","123",null);
        likeList.add(like1);
        likeList.add(like2);
        return likeList;

    }
    private List<LikeDto> createListLikeDto() throws ParseException {
        List<LikeDto> likeList =new ArrayList<>();
        LikeDto like1= new LikeDto("1","12",createOneUser(),null);
        LikeDto like2=new LikeDto("2","12",createOneUser(),null);
        likeList.add(like1);
        likeList.add(like2);
        return likeList;

    }

    @Test
    void likeDetailsByID() throws Exception {
        Like like =createDummyLike();
        LikeDto likeDTO =createDummyLikeDTO();
        Mockito.when(likeService.likeDetailsByID("1")).thenReturn(likeDTO);

        mockMvc.perform(get("/postsOrComments/1/likes/1"))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$", Matchers.aMapWithSize(4)))
                .andExpect(jsonPath("$.likeID", Matchers.is("1")));

    }

    @Test
    void deleteLikeID() throws Exception {
        Mockito.when(likeService.deleteLikeID("1")).thenReturn("Deleted");
        mockMvc.perform(delete("/postsOrComments/1/likes/1"))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$", Matchers.is("Deleted")));
    }

    @Test
    void countLikes() throws Exception {

        Mockito.when(likeService.countLikes("1")).thenReturn(0);

        mockMvc.perform(get("/postsOrComments/1/likes/count"))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$", Matchers.is(0)));


    }

    @Test
    void likeCreate() throws Exception {
        Like like =createDummyLike();
        LikeDto likeDTO =createDummyLikeDTO();
        Mockito.when(likeService.likeCreate(like,"1")).thenReturn(likeDTO);

        mockMvc.perform(post("/postsOrComments/1/likes")
                        .content(asJsonString(like))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.likeID", Matchers.is("1")));

    }

    @Test
    void likesPage() throws Exception {
        List<Like> likes =createListLike();
        List<LikeDto> likeDTOs =createListLikeDto();
        Mockito.when(likeService.likesPage("1",1,2)).thenReturn(likeDTOs);

        mockMvc.perform(get("/postsOrComments/1/likes?page=1&pageSize=2"))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].likeID", Matchers.is("1")))
                .andExpect(jsonPath("$[1].likeID", Matchers.is("2")));
    }
}