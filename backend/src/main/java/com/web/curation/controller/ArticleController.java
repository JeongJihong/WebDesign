package com.web.curation.controller;

import com.web.curation.dao.article.ArticleDao;
import com.web.curation.dao.article.ArticleLikeDao;
import com.web.curation.dao.image.ImageDao;
import com.web.curation.dao.scrap.ScrapDao;
import com.web.curation.dao.user.UserDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.article.Article;
import com.web.curation.model.article.PostArticleRequest;
import com.web.curation.model.comment.Comment;
import com.web.curation.model.image.Image;
import com.web.curation.model.scrap.Scrap;
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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@CrossOrigin(origins = { "http://localhost:3000" })
@RequiredArgsConstructor
@RestController
public class ArticleController {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    ArticleDao articleDao;

    @Autowired
    ArticleLikeDao articleLikeDao;

    @Autowired
    ScrapDao scrapDao;

    @Autowired
    UserDao userDao;

    @Autowired
    ImageDao imageDao;

    @PostMapping("/article")
    @ApiOperation(value = "게시글 작성")
    public Object postArticle(@RequestBody PostArticleRequest request) {
        Authentication user = SecurityContextHolder.getContext().getAuthentication();
        ResponseEntity response = null;
        if(user.getPrincipal() == "anonymousUser"){
            response = new ResponseEntity<>("Fail", HttpStatus.UNAUTHORIZED);
            return response;
        }else {
            UserDetails user2 = (UserDetails) user.getPrincipal();
            Optional<User> userOpt = userDao.findByEmail(user2.getUsername());

            Long articleId = articleDao.save(Article.builder()
                    .articleid(null)
                    .id(userOpt.get().getUid())
                    .createdtime(null)
                    .updatedtime(null)
                    .content(request.getContent())
                    .build()
            ).getArticleid();

            for(int i = 0; i < request.getImages().size(); ++i) {
                imageDao.save(Image.builder()
                        .imageid(null)
                        .articleid(articleId)
                        .imgURL(request.getImages().get(i).getImgURL())
                        .build()
                );
            }

            response = new ResponseEntity<>("게시글 작성 완료", HttpStatus.OK);
            return response;
        }
    }

    @GetMapping("/article/{articleid}")
    @ApiOperation(value = "해당 게시글 하나만 보기")
    public Article getTestCode(@PathVariable(required = false) final Long articleid) {
        // 게시글 번호로 게시글 추출
        Article findArticle = articleDao.findByArticleid(articleid);
        int articleLikeNum = articleLikeDao.countArticleLike(articleid);

        Article returnArticle = new Article(findArticle.getArticleid(),
                findArticle.getId(), findArticle.getCreatedtime(), findArticle.getUpdatedtime(),
                findArticle.getContent(), articleLikeNum, findArticle.getImages(), findArticle.getComments());

        return returnArticle;
    }

    @GetMapping("/article")
    @ApiOperation(value = "내 피드 보기")
    public Object viewMyFeed() {
        Authentication user = SecurityContextHolder.getContext().getAuthentication();
        ResponseEntity response = null;
        if(user.getPrincipal() == "anonymousUser"){
            response = new ResponseEntity<>("Fail", HttpStatus.UNAUTHORIZED);
            return response;
        }else {
            UserDetails user2 = (UserDetails) user.getPrincipal();
            Optional<User> userOpt = userDao.findByEmail(user2.getUsername());
            // uid를 통해 해당 유저의 게시글을 불러온다.
            Optional<Article> article = articleDao.findById(userOpt.get().getUid());
            response = new ResponseEntity<>(article, HttpStatus.OK);
        }
        return response;
    }

//    @GetMapping("/article")
//    @ApiOperation(value = "메인 피드 보기")
//    public Object viewMainFeed() {
//        Authentication user = SecurityContextHolder.getContext().getAuthentication();
//        ResponseEntity response = null;
//        if(user.getPrincipal() == "anonymousUser"){
//            response = new ResponseEntity<>("Fail", HttpStatus.UNAUTHORIZED);
//            return response;
//        }else {
//            UserDetails user2 = (UserDetails) user.getPrincipal();
//            Optional<User> userOpt = userDao.findByEmail(user2.getUsername());
//
//            // 좋아요 개수
//            int articlelikenum = 0;
//
//            articleDao.save(Article.builder()
//                    .articleid(null)
//                    .id(null)   // user id
//                    .createdtime(null)
//                    .updatedtime(null)
//                    .content(request.getContent())
//                    .articlelikecount(articlelikenum)
//                    .images(request.getImages())
//                    .comments()
//                    .build()).getArticleid();
//
//            response = new ResponseEntity<>(articleDao, HttpStatus.OK);
//            return response;
//        }
//    }

    @DeleteMapping("/article/{articleid}")
    @ApiOperation(value = "게시글 삭제")
    public Object deleteArticle(@PathVariable final Long articleid){
        Authentication user = SecurityContextHolder.getContext().getAuthentication();
        ResponseEntity response = null;
        if(user.getPrincipal() == "anonymousUser"){
            response = new ResponseEntity<>("Fail", HttpStatus.UNAUTHORIZED);
            return response;
        }else {
            UserDetails user2 = (UserDetails) user.getPrincipal();
            articleDao.deleteByArticleid(articleid);
            response = new ResponseEntity<>("게시글 삭제 완료", HttpStatus.OK);
        }
        return response;
    }

    @GetMapping("/scrap")
    @ApiOperation(value = "스크랩목록 가져오기")
    public Object scraplist() {
        Authentication user = SecurityContextHolder.getContext().getAuthentication();
        ResponseEntity response = null;
        if(user.getPrincipal() == "anonymousUser"){
            response = new ResponseEntity<>("Fail", HttpStatus.UNAUTHORIZED);
            return response;
        }else {
            UserDetails user2 = (UserDetails) user.getPrincipal();
            Optional<User> userOpt = userDao.findByEmail(user2.getUsername());
            response = new ResponseEntity<>(scrapDao.findById(userOpt.get().getUid()), HttpStatus.OK);
        }
        return response;
    }

    @PostMapping("/scrap/{articleid}")
    @ApiOperation(value = "게시글 스크랩하기")
    public Object doScrap(@PathVariable final Long articleid) {
        Authentication user = SecurityContextHolder.getContext().getAuthentication();
        ResponseEntity response = null;
        if(user.getPrincipal() == "anonymousUser"){
            response = new ResponseEntity<>("Fail", HttpStatus.UNAUTHORIZED);
            return response;
        }else{
            UserDetails user2 = (UserDetails) user.getPrincipal();
            Optional<User> userOpt = userDao.findByEmail(user2.getUsername());
            return scrapDao.save(Scrap.builder()
                    .scrapid(null)
                    .id(userOpt.get().getUid())
                    .articleid(articleid)
                    .build());
        }

    }

    @DeleteMapping("/scrap/{scrapid}")
    @ApiOperation(value = "스크랩 취소")
    public Object deleteScrap(@PathVariable final Long scrapid){
        Authentication user = SecurityContextHolder.getContext().getAuthentication();
        ResponseEntity response = null;
        if(user.getPrincipal() == "anonymousUser"){
            response = new ResponseEntity<>("Fail", HttpStatus.UNAUTHORIZED);
        }else {
            UserDetails user2 = (UserDetails) user.getPrincipal();
            Optional<User> userOpt = userDao.findByEmail(user2.getUsername());
            scrapDao.deleteByScrapidAndId(scrapid, userOpt.get().getUid());
            response = new ResponseEntity<>("스크랩 취소 완료", HttpStatus.OK);
        }
        return response;
    }

}