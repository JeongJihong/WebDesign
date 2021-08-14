package com.web.curation.dao.article;

import com.web.curation.model.article.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface ArticleDao extends JpaRepository<Article, Long> {
    @Transactional
    void deleteByArticleid(Long articleid);

    List<Article> findAllById(Long articleid);

    boolean existsArticleById(Long id);

    Optional<Article> findByArticleid(Long articleid);

    List<Article> findAllByIdIn(List<Long> articleid);

//    Page<Article> findAllByIdInOrderByArticleidDesc(List<Long> followingIds, Pageable pageable);

    List<Article> findAllByIdInOrderByArticleidDesc(List<Long> followingIds);

    @Query(value = "select * from", nativeQuery = true)
    List<Article> selectListLimit(int size);

    @Transactional
    void deleteByPromiseid(Long promiseid);
}
