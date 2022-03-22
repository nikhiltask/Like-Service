package com.likeservice.Service;

import com.likeservice.Model.Like;
import com.likeservice.Repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeService {
    @Autowired
    private LikeRepository likeRepository;

    public List<Like> likesPage(String postOrCommentId, int page, int pageSize){
        Pageable firstPage = PageRequest.of(page, pageSize);
        List<Like> allLikes=likeRepository.findBypostorcommentID(postOrCommentId,firstPage);
        return  allLikes;

    }
}
