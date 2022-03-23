package com.likeservice.Service;

import com.likeservice.Exception.LikesNotFoundException;
import com.likeservice.Feign.UserService;
import com.likeservice.Model.Like;
import com.likeservice.Model.LikeDto;
import com.likeservice.Repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import java.time.LocalDateTime;

@Service
public class LikeService {
    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private UserService userFeign;

    public String deleteLikeID(String likeId){
        likeRepository.deleteById(likeId);
        return "Deleted  "+likeId+" successfully";
    }

public List<LikeDto> likesPage(String postOrCommentId, Integer page, Integer pageSize){
    Pageable firstPage = PageRequest.of(page, pageSize);
    List<Like> allLikes=likeRepository.findBypostorcommentID(postOrCommentId,firstPage);
    if(allLikes.isEmpty()){
        throw new LikesNotFoundException("Like ID Doesnot Exists");
    }
    List<LikeDto> likeDTOS = new ArrayList<>();
    for(Like like:allLikes){
        LikeDto likeDTO=new LikeDto(like.getLikeID(),like.getPostorcommentID(),
                userFeign.findByID(like.getLikedBy()),like.getCreatedAt());

        likeDTOS.add(likeDTO);
    }
    return  likeDTOS;

}
    public int countLikes(String postOrCommentId){
        List<Like> allData=likeRepository.findAll();
        int count=0;
        for(Like like:allData){
            if(like.getPostorcommentID().equals(postOrCommentId)){
                count++;
            }
        }
        return count;
    }

    public Like likeDetailsByID(String likeId){
        return likeRepository.findById(likeId).get();
}
    public Like likeCreate(Like like, String postOrCommentId){
        like.setPostorcommentID(postOrCommentId);
        like.setCreatedAt(LocalDateTime.now());
        return likeRepository.save(like);

    }
}
