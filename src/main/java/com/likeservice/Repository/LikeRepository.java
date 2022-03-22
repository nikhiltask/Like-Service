package com.likeservice.Repository;

import com.likeservice.Model.Like;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LikeRepository extends MongoRepository<Like,String> {
}
