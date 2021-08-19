package com.web.curation.service.comment;

import com.web.curation.dao.article.ArticleDao;
import com.web.curation.dao.comment.CommentDao;
import com.web.curation.dao.user.UserDao;
import com.web.curation.model.comment.Comment;
import com.web.curation.model.comment.CommentResponse;
import com.web.curation.model.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public void postComment(Long articleid, String request) {
        Optional<User> userOpt = Authentication();
        commentDao.save(Comment.builder()
                .articleid(articleid)
                .id(userOpt.get().getUid())
                .nickname(userOpt.get().getNickname())
                .createdtime(null)
                .updatedtime(null)
                .comment(request)
                .build());
    }

    @Override
    public List<CommentResponse> commentList(Long articleid) {
        List<Comment> commentList = commentDao.findByArticleid(articleid);
        Stream<Comment> commentStream = commentList.stream();
        List<CommentResponse> responseList = commentStream.map(comment -> new CommentResponse(comment.getCommentid(),
                comment.getArticleid(), comment.getId(), comment.getNickname(), comment.getCreatedtime(),
                comment.getUpdatedtime(), comment.getComment(), userDao.findByUid(comment.getId()).get().getThumbnail())).collect(Collectors.toList());

        return responseList;
    }

    @Override
    public void changeComment(Long commentid, String request) {
        Optional<User> userOpt = Authentication();
        Comment oldComment = commentDao.findByCommentid(commentid);

        // 만약 현재 로그인한 유저와 수정요청한 유저가 같을때만 수정한다
        if(userOpt.get().getUid().equals(oldComment.getId())) {
            Comment newComment = new Comment(oldComment.getCommentid(), oldComment.getArticleid(), userOpt.get().getUid(),
                    userOpt.get().getNickname(), oldComment.getCreatedtime(), null, request, oldComment.getArticle());
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
