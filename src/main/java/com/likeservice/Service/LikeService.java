package com.likeservice.Service;

import com.likeservice.Model.Like;
import com.likeservice.Repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LikeService {
    @Autowired
    private LikeRepository likeRepository;

    public Like likeCreate(Like like, String postOrCommentId){
        like.setPostOrCommentId(postOrCommentId);
        like.setCreatedAt(LocalDateTime.now());
        return likeRepository.save(like);

    }
}
