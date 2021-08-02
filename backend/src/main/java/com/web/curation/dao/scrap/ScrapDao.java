package com.web.curation.dao.scrap;

import com.web.curation.model.scrap.Scrap;
import com.web.curation.model.search.Search;
import org.hibernate.loader.plan.spi.QuerySpace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface ScrapDao extends JpaRepository<Scrap, Long> {
    @Transactional
    void deleteByScrapid(Long scrapid);
    boolean existsByArticleidAndId(Long articleid, Long id);

    List<Scrap> findAllById(Long id);
}
