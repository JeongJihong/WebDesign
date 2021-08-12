package com.web.curation.controller;

import com.web.curation.dao.article.ArticleDao;
import com.web.curation.dao.article.ArticleLikeDao;
import com.web.curation.dao.image.ImageDao;
import com.web.curation.dao.follow.FollowDao;
import com.web.curation.dao.promise.PromiseDao;
import com.web.curation.dao.scrap.ScrapDao;
import com.web.curation.dao.user.UserDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.article.Article;
import com.web.curation.model.article.ArticleLike;
import com.web.curation.model.article.ArticleWrite;
import com.web.curation.model.article.ViewArticleRequest;
import com.web.curation.model.comment.Comment;
import com.web.curation.model.follow.Follow;
import com.web.curation.model.follow.FollowRequest;
import com.web.curation.model.image.Image;
import com.web.curation.model.promise.Promise;
import com.web.curation.model.scrap.Scrap;
import com.web.curation.model.user.User;
import com.web.curation.service.article.ArticleService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.*;
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

//@CrossOrigin(origins = { "http://localhost:3000" })
@CrossOrigin(origins = "*")
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
    PromiseDao promiseDao;

    @Autowired
    ArticleService articleService;

//    final String basePath = "/home/ubuntu/b302/dist/img/feed/";
    final String rootPath = System.getProperty("user.dir");
    String basePath = rootPath.substring(0, rootPath.length()-7) + "frontend\\src\\assets\\images\\";

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
            String filename = i + "_" + uuid + "." + extension;
            String rename = basePath + filename;
            File dest = new File(rename);
            files.get(i).transferTo(dest);
            pathName.add(filename);
        }

        return pathName;
    }

    @PostMapping(value = "/article" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "게시글 작성")
    public Object postArticle(@RequestPart String content, @RequestPart(required = false) List<MultipartFile> files, @RequestParam(required = false) Long promiseid) throws IOException {
        Authentication user = SecurityContextHolder.getContext().getAuthentication();
        ResponseEntity response = null;
        if(user.getPrincipal() == "anonymousUser"){
            response = new ResponseEntity<>("Fail", HttpStatus.UNAUTHORIZED);
            return response;
        }else {
            UserDetails user2 = (UserDetails) user.getPrincipal();
            Optional<User> userOpt = userDao.findByEmail(user2.getUsername());

            // 일반 게시글에 이미지 첨부가 되지 않았을 경우 게시글 작성 실패
            if(promiseid == null && files.size() == 0) {
                response = new ResponseEntity<>("게시글 작성 실패", HttpStatus.BAD_REQUEST);
                return response;
            }

            Long articleId = articleDao.save(Article.builder()
                    .articleid(null)
                    .id(userOpt.get().getUid())
                    .promiseid(promiseid)
                    .createdtime(null)
                    .updatedtime(null)
                    .review(content)
                    .build()
            ).getArticleid();

            List<String> pathName = saveFiles(files);
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
            Promise promise = null;
            if(article.get().getPromiseid() != null) {
                promise = promiseDao.findByPromiseid(article.get().getPromiseid());
            }
            ViewArticleRequest result = new ViewArticleRequest(article.get(), promise, userOpt.get().getUid(),
                                                            userOpt.get().getNickname(), like, likeCheck, scrapCheck);
            response = new ResponseEntity<>(result, HttpStatus.OK);
        }
        return response;
    }

    @GetMapping("/article/main")
    @ApiOperation(value = "메인피드")
    public Object getArticlePages(@RequestParam int pageNum) {
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

            // Article는 피드를 나타내기에 정보가 부족하기 때문에, List<Article>을 List<ViewArticleRequest>로 변환하여 정보를 더해준다.
            List<Article> articleList = articleService.followingsArticleList(loginID);
            Stream<Article> articleStream = articleList.stream();
            List<ViewArticleRequest> requestList = articleStream.map(article -> new ViewArticleRequest(article, null, loginID, loginUser,
                                                    articleLikeDao.countArticleLike(article.getArticleid()),
                                                    articleLikeDao.existsByArticleidAndId(article.getArticleid(), loginID),
                                                    scrapDao.existsByArticleidAndId(article.getArticleid(), loginID)))
                                                    .collect(Collectors.toList());

            PagedListHolder<ViewArticleRequest> requestPage = new PagedListHolder<>(requestList);
            requestPage.setPageSize(5);
            requestPage.setPage(pageNum);

            Map result = new HashMap<String, Object>();
            result.put("pageList", requestPage.getPageList());
            result.put("totalPages", requestPage.getPageCount());

            for (int i = 0; i < requestPage.getPageList().size(); i++) {
                if(requestPage.getPageList().get(i).getArticleDetail().getPromiseid() != null) {
                    Promise promise = promiseDao.findByPromiseid(requestPage.getPageList().get(i).getArticleDetail().getPromiseid());
                    requestPage.getPageList().get(i).setPromiseDetail(promise);
                }
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

//    @GetMapping("/article/main")
//    @ApiOperation(value = "메인피드")
//    public ResponseEntity<Page<ViewArticleRequest>> getArticlePages(@RequestParam int pageNum) {
//        Authentication user = SecurityContextHolder.getContext().getAuthentication();
//        ResponseEntity response = null;
//        if(user.getPrincipal() == "anonymousUser"){
//            response = new ResponseEntity<>("Fail", HttpStatus.UNAUTHORIZED);
//            return response;
//        }else {
//            UserDetails user2 = (UserDetails) user.getPrincipal();
//            Optional<User> userOpt = userDao.findByEmail(user2.getUsername());
//
//            Long loginID = userOpt.get().getUid();
//            String loginUser = userOpt.get().getNickname();
//
//            // Article는 피드를 나타내기에 정보가 부족하기 때문에, List<Article>을 List<ViewArticleRequest>로 변환하여 정보를 더해준다.
//            List<Article> articleList = articleService.followingsArticleList(loginID);
//            Stream<Article> articleStream = articleList.stream();
//            List<ViewArticleRequest> requestList = articleStream.map(article -> new ViewArticleRequest(article, loginID, loginUser,
//                            articleLikeDao.countArticleLike(article.getArticleid()),
//                            articleLikeDao.existsByArticleidAndId(article.getArticleid(), loginID),
//                            scrapDao.existsByArticleidAndId(article.getArticleid(), loginID)))
//                    .collect(Collectors.toList());
//
//            // 무한 스크롤을 사용할 때, 한 번 정보를 요청할 때마다 5개씩 반환하기 위해 페이지네이션 사용
//            Page<ViewArticleRequest> requestPage = new PageImpl<>(requestList, PageRequest.of(pageNum, 5), requestList.size());
//            return new ResponseEntity<>(requestPage, HttpStatus.OK);
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

    @GetMapping("/article/{articleid}/like")
    @ApiOperation(value = "로그인한 유저가 해당 게시글 좋아요를 눌렀는지 확인")
    public Object getArticleLike(@PathVariable(required = true) final Long articleid){
        Authentication user = SecurityContextHolder.getContext().getAuthentication();
        ResponseEntity response = null;
        if(user.getPrincipal() == "anonymousUser"){
            response = new ResponseEntity<>("Fail", HttpStatus.UNAUTHORIZED);
        }else{
            UserDetails user2 = (UserDetails) user.getPrincipal();
            Optional<User> userOpt = userDao.findByEmail(user2.getUsername());
            boolean isArticeLike = articleLikeDao.existsByArticleidAndId(articleid, userOpt.get().getUid());

            response = new ResponseEntity<>(isArticeLike, HttpStatus.OK);
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