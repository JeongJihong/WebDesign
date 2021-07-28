package com.web.curation.controller;

import com.web.curation.dao.article.ArticleDao;
import com.web.curation.dao.comment.CommentDao;
import com.web.curation.dao.user.UserDao;
import com.web.curation.model.article.Article;
import com.web.curation.model.article.PostArticleRequest;
import com.web.curation.model.comment.Comment;
import com.web.curation.model.follow.Follow;
import com.web.curation.model.user.User;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

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

        ResponseEntity response = null;

        commentDao.save(Comment.builder()
        .commentid(null)
        .articleid(articleid)
        .id(request.getId())
        .createdtime(null)
        .updatedtime(null)
        .comment(request.getComment())
        .build());

        response = new ResponseEntity<>("댓글 작성 완료", HttpStatus.OK);
        return response;
    }

    @GetMapping("/article/{articleid}/comment")
    @ApiOperation(value = "댓글목록 가져오기")
    public List<Comment> commentlist(@PathVariable Long articleid) {
        List<Comment> commentList = commentDao.findByArticleid(articleid);
        return commentList;
    }

}
