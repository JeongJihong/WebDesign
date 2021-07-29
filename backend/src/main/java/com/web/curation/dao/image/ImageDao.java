package com.web.curation.dao.image;

import com.web.curation.model.image.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageDao extends JpaRepository<Image, Long> {
    List<Image> findByArticleid(Long articleid);

    Image findByArticleidAndImageidStartingWith(Long articleid, String start);


}