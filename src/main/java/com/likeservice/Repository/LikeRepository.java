package com.likeservice.Repository;

import com.likeservice.Model.Like;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LikeRepository extends MongoRepository<Like,String> {

    public List<Like> findBypostorcommentID(String postOrCommentId, Pageable pageable);
}
