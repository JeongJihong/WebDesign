package com.web.curation.dao.follow;

import com.web.curation.model.follow.Follow;
import com.web.curation.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FollowDao extends JpaRepository<Follow, Long> {
    void deleteById(Long followid);

    @Query(value = "SELECT srcid FROM follow f WHERE f.dstid = :id and f.approve = true", nativeQuery = true)
    List<Long> findByDstidAndApprove(Long id);

    @Query(value = "SELECT dstid FROM follow f WHERE f.srcid = :id and f.approve = true", nativeQuery = true)
    List<Long> findBySrcidAndApprove(Long id);

    Optional<Follow> findByFollowid(Long followid);

    boolean existsBySrcidAndDstid(Long src, Long dst);

    Optional<Follow> findBySrcidAndDstid(Long src, Long dst);
}
