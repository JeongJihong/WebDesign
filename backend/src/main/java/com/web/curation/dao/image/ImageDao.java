package com.web.curation.dao.image;

import com.web.curation.model.image.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageDao extends JpaRepository<Image, Long> {
}