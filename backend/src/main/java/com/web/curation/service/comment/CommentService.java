package com.web.curation.service.comment;

import com.web.curation.model.comment.Comment;
import com.web.curation.model.comment.CommentResponse;

import java.util.List;

public interface CommentService {

    public void postComment(Long articleid, Comment request);
    public List<CommentResponse> commentList(Long articleid);
    public void changeComment(Long commentid, Comment request);
    public void deleteComment(Long commentid);
}
