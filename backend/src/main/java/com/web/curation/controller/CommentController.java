package com.web.curation.controller;

import com.web.curation.dao.article.ArticleDao;
import com.web.curation.dao.comment.CommentDao;
import com.web.curation.dao.user.UserDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.comment.Comment;
import com.web.curation.model.comment.CommentResponse;
import com.web.curation.model.user.User;
import com.web.curation.service.comment.CommentServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

//@CrossOrigin(origins = { "http://localhost:3000" })
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/article")
public class CommentController {

    private final CommentServiceImpl commentService;

    @PostMapping("/{articleid}/comment")
    @ApiOperation(value = "댓글 작성하기")
    public ResponseEntity<String> postComment(@PathVariable Long articleid, @RequestBody Comment request) {
        commentService.postComment(articleid, request);
        return new ResponseEntity<>("댓글 작성 완료", HttpStatus.OK);
    }

    @GetMapping("/{articleid}/comment")
    @ApiOperation(value = "댓글목록 가져오기")
    public ResponseEntity<List<CommentResponse>> commentlist(@PathVariable Long articleid) {
        List<CommentResponse> result = commentService.commentList(articleid);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/comment/{commentid}")
    @ApiOperation(value = "댓글 수정하기")
    public ResponseEntity<String> changeComment(@PathVariable Long commentid, @RequestBody Comment request){
        commentService.changeComment(commentid, request);
        return new ResponseEntity<>("댓글 수정 완료", HttpStatus.OK);
    }

    @DeleteMapping("/comment/{commentid}")
    @ApiOperation(value = "댓글 삭제하기")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentid){
        commentService.deleteComment(commentid);
        return new ResponseEntity<>("댓글 삭제 완료", HttpStatus.OK);
    }

}
