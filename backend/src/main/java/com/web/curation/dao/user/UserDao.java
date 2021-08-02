package com.web.curation.dao.user;

import java.util.List;
import java.util.Optional;

import com.web.curation.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.swing.text.html.Option;

public interface UserDao extends JpaRepository<User, Long> {

    List<User> findByNicknameIsContaining(String name);

    Optional<User> findByEmail(String email);

    Optional<User> findByUid(Long uid);

    Optional<User> findByNickname(String name);

    List<User> findByUidIn(List<Long> followerId);

    @Query(value = "SELECT uid FROM user u WHERE u.nickname = :nickname", nativeQuery = true)
    Long getUidFromNickname(String nickname);

}
