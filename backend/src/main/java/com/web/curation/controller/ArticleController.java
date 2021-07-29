package com.web.curation.controller;

import com.google.api.Http;
import com.web.curation.dao.article.ArticleDao;
import com.web.curation.dao.article.ArticleLikeDao;
import com.web.curation.dao.image.ImageDao;
import com.web.curation.dao.follow.FollowDao;
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
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.lwawt.macosx.CSystemTray;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

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

    @Autowired
    FollowDao followDao;

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

    @PostMapping("/article")
    @ApiOperation(value = "게시글 작성")
    public Object postArticle(@RequestParam String content, @RequestParam(required = true) List<MultipartFile> files) throws IOException {
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

    @GetMapping("/article/{articleid}/")
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
            List<String> imageLocation = new ArrayList<>();
            for(int i = 0; i < images.size(); i++){
                imageLocation.add(images.get(i).getImgURL());
            }
            boolean likeCheck = articleLikeDao.existsByArticleidAndId(articleid, userOpt.get().getUid());
            boolean scrapCheck = scrapDao.existsByArticleidAndId(articleid, userOpt.get().getUid());
            Map result = new HashMap<String, Object>();
            result.put("likeCount", like);
            result.put("likeCheck", likeCheck);
            result.put("scrapCheck", scrapCheck);
            result.put("imageLocation", imageLocation);
            response = new ResponseEntity<>(result, HttpStatus.OK);
        }
        return response;
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