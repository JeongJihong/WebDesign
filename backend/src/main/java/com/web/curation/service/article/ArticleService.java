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
    private FollowDao followDao;

    public List<Article> followingsArticleList(Long loginMemberId) {
        // 내가 팔로잉하고 있는 유저 + 나의 게시물 리스트를 반환
        List<Long> followingIds = followDao.findBySrcidAndApprove(loginMemberId);
        followingIds.add(loginMemberId);
        return articleDao.findAllByIdInOrderByArticleidDesc(followingIds);
    }
}
