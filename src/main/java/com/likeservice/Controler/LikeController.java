package com.likeservice.Controler;

import com.likeservice.Model.Like;
import com.likeservice.Model.LikeDto;
import com.likeservice.Service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.QueryParam;
import java.util.List;

import javax.validation.Valid;

@RestController
@RequestMapping("/postsOrComments/{postOrCommentId}")
public class LikeController {
    @Autowired
    private LikeService likeService;

    @DeleteMapping("/likes/{likeId}")
    public ResponseEntity<String> deleteLikeID(@PathVariable("likeId") String likeId, @PathVariable("postOrCommentId") String postOrCommentId) {
        return new ResponseEntity<>(likeService.deleteLikeID(likeId), HttpStatus.ACCEPTED);
    }

    @GetMapping("/likes")
    public ResponseEntity<List<LikeDto>> likesPage(@PathVariable("postOrCommentId") String postOrCommentId, @QueryParam("page") Integer page, @QueryParam("pageSize") Integer pageSize) {
        return new ResponseEntity<>(likeService.likesPage(postOrCommentId, page, pageSize), HttpStatus.ACCEPTED);
    }

    @GetMapping("/likes/count")
    public ResponseEntity<Integer> countLikes(@PathVariable("postOrCommentId") String postOrCommentId) {
        return new ResponseEntity<>(likeService.countLikes(postOrCommentId), HttpStatus.ACCEPTED);
    }

    @GetMapping("/likes/{likeId}")
    public ResponseEntity<Like> likeDetailsByID(@PathVariable("likeId") String Id, @PathVariable("postOrCommentId") String postOrCommentId) {
        return new ResponseEntity<>(likeService.likeDetailsByID(Id), HttpStatus.ACCEPTED);
    }

    @PostMapping("/likes")
    public ResponseEntity<LikeDto> likeCreate(@PathVariable("postOrCommentId") String postOrCommentId, @RequestBody @Valid Like like) {
        return new ResponseEntity<>(likeService.likeCreate(like, postOrCommentId), HttpStatus.ACCEPTED);


    }
}

