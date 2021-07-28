package com.web.curation.dao.comment;

import com.web.curation.model.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentDao extends JpaRepository<Comment, Long> {
    List<Comment> findByArticleid(Long articleid);
}
