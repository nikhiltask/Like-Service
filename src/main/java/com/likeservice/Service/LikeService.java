package com.likeservice.Service;

import com.likeservice.ConstantFile.ConstantFiles;
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

    public String deleteLikeID(String likeId) {
        if (likeRepository.findById(likeId).isPresent()) {
            likeRepository.deleteById(likeId);
            return ConstantFiles.passCode;
        } else {
            throw new LikesNotFoundException(ConstantFiles.errorCode);
        }
    }

    public List<LikeDto> likesPage(String postOrCommentId, Integer page, Integer pageSize) {
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Pageable firstPage = PageRequest.of(page - 1, pageSize);

        List<Like> allLikes = likeRepository.findBypostorcommentID(postOrCommentId, firstPage);
        if (allLikes.isEmpty()) {
            throw new LikesNotFoundException(ConstantFiles.errorCode);
        }
        List<LikeDto> likeDTOS = new ArrayList<>();
        for (Like like : allLikes) {
            LikeDto likeDTO = new LikeDto(like.getLikeID(), like.getPostorcommentID(),
                    userFeign.findByID(like.getLikedBy()), like.getCreatedAt());

            likeDTOS.add(likeDTO);
        }
        return likeDTOS;

    }

    public int countLikes(String postOrCommentId) {

            List<Like> allData = likeRepository.findAll();
            int count = 0;
            for (Like like : allData) {
                if (like.getPostorcommentID().equals(postOrCommentId)) {
                    count++;
                }
            }
            return count;

    }

    public LikeDto likeDetailsByID(String likeId) {
        if(likeRepository.findById(likeId).isPresent()){
            Like like=likeRepository.findById(likeId).get();

            LikeDto likeDto= new LikeDto(like.getLikeID(),like.getPostorcommentID(),
                    userFeign.findByID(like.getLikedBy())
                    ,like.getCreatedAt());
            return likeDto;
        }else {
            throw new LikesNotFoundException(ConstantFiles.errorCode);
        }
    }

    public LikeDto likeCreate(Like like, String postOrCommentId) {
        if(likeRepository.findById(postOrCommentId).isPresent()) {
            like.setPostorcommentID(postOrCommentId);
            like.setCreatedAt(LocalDateTime.now());
            likeRepository.save(like);
            LikeDto likeDTO = new LikeDto(like.getLikeID(), like.getPostorcommentID(),
                    userFeign.findByID(like.getLikedBy()), like.getCreatedAt());
            return likeDTO;
        }else {
            throw new LikesNotFoundException(ConstantFiles.errorCode);
        }

    }

}
