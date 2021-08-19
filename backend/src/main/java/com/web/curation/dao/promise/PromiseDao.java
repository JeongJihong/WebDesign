package com.web.curation.dao.promise;

import com.web.curation.model.promise.Promise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import javax.transaction.Transactional;
import java.util.List;

public interface PromiseDao extends JpaRepository<Promise, Long> {

    @Query(value = "SELECT * FROM promise p " +
            "WHERE createruid = :loginID " +
            "and TIMESTAMP(promisetime) >= TIMESTAMP(NOW())", nativeQuery = true)
    List<Promise> waitingPromise(Long loginID);

    Promise findByPromiseid(Long promiseid);


    @Transactional
    void deleteByPromiseid(Long promiseid);
}
