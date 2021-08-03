package com.web.curation.dao.article;

import com.web.curation.model.article.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ArticleDao extends JpaRepository<Article, Long> {
    void deleteByArticleid(Long articleid);

    List<Article> findAllById(Long articleid);

    Optional<Article> findByArticleid(Long articleid);

    List<Article> findAllByIdIn(List<Long> articleid);

    List<Article> findAllByIdInOrderByArticleidDesc(List<Long> followingIds);

    @Query(value = "select * from", nativeQuery = true)
    List<Article> selectListLimit(int size);
}
