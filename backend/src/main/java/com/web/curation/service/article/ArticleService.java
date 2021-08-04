package com.web.curation.service.article;

import com.web.curation.dao.article.ArticleDao;
import com.web.curation.dao.follow.FollowDao;
import com.web.curation.dao.user.UserDao;
import com.web.curation.model.article.Article;
import com.web.curation.model.article.ViewArticleRequest;
import com.web.curation.model.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ArticleService {

    @Autowired
    private final ArticleDao articleDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private FollowDao followDao;

//    public Page<Article> fetchArticlePagesBy(int pageNum, Long loginMemberId) {
//        List<Long> followingIds = findFollowersWithLoggedInMember(loginMemberId); // 사용자를 포함하고, 사용자가 팔로우하고 있는 사람들을 가져온다.
//        Page<Article> articles = fetchPages(pageNum, followingIds); // followers의 게시물들을 페이지네이션해서 가져온다.
//
//        return articles;
//    }
//
//    private List<Long> findFollowersWithLoggedInMember(Long loginMemberId) {
//        List<Long> followingIds = followDao.findBySrcidAndApprove(loginMemberId);
//        followingIds.add(loginMemberIㅋd);
//        return followingIds;
//    }
//
//    private Page<Article> fetchPages(int pageNum, List<Long> followingIds) {
//        Pageable pageRequest = PageRequest.of(pageNum, 5); // 페이지네이션을 위한 PageRequest, 페이지는 0으로 고정한다.
//        return articleDao.findAllByIdInOrderByArticleidDesc(followingIds, pageRequest);
//    }

    public List<Article> followingsArticleList(Long loginMemberId) {
        List<Long> followingIds = followDao.findBySrcidAndApprove(loginMemberId);
        followingIds.add(loginMemberId);
        return articleDao.findAllByIdIn(followingIds);
    }
}
