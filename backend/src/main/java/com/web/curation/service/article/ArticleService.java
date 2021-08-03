package com.web.curation.service.article;

import com.google.api.gax.paging.Page;
import com.web.curation.dao.article.ArticleDao;
import com.web.curation.dao.follow.FollowDao;
import com.web.curation.dao.user.UserDao;
import com.web.curation.model.article.Article;
import com.web.curation.model.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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

//    public Page<Article> fetchArticlePagesBy(Long lastArticleId, int size, Long loginMemberId) {
////        Optional<User> loginUser = userDao.findByUid(loginMemberId); // 사용자 객체를 조회한다.
//        List<Long> followingIds = findFollowersWithLoggedInMember(loginMemberId, loginMemberId); // 사용자를 포함하고, 사용자가 팔로우하고 있는 사람들을 가져온다.
//        Page<Article> articles = fetchPages(lastArticleId, size, followingIds); // followers의 게시물들을 페이지네이션해서 가져온다.
//
//        return ArticleAssembler.toDtos(articles.getNextPage(), loginUser);
//    }
//
//    private List<Long> findFollowersWithLoggedInMember(Long memberId, Long loginMemberId) {
//        List<Long> followingIds = followDao.findBySrcidAndApprove(memberId);
////        List<User> allFollowings = userDao.findByUidIn(followingIds);
//        followingIds.add(loginMemberId);
//        return followingIds;
//    }
//
//    private Page<Article> fetchPages(Long lastArticleId, int size, List<Long> followingIds) {
//        PageRequest pageRequest = PageRequest.of(0, size); // 페이지네이션을 위한 PageRequest, 페이지는 0으로 고정한다.
//        return articleDao.findByArticleidLessThanAndIdInOrderByIdDesc(lastArticleId, followingIds, pageRequest); // JPA 쿼리 메소드
//    }
}
