package com.web.curation.dao.search;

import com.web.curation.model.search.Search;
import com.web.curation.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SearchDao extends JpaRepository<Search, String> {

    List<Search> findByNameIsContaining(String name);
    List<Search> findById(Long id);
    Optional<User> findByNickName(String nickname);

}
