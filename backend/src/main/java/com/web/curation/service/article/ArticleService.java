package com.web.curation.service.article;

import com.web.curation.model.article.ViewArticleRequest;
import com.web.curation.model.scrap.Scrap;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;


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
