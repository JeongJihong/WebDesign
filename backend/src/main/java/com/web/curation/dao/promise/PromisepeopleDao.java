package com.web.curation.dao.promise;

import com.web.curation.model.promise.Promisepeople;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface PromisepeopleDao extends JpaRepository<Promisepeople, Long> {

    List<Promisepeople> findAllByPromiseidAndApprove(Long promiseid, int i);

    @Query(value = "SELECT pp.promisepeopleid, pp.uid, pp.promiseid, " +
            "pp.createruid, pp.updatedtime, pp.nickname, pp.lat, pp.lon, " +
            "pp.thumbnail, pp.approve " +
            "FROM promisepeople pp LEFT OUTER JOIN promise p " +
            "ON pp.promiseid = p.promiseid " +
            "WHERE uid = :loginID " +
            "and TIMESTAMP(promisetime) >= TIMESTAMP(NOW()) " +
            "and approve >= 1", nativeQuery = true)
    List<Promisepeople> upcomingPromise(Long loginID);

    @Transactional
    void deleteAllByPromiseid(Long promiseid);

    Promisepeople findByPromiseidAndUid(Long promiseid, Long uid);

    @Transactional
    void deleteByPromiseidAndUid(Long promiseid, Long uid);
}
