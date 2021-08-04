package com.web.curation.controller;

import com.web.curation.dao.article.ArticleDao;
import com.web.curation.dao.comment.CommentDao;
import com.web.curation.dao.user.UserDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.comment.Comment;
import com.web.curation.model.user.User;
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
public class CommentController {

    @Autowired
    UserDao userDao;

    @Autowired
    ArticleDao articleDao;

    @Autowired
    CommentDao commentDao;

    @PostMapping("/article/{articleid}/comment")
    @ApiOperation(value = "댓글 작성하기")
    public Object postComment(@PathVariable Long articleid, @RequestBody Comment request) {

        Authentication user = SecurityContextHolder.getContext().getAuthentication();
        ResponseEntity response = null;

        if(user.getPrincipal() == "anonymousUser"){
            response = new ResponseEntity<>("Fail", HttpStatus.UNAUTHORIZED);
            return response;
        }else {
            UserDetails user2 = (UserDetails) user.getPrincipal();
            Optional<User> userOpt = userDao.findByEmail(user2.getUsername());

            commentDao.save(Comment.builder()
                    .commentid(null)
                    .articleid(articleid)
                    .id(userOpt.get().getUid())
                    .nickname(userOpt.get().getNickname())
                    .createdtime(null)
                    .updatedtime(null)
                    .comment(request.getComment())
                    .build());

            response = new ResponseEntity<>("댓글 작성 완료", HttpStatus.OK);
            return response;
        }
    }

    @GetMapping("/article/{articleid}/comment")
    @ApiOperation(value = "댓글목록 가져오기")
    public List<Comment> commentlist(@PathVariable Long articleid) {
        List<Comment> commentList = commentDao.findByArticleid(articleid);
        return commentList;
    }

    @PutMapping("/article/comment/{commentid}")
    @ApiOperation(value = "댓글 수정하기")
    public Object changeComment(@PathVariable Long commentid, @RequestBody Comment request){
        Authentication user = SecurityContextHolder.getContext().getAuthentication();
        ResponseEntity response = null;
        if(user.getPrincipal() == "anonymousUser"){
            response = new ResponseEntity<>("Fail", HttpStatus.UNAUTHORIZED);
        }else{
            UserDetails user2 = (UserDetails) user.getPrincipal();
            //유저ID, 새로운 닉네임, 새로운 소개글을 받아온다
            Optional<User> userOpt = userDao.findByEmail(user2.getUsername());
            //기존 Comment 정보를 받아온다
            Comment oldComment = commentDao.findByCommentid(commentid);

            // 만약 현재 로그인한 유저와 수정요청한 유저가 같을때만 수정한다
            if(userOpt.get().getUid() == oldComment.getId()) {
                Comment newComment = new Comment(oldComment.getCommentid(), oldComment.getArticleid(), userOpt.get().getUid(),
                        request.getNickname(), request.getCreatedtime(), request.getUpdatedtime(), request.getComment());
                commentDao.save(newComment);
                response = new ResponseEntity<>("댓글 수정 성공", HttpStatus.OK);
            }
            else {
                response = new ResponseEntity<>("Fail", HttpStatus.UNAUTHORIZED);
            }
        }
        return response;
    }

    @DeleteMapping("/article/comment/{commentid}")
    @ApiOperation(value = "댓글 삭제하기")
    public Object deleteComment(@PathVariable Long commentid){
        Authentication user = SecurityContextHolder.getContext().getAuthentication();
        ResponseEntity response = null;
        if(user.getPrincipal() == "anonymousUser"){
            response = new ResponseEntity<>("Fail", HttpStatus.UNAUTHORIZED);
        }else{
            UserDetails user2 = (UserDetails) user.getPrincipal();
            Optional<User> userOpt = userDao.findByEmail(user2.getUsername());
            Comment deleteComment = commentDao.findByCommentid(commentid);
            // 만약 현재 로그인한 유저와 삭제요청한 유저가 같을때만 삭제한다
            if(userOpt.get().getUid() == deleteComment.getId()) {
                commentDao.deleteByCommentid(commentid);
                response = new ResponseEntity<>("댓글 삭제 성공", HttpStatus.OK);
            }
            else {
                response = new ResponseEntity<>("Fail", HttpStatus.UNAUTHORIZED);
            }
        }
        return response;
    }

}
