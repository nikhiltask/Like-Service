package com.likeservice.Service;

import com.likeservice.Model.Like;
import com.likeservice.Repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeService {
    @Autowired
    private LikeRepository likeRepository;

    public Like likeDetailsByID(String likeId){
        return likeRepository.findById(likeId).get();
    }
}
