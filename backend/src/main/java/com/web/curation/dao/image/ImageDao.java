package com.web.curation.dao.image;

import com.web.curation.model.image.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface ImageDao extends JpaRepository<Image, Long> {
    List<Image> findByArticleid(Long articleid);

    List<Image> findByArticleidAndImgURLStartingWith(Long articleid, String start);

    @Transactional
    void deleteByArticleid(Long articleid);



}