package com.web.curation.dao.follow;

import com.web.curation.model.follow.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowDao extends JpaRepository<Follow, String> {
    void deleteById(Long followid);
}
