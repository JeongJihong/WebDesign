package com.web.curation.service.comment;

import com.web.curation.dao.article.ArticleDao;
import com.web.curation.dao.comment.CommentDao;
import com.web.curation.dao.user.UserDao;
import com.web.curation.model.comment.Comment;
import com.web.curation.model.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService{
    @Autowired
    private CommentDao commentDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ArticleDao articleDao;

    public Optional<User> Authentication() {
        Authentication user = SecurityContextHolder.getContext().getAuthentication();
        if (user.getPrincipal() == "anonymousUser") {
            throw new IllegalArgumentException("토큰이 불일치하거나 만료되었습니다.");
        }
        UserDetails user2 = (UserDetails) user.getPrincipal();
        Optional<User> userOpt = userDao.findByEmail(user2.getUsername());
        return userOpt;
    }

    @Override
    public void postComment(Long articleid, Comment request) {
        Optional<User> userOpt = Authentication();
        commentDao.save(Comment.builder()
                .commentid(null)
                .articleid(articleid)
                .id(userOpt.get().getUid())
                .nickname(userOpt.get().getNickname())
                .createdtime(null)
                .updatedtime(null)
                .comment(request.getComment())
                .build());
    }

    @Override
    public List<Comment> commentList(Long articleid) {
        return commentDao.findByArticleid(articleid);
    }

    @Override
    public void changeComment(Long commentid, Comment request) {
        Optional<User> userOpt = Authentication();
        Comment oldComment = commentDao.findByCommentid(commentid);

        // 만약 현재 로그인한 유저와 수정요청한 유저가 같을때만 수정한다
        if(userOpt.get().getUid() == oldComment.getId()) {
            Comment newComment = new Comment(oldComment.getCommentid(), oldComment.getArticleid(), userOpt.get().getUid(),
                    request.getNickname(), request.getCreatedtime(), request.getUpdatedtime(), request.getComment(), oldComment.getArticle());
            commentDao.save(newComment);
        }
        else {
            throw new IllegalArgumentException("댓글 작성한 유저만 변경 가능합니다.");
        }
    }

    @Override
    public void deleteComment(Long commentid) {
        Optional<User> userOpt = Authentication();
        Comment deleteComment = commentDao.findByCommentid(commentid);
        // 만약 현재 로그인한 유저와 삭제요청한 유저가 같을때만 삭제한다
        if(userOpt.get().getUid() == deleteComment.getId()) {
            commentDao.deleteByCommentid(commentid);
        }
        else {
            throw new IllegalArgumentException("댓글 작성한 유저만 삭제 가능합니다.");
        }
    }
}
