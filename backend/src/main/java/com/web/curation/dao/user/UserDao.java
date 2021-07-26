package com.web.curation.dao.user;

import java.util.List;
import java.util.Optional;

import com.web.curation.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;

public interface UserDao extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUid(Long uid);

    Optional<User> findByNickname(String name);




    Optional<User> findByNickname(String nickname);

    List<User> findByUidIn(List<Long> followerId);
}
