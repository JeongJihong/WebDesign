package com.web.curation.service.article;

import com.web.curation.dao.article.ArticleDao;
import com.web.curation.dao.article.ArticleLikeDao;
import com.web.curation.dao.follow.FollowDao;
import com.web.curation.dao.image.ImageDao;
import com.web.curation.dao.promise.PromiseDao;
import com.web.curation.dao.scrap.ScrapDao;
import com.web.curation.dao.user.UserDao;
import com.web.curation.model.article.Article;
import com.web.curation.model.article.ArticleLike;
import com.web.curation.model.article.ViewArticleRequest;
import com.web.curation.model.image.Image;
import com.web.curation.model.promise.Promise;
import com.web.curation.model.scrap.Scrap;
import com.web.curation.model.user.User;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class ArticleServiceImpl implements ArticleService{

    @Autowired
    private FollowDao followDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ImageDao imageDao;

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private ScrapDao scrapDao;

    @Autowired
    private PromiseDao promiseDao;

    @Autowired
    private ArticleLikeDao articleLikeDao;


    final String rootPath = System.getProperty("user.dir");
    final String basePath = rootPath.substring(0, rootPath.length()-5) + "/b302/dist/img/";

    public Optional<User> Authentication() {
        Authentication user = SecurityContextHolder.getContext().getAuthentication();
        if (user.getPrincipal() == "anonymousUser") {
            throw new IllegalArgumentException("토큰이 불일치하거나 만료되었습니다.");
        }
        UserDetails user2 = (UserDetails) user.getPrincipal();
        Optional<User> userOpt = userDao.findByEmail(user2.getUsername());
        return userOpt;
    }

    private List<String> saveFiles(List<MultipartFile> files) throws IOException {
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

    public List<Article> followingsArticleList(Long loginMemberId) {
        // 내가 팔로잉하고 있는 유저 + 나의 게시물 리스트를 반환
        List<Long> followingIds = followDao.findBySrcidAndApprove(loginMemberId);
        followingIds.add(loginMemberId);
        return articleDao.findAllByIdInOrderByArticleidDesc(followingIds);
    }

    @Override
    public void postArticle(String content, List<MultipartFile> files, Long promiseid) {
        Optional<User> userOpt = Authentication();
        // 일반 게시글에 이미지 첨부가 되지 않았을 경우 게시글 작성 실패
        if(promiseid == null && files.size() == 0) {
            throw new IllegalArgumentException("일반 게시글에는 이미지가 첨부 되어야 합니다.");
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

        List<String> pathName = null;
        try {
            pathName = saveFiles(files);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("=============================================");
        System.out.println(rootPath);
        System.out.println(basePath);
        System.out.println("=============================================");

        for(int i = 0; i < files.size(); ++i) {
            imageDao.save(Image.builder()
                    .imageid(null)
                    .articleid(articleId)
                    .imgURL(pathName.get(i))
                    .build()
            );
        }
    }

    @Override
    public ViewArticleRequest getOneArticle(Long articleid) {
        Optional<User> userOpt = Authentication();
        int like = articleLikeDao.countArticleLike(articleid);
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
        return result;
    }

    @Override
    public Map Mainfeed(int pageNum) {
        Optional<User> userOpt = Authentication();
        Long loginID = userOpt.get().getUid();
        String loginUser = userOpt.get().getNickname();
        // Article는 피드를 나타내기에 정보가 부족하기 때문에, List<Article>을 List<ViewArticleRequest>로 변환하여 정보를 더해준다.
        List<Article> articleList = followingsArticleList(loginID);
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
        return result;
    }

    @Override
    public void deleteArticle(Long articleid) {
        Optional<User> userOpt = Authentication();
        articleDao.deleteByArticleid(articleid);
    }

    @Override
    public Boolean getArticleLike(Long articleid) {
        Optional<User> userOpt = Authentication();
        boolean isArticeLike = articleLikeDao.existsByArticleidAndId(articleid, userOpt.get().getUid());
        return isArticeLike;
    }

    @Override
    public Long articleLike(Long articleid) {
        Optional<User> userOpt = Authentication();
        Long articlelikeid = articleLikeDao.save(ArticleLike.builder()
                .articlelikeid(null)
                .id(userOpt.get().getUid())
                .articleid(articleid)
                .build()).getArticlelikeid();
        return articlelikeid;
    }

    @Override
    public void articleLikeCancel(Long articleid) {
        Optional<User> userOpt = Authentication();
        articleLikeDao.deleteByIdAndArticleid(userOpt.get().getUid(), articleid);
    }

    @Override
    public List<Scrap> scrapList() {
        Optional<User> userOpt = Authentication();
        List<Scrap> result = scrapDao.findAllById(userOpt.get().getUid());
        return result;
    }

    @Override
    public void doScrap(Long articleid) {
        Optional<User> userOpt = Authentication();
        Optional<Article> article = articleDao.findByArticleid(articleid);
        if(article.get().getImages().size() == 0){
            scrapDao.save(Scrap.builder()
                    .scrapid(null)
                    .id(userOpt.get().getUid())
                    .articleid(articleid)
                    .thumnailURL(null)
                    .build());
        }
        else{
            scrapDao.save(Scrap.builder()
                    .scrapid(null)
                    .id(userOpt.get().getUid())
                    .articleid(articleid)
                    .thumnailURL(articleDao.findByArticleid(articleid).get().getImages().get(0).getImgURL())
                    .build());
        }
    }

    @Override
    public void deleteScrap(Long scrapid) {
        scrapDao.deleteByScrapid(scrapid);
    }
}
