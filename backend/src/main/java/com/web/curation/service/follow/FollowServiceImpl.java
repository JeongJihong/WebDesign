package com.web.curation.service.follow;

import com.web.curation.dao.follow.FollowDao;
import com.web.curation.dao.user.UserDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.follow.Follow;
import com.web.curation.model.follow.FollowRequest;
import com.web.curation.model.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class FollowServiceImpl implements FollowService{

    @Autowired
    FollowDao followDao;

    @Autowired
    UserDao userDao;

    @Override
    @Transactional
    public Long followReqeust(FollowRequest request) {
        return followDao.save(Follow.builder()
                .followid(null)
                .srcid(userDao.getUidFromNickname(request.getSrcnickname()))
                .dstid(userDao.getUidFromNickname(request.getDstnickname()))
                .approve(false)
                .build()).getFollowid();
    }

    @Override
    @Transactional
    public void followReject(String srcnickname, String dstnickname) {
        Long srcID = userDao.findByNickname(srcnickname).get().getUid();
        Long dstID = userDao.findByNickname(dstnickname).get().getUid();
        followDao.deleteBySrcidAndDstid(srcID, dstID);
    }

    @Override
    @Transactional
    public void followApprove(Long followid) {
        Optional<Follow> follow = followDao.findByFollowid(followid);
        Follow newFollow = new Follow(followid, follow.get().getSrcid(), follow.get().getDstid(), true);
        followDao.save(newFollow);
    }

    @Override
    public List<User> followerList(String nickname) {
        // 닉네임에 해당하는 User를 찾는다.
        Optional<User> user = userDao.findByNickname(nickname);

        // 해당 유저를 팔로잉하고 있는 유저의 uid를 저장한다.
        List<Long> followerId = followDao.findByDstidAndApprove(user.get().getUid());
        // uid에 해당하는 유저 정보를 저장한다.
        List<User> followerUser = userDao.findByUidIn(followerId);
        return followerUser;
    }

    @Override
    public List<User> followingList(String nickname) {
        // 닉네임에 해당하는 User를 찾는다.
        Optional<User> user = userDao.findByNickname(nickname);

        // 해당 유저가 팔로잉하고 있는 유저의 uid를 저장한다.
        List<Long> followingId = followDao.findBySrcidAndApprove(user.get().getUid());
        // uid에 해당하는 유저 정보를 저장한다.
        List<User> followingUser = userDao.findByUidIn(followingId);
        return followingUser;
    }
}
