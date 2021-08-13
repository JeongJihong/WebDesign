package com.web.curation.service.article;

import com.web.curation.dao.article.ArticleDao;
import com.web.curation.dao.follow.FollowDao;
import com.web.curation.dao.user.UserDao;
import com.web.curation.model.article.Article;
import com.web.curation.model.article.ViewArticleRequest;
import com.web.curation.model.scrap.Scrap;
import com.web.curation.model.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;


public interface ArticleService {

    public void postArticle(String content, List<MultipartFile> files, Long promiseid);
    public ViewArticleRequest getOneArticle(Long articleid);
    public Map Mainfeed(int pageNum);
    public void deleteArticle(Long articleid);
    public Boolean getArticleLike(Long articleid);
    public Long articleLike(Long articleid);
    public void articleLikeCancel(Long articleid);
    public List<Scrap> scrapList();
    public void doScrap(Long articleid);
    public void deleteScrap(Long scrapid);

}
