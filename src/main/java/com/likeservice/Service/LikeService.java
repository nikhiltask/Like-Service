package com.likeservice.Service;

import com.likeservice.Model.Like;
import com.likeservice.Repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import java.time.LocalDateTime;

@Service
public class LikeService {
    @Autowired
    private LikeRepository likeRepository;

    public String deleteLikeID(String likeId){
        likeRepository.deleteById(likeId);
        return "Deleted  "+likeId+" successfully";
    }
    public List<Like> likesPage(String postOrCommentId, int page, int pageSize){
        Pageable firstPage = PageRequest.of(page, pageSize);
        List<Like> allLikes=likeRepository.findBypostorcommentID(postOrCommentId,firstPage);
        return  allLikes;
    }
    public int countLikes(String postOrCommentId){
        List<Like> allData=likeRepository.findAll();
        int count=0;
        for(Like like:allData){
            if(like.getPostOrCommentId().equals(postOrCommentId)){
                count++;
            }
        }
        return count;
    }

    public Like likeDetailsByID(String likeId){
        return likeRepository.findById(likeId).get();
}
    public Like likeCreate(Like like, String postOrCommentId){
        like.setPostOrCommentId(postOrCommentId);
        like.setCreatedAt(LocalDateTime.now());
        return likeRepository.save(like);

    }
}
