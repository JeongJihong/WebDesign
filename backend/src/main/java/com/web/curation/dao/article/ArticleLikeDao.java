package com.web.curation.dao.article;

import com.web.curation.model.article.ArticleLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface ArticleLikeDao extends JpaRepository<ArticleLike, Long> {


    @Query(value = "SELECT count(articlelikeid) FROM articlelike WHERE articleid = :articleid", nativeQuery = true)
    int countArticleLike(Long articleid);

    boolean existsByArticleidAndId(Long articleid, Long id);

    @Transactional
    void deleteByIdAndArticleid(Long uid, Long articleid);

    @Transactional
    void deleteByArticleid(Long articleid);
}
