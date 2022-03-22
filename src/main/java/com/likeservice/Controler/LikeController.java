package com.likeservice.Controler;

import com.likeservice.Service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LikeController {
    @Autowired
    private LikeService likeService;

    @DeleteMapping("/postsOrComments/{postOrCommentId}/likes/{likeId}")
    public ResponseEntity<String> deleteLikeID(@PathVariable("likeId") String likeId, @PathVariable("postOrCommentId") String postOrCommentId ){
        return new ResponseEntity<>(likeService.deleteLikeID(likeId), HttpStatus.ACCEPTED);
    }
}
