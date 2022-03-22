package com.likeservice.Service;

import com.likeservice.Repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeService {
    @Autowired
    private LikeRepository likeRepository;

    public String deleteLikeID(String likeId){
        likeRepository.deleteById(likeId);
        return "Deleted  "+likeId+" successfully";
    }
}
