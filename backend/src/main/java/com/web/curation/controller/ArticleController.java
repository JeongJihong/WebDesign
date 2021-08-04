package com.web.curation.controller;

import com.web.curation.dao.article.ArticleDao;
import com.web.curation.dao.article.ArticleLikeDao;
import com.web.curation.dao.image.ImageDao;
import com.web.curation.dao.follow.FollowDao;
import com.web.curation.dao.scrap.ScrapDao;
import com.web.curation.dao.user.UserDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.article.Article;
import com.web.curation.model.article.ArticleLike;
import com.web.curation.model.article.ViewArticleRequest;
import com.web.curation.model.comment.Comment;
import com.web.curation.model.follow.Follow;
import com.web.curation.model.follow.FollowRequest;
import com.web.curation.model.image.Image;
import com.web.curation.model.scrap.Scrap;
import com.web.curation.model.user.User;
import com.web.curation.service.article.ArticleService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
//import sun.lwawt.macosx.CSystemTray;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@CrossOrigin(origins = { "http://localhost:3000" })
@RequiredArgsConstructor
@RestController
public class ArticleController {

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

    @Autowired
    FollowDao followDao;

    @Autowired
    ArticleService articleService;

    final String rootPath = FileSystemView.getFileSystemView().getHomeDirectory().toString();
    final String basePath = rootPath + "/" + "SNSImage" + "/" ;

    private List<String> saveFiles(List<MultipartFile> files) throws IOException{
        List<String> pathName = new ArrayList<>();
        File Folder = new File(basePath);
        if(!Folder.exists()){
            try{
                Folder.mkdir();
            }catch (Exception e){
                e.getStackTrace();
            }
        }
        UUID uuid = null;
        for(int i = 0; i < files.size(); i++){
            uuid = UUID.randomUUID();
            String extension = FilenameUtils.getExtension(files.get(i).getOriginalFilename());
            String rename = basePath + i + "_" + uuid + "." + extension;
            File dest = new File(rename);
            files.get(i).transferTo(dest);
            pathName.add(rename);
        }

        return pathName;
    }

    @PostMapping(value = "/article", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "게시글 작성")
    public Object postArticle(@RequestPart String content, @RequestPart(required = true) List<MultipartFile> files) throws IOException {
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
                    .review(content)
                    .build()
            ).getArticleid();

            List<String> pathName = saveFiles(files);
            System.out.println(pathName.get(0));
            for(int i = 0; i < files.size(); ++i) {
                imageDao.save(Image.builder()
                        .imageid(null)
                        .articleid(articleId)
                        .imgURL(pathName.get(i))
                        .build()
                );
            }

            response = new ResponseEntity<>("게시글 작성 완료", HttpStatus.OK);
            return response;
        }
    }

    @GetMapping("/article/{articleid}")
    @ApiOperation(value = "해당 게시글 하나만 보기")
    public Object getTestCode(@PathVariable(required = true) final Long articleid) {
        // 게시글 번호로 게시글 추출
        int like = articleLikeDao.countArticleLike(articleid);
        Authentication user = SecurityContextHolder.getContext().getAuthentication();
        ResponseEntity response = null;
        if(user.getPrincipal() == "anonymousUser"){
            response = new ResponseEntity<>("Fail", HttpStatus.UNAUTHORIZED);
            return response;
        }else {
            UserDetails user2 = (UserDetails) user.getPrincipal();
            Optional<User> userOpt = userDao.findByEmail(user2.getUsername());
            List<Image> images = imageDao.findByArticleid(articleid);
            Optional<Article> article = articleDao.findByArticleid(articleid);
            List<String> imageLocation = new ArrayList<>();
            for(int i = 0; i < images.size(); i++){
                imageLocation.add(images.get(i).getImgURL());
            }
            boolean likeCheck = articleLikeDao.existsByArticleidAndId(articleid, userOpt.get().getUid());
            boolean scrapCheck = scrapDao.existsByArticleidAndId(articleid, userOpt.get().getUid());
            ViewArticleRequest result = new ViewArticleRequest(article.get(), userOpt.get().getUid(), userOpt.get().getNickname(), like, likeCheck, scrapCheck);
            response = new ResponseEntity<>(result, HttpStatus.OK);
        }
        return response;
    }

//    @GetMapping("/article")
//    @ApiOperation(value = "전체 게시글 보기")
//    public Object getArticleList(final Pageable pageable) {
//        Authentication user = SecurityContextHolder.getContext().getAuthentication();
//        ResponseEntity response = null;
//        if(user.getPrincipal() == "anonymousUser"){
//            response = new ResponseEntity<>("Fail", HttpStatus.UNAUTHORIZED);
//            return response;
//        }else {
//            UserDetails user2 = (UserDetails) user.getPrincipal();
//            // 로그인 한 사용자
//            Optional<User> userOpt = userDao.findByEmail(user2.getUsername());
//            // 사용자가 팔로잉한 사람들의 ID목록
//            List<Long> followingIds = followDao.findBySrcidAndApprove(userOpt.get().getUid());
//            // 내가 팔로잉한 사람들의 목록
//            List<User> allFollowings = userDao.findByUidIn(followingIds);
//
//            User loginMember = new User(userOpt.get().getUid(), userOpt.get().getNickname(), userOpt.get().getEmail(),
//                    userOpt.get().getPassword(), userOpt.get().getIntroduction(), userOpt.get().getThumbnail(), userOpt.get().getRoles());
//            // 내가 팔로잉한 사람들 + 나
//            allFollowings.add(loginMember);
//            followingIds.add(userOpt.get().getUid());
//
//
////            List<Article> articleList = articleDao.findAllByIdInOrderByArticleidDesc(followingIds);
//            Page<Article> articlePage = articleDao.findAll(pageable);
//            response = new ResponseEntity<>(articlePage, HttpStatus.OK);
//        }
//        return response;
//    }

//    @GetMapping("/article/main")
//    @ApiOperation(value = "메인피드")
//    public ResponseEntity<Page<Article>> getArticlePages(@RequestParam int pageNum) {
//        Authentication user = SecurityContextHolder.getContext().getAuthentication();
//        ResponseEntity response = null;
//        if(user.getPrincipal() == "anonymousUser"){
//            response = new ResponseEntity<>("Fail", HttpStatus.UNAUTHORIZED);
//            return response;
//        }else {
//            UserDetails user2 = (UserDetails) user.getPrincipal();
//            Optional<User> userOpt = userDao.findByEmail(user2.getUsername());
//
//            Page<Article> articleResponses = articleService.fetchArticlePagesBy(pageNum, userOpt.get().getUid());
//            return new ResponseEntity<>(articleResponses, HttpStatus.OK);
//        }
//    }

    // 메인피드 반환객체를 ViewArticleRequest로 변환
    @GetMapping("/article/main")
    @ApiOperation(value = "메인피드")
    public ResponseEntity<Page<ViewArticleRequest>> getArticlePages(@RequestParam int pageNum) {
        Authentication user = SecurityContextHolder.getContext().getAuthentication();
        ResponseEntity response = null;
        if(user.getPrincipal() == "anonymousUser"){
            response = new ResponseEntity<>("Fail", HttpStatus.UNAUTHORIZED);
            return response;
        }else {
            UserDetails user2 = (UserDetails) user.getPrincipal();
            Optional<User> userOpt = userDao.findByEmail(user2.getUsername());

            Long loginID = userOpt.get().getUid();
            String loginUser = userOpt.get().getNickname();

            List<Article> articleList = articleService.followingsArticleList(loginID);
            Stream<Article> articleStream = articleList.stream();

            List<ViewArticleRequest> requestList = articleStream.map(article -> new ViewArticleRequest(article, loginID, loginUser,
                                                    articleLikeDao.countArticleLike(article.getArticleid()),
                                                    articleLikeDao.existsByArticleidAndId(article.getArticleid(), loginID),
                                                    scrapDao.existsByArticleidAndId(article.getArticleid(), loginID)))
                                                     .collect(Collectors.toList());

            Page<ViewArticleRequest> requestPage = new PageImpl<>(requestList, PageRequest.of(pageNum, 5), requestList.size());
            return new ResponseEntity<>(requestPage, HttpStatus.OK);
        }
    }

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

    @PostMapping("/article/{articleid}/like")
    @ApiOperation(value = "좋아요")
    public Object articleLike(@PathVariable(required = true) final Long articleid){
        Authentication user = SecurityContextHolder.getContext().getAuthentication();
        ResponseEntity response = null;
        if(user.getPrincipal() == "anonymousUser"){
            response = new ResponseEntity<>("Fail", HttpStatus.UNAUTHORIZED);
        }else{
            UserDetails user2 = (UserDetails) user.getPrincipal();
            Optional<User> userOpt = userDao.findByEmail(user2.getUsername());
            Long articlelikeid = articleLikeDao.save(ArticleLike.builder()
                    .articlelikeid(null)
                    .id(userOpt.get().getUid())
                    .articleid(articleid)
                    .build()).getArticlelikeid();

            response = new ResponseEntity<>(articlelikeid, HttpStatus.OK);
        }
        return response;
    }

    @DeleteMapping("/article/{articleid}/like")
    @ApiOperation(value = "좋아요 취소")
    public Object articleLikeCancle(@PathVariable(required = true) final Long articleid){
        Authentication user = SecurityContextHolder.getContext().getAuthentication();
        ResponseEntity response = null;
        if(user.getPrincipal() == "anonymousUser"){
            response = new ResponseEntity<>("Fail", HttpStatus.UNAUTHORIZED);
        }else{
            UserDetails user2 = (UserDetails) user.getPrincipal();
            Optional<User> userOpt = userDao.findByEmail(user2.getUsername());
            articleLikeDao.deleteByIdAndArticleid(userOpt.get().getUid(), articleid);
            response = new ResponseEntity<>("좋아요 취소 완료", HttpStatus.OK);
        }
        return response;
    }

    @GetMapping("/scrap")
    @ApiOperation(value = "스크랩목록 가져오기")
    public ResponseEntity<List<Scrap>> scraplist() {
        Authentication user = SecurityContextHolder.getContext().getAuthentication();
        ResponseEntity response = null;
        if(user.getPrincipal() == "anonymousUser"){
            response = new ResponseEntity<>("Fail", HttpStatus.UNAUTHORIZED);
            return null;
        }else {
            UserDetails user2 = (UserDetails) user.getPrincipal();
            Optional<User> userOpt = userDao.findByEmail(user2.getUsername());
            response = new ResponseEntity<>(scrapDao.findAllById(userOpt.get().getUid()), HttpStatus.OK);
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
                    .thumnailURL(articleDao.findByArticleid(articleid).get().getImages().get(0).getImgURL())
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
            scrapDao.deleteByScrapid(scrapid);
            response = new ResponseEntity<>("스크랩 취소 완료", HttpStatus.OK);
        }
        return response;
    }

}