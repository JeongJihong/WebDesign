package com.web.curation.dao.promise;
import com.web.curation.model.promise.Promise;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

public interface PromiseDao extends JpaRepository<Promise, Long> {

    @Query(value = "SELECT * FROM promise p " +
            "WHERE createruid = :loginID " +
            "and TIMESTAMP(promisetime) >= TIMESTAMP(NOW())", nativeQuery = true)
    List<Promise> waitingPromise(Long loginID);

    Promise findByPromiseid(Long promiseid);

    Optional<Promise> findByPromiseid(Long id);

    @Transactional
    void deleteByPromiseid(Long promiseid);

//    @Query(value = "SELECT p.promiseid, p.type,  p.num, cp.peopleNum, p.title, p.promisetime " +
//            "FROM promise p " +
//            "LEFT OUTER JOIN " +
//            "(select pp.promiseid, COUNT(pp.promiseid) peopleNum " +
//            "FROM promise p, promisepeople pp " +
//            "WHERE p.promiseid = pp.promiseid and approve = 1 " +
//            "GROUP BY p.promiseid) cp " +
//            "ON p.promiseid = cp.promiseid;", nativeQuery = true)
//    List<Promise> waitingPromise(Long loginID);
}
