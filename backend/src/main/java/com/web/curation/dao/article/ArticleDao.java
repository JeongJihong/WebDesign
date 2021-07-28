package com.web.curation.dao.article;

import com.web.curation.model.article.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArticleDao extends JpaRepository<Article, Long> {
    void deleteByArticleid(Long articleid);

//    List<Article> findByArticleid(Long articleid);

    Article findByArticleid(Long articleid);
}