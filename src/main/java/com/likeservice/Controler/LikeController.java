package com.likeservice.Controler;

import com.likeservice.Model.Like;
import com.likeservice.Service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.QueryParam;
import java.util.List;

@RestController
public class LikeController {
    @Autowired
    private LikeService likeService;

    @GetMapping("/postsOrComments/{postOrCommentId}/likes")
    public ResponseEntity<List<Like>> likesPage(@PathVariable("postOrCommentId") String postOrCommentId, @QueryParam("page") int page, @QueryParam("pageSize") int pageSize){
        return new ResponseEntity<>(likeService.likesPage(postOrCommentId,page,pageSize), HttpStatus.ACCEPTED);
    }
}
