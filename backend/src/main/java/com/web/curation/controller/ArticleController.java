package com.web.curation.controller;

import com.web.curation.model.BasicResponse;
import com.web.curation.model.article.ViewArticleRequest;
import com.web.curation.model.scrap.Scrap;
import com.web.curation.service.article.ArticleServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.*;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

//@CrossOrigin(origins = { "http://localhost:3000" })
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
public class ArticleController {

    private final ArticleServiceImpl articleService;

    @PostMapping(value = "/article" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "게시글 작성")
    public ResponseEntity<String> postArticle(@RequestPart String content, @RequestPart(required = false) List<MultipartFile> files, @RequestParam(required = false) Long promiseid){
        articleService.postArticle(content, files, promiseid);
        return new ResponseEntity<>("게시글 작성 완료", HttpStatus.OK);
    }

    @GetMapping("/article/{articleid}")
    @ApiOperation(value = "해당 게시글 하나만 보기")
    public ResponseEntity<ViewArticleRequest> getOneArticle(@PathVariable(required = true) final Long articleid) {
        // 게시글 번호로 게시글 추출
        ViewArticleRequest result = articleService.getOneArticle(articleid);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/article/main")
    @ApiOperation(value = "메인피드")
    public ResponseEntity<Map> getArticlePages(@RequestParam int pageNum) {
        Map result = articleService.Mainfeed(pageNum);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/article/{articleid}")
    @ApiOperation(value = "게시글 삭제")
    public ResponseEntity<String> deleteArticle(@PathVariable final Long articleid){
        articleService.deleteArticle(articleid);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @GetMapping("/article/{articleid}/like")
    @ApiOperation(value = "로그인한 유저가 해당 게시글 좋아요를 눌렀는지 확인")
    public ResponseEntity<Boolean> getArticleLike(@PathVariable(required = true) final Long articleid){
        Boolean result = articleService.getArticleLike(articleid);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/article/{articleid}/like")
    @ApiOperation(value = "좋아요")
    public ResponseEntity<Long> articleLike(@PathVariable(required = true) final Long articleid){
        Long result = articleService.articleLike(articleid);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/article/{articleid}/like")
    @ApiOperation(value = "좋아요 취소")
    public ResponseEntity<String> articleLikeCancle(@PathVariable(required = true) final Long articleid){
        articleService.articleLikeCancel(articleid);
        return new ResponseEntity<>("좋아요 취소 완료", HttpStatus.OK);
    }

    @GetMapping("/scrap")
    @ApiOperation(value = "스크랩목록 가져오기")
    public ResponseEntity<List<Scrap>> scraplist() {
        List<Scrap> result = articleService.scrapList();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/scrap/{articleid}")
    @ApiOperation(value = "게시글 스크랩하기")
    public ResponseEntity<String> doScrap(@PathVariable final Long articleid) {
        articleService.doScrap(articleid);
        return new ResponseEntity<>("스크랩 완료", HttpStatus.OK);
    }

    @DeleteMapping("/scrap/{scrapid}")
    @ApiOperation(value = "스크랩 취소")
    public ResponseEntity<String> deleteScrap(@PathVariable final Long scrapid) {
        articleService.deleteScrap(scrapid);
        return new ResponseEntity<>("스크랩 취소 완료", HttpStatus.OK);
    }
}