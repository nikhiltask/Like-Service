package com.likeservice.Service;

import com.likeservice.Enum.BloodGroup;
import com.likeservice.Enum.Gender;
import com.likeservice.Exception.LikesNotFoundException;
import com.likeservice.Feign.UserService;
import com.likeservice.Model.Like;
import com.likeservice.Model.LikeDto;
import com.likeservice.Model.User;
import com.likeservice.Repository.LikeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.AssertionsForClassTypes.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class LikeServiceTest {

    @InjectMocks
    LikeService likeService;

    @Mock
    LikeRepository likeRepository;

    @Mock
    UserService feignUser;

    @Test
    void deleteLikeID() throws ParseException {
        Like commentModel = createLikeModel();
        doNothing().when(this.likeRepository).deleteById("1");
        when(this.likeRepository.findById("1")).thenReturn(Optional.of(commentModel));
        assertThat(this.likeService.deleteLikeID("1"));
        assertThrows(LikesNotFoundException.class, () -> this.likeService.deleteLikeID("2"));
    }

    @Test
    void likesPage() throws ParseException {
        Like likeModel = createLikeModel();
        LikeDto likeDto = createLikeDTO();

        List<Like> list = new ArrayList<>();
        list.add(likeModel);

        PageImpl<Like> pageImpl = new PageImpl<Like>(list);
        Pageable firstPage = PageRequest.of(0, 2);
        when(this.likeRepository.findBypostorcommentID("12", firstPage)).thenReturn(list);
        assertEquals(1, this.likeService.likesPage("12", null, 2).size());
    }

    @Test
    void countLikes() throws ParseException {
        when(this.likeRepository.findAll()).thenThrow(new LikesNotFoundException("An error occurred"));
        assertThrows(LikesNotFoundException.class, () -> this.likeService.countLikes("12"));
        verify(this.likeRepository).findAll();
    }

    @Test
    void likeDetailsByID() throws ParseException {

        Like like = createLikeModel();
        LikeDto likeDto = createLikeDTO();
        when(this.likeRepository.findById("1")).thenReturn(Optional.of(like));
        assertThat(this.likeService.likeDetailsByID("1").getLikeID()).isEqualTo(likeDto.getLikeID());
        assertThrows(LikesNotFoundException.class, () -> this.likeService.likeDetailsByID("2"));
    }

    @Test
    void likeCreate() throws ParseException {
        LikeDto likeDTO = createLikeDTO();
        Like like = createLikeModel();
        when(this.likeRepository.findById("1")).thenReturn(Optional.of(like));
        Mockito.when(this.likeRepository.save(any(Like.class))).thenReturn(like);
        assertThat(this.likeService.likeCreate(like, "1").getLikeID()).isEqualTo(likeDTO.getLikeID());
    }
    private Like createLikeModel() {
        return new Like("1", "12", "123", null);
    }

    private LikeDto createLikeDTO() throws ParseException {
        return new LikeDto("1", "comment", createUser(), null);
    }

    private User createUser() throws ParseException {
        User user = new User();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date c = sdf.parse("2015-05-26");
        user.setUserID("123");
        user.setFirstName("Nikhil");
        user.setMiddleName("K");
        user.setLastName("Swami");
        user.setPhoneNumber("9638527410");
        user.setEmail("swami@Gmail.com");
        user.setDateOfBirth(c);
        user.setEmployeeNumber("852");
        user.setBloodGroup(BloodGroup.A_POS);
        user.setGender(Gender.MALE);
        return user;

    }
}