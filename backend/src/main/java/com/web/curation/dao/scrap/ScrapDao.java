package com.web.curation.dao.scrap;

import com.web.curation.model.scrap.Scrap;
import com.web.curation.model.search.Search;
import org.hibernate.loader.plan.spi.QuerySpace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScrapDao extends JpaRepository<Scrap, Long> {
    void deleteByScrapidAndId(Long scrapid, Long id);
}
