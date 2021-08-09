package com.web.curation.dao.promise;

import com.web.curation.model.promise.Promise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PromiseDao extends JpaRepository<Promise, Long> {

    Optional<Promise> findByPromiseid(Long id);
}
