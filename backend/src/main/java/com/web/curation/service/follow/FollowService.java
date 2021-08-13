package com.web.curation.service.follow;

import com.web.curation.model.follow.FollowRequest;
import com.web.curation.model.user.User;

import java.util.List;

public interface FollowService {
    public Long followReqeust(FollowRequest request);
    public void followReject(String srcnickname, String dstnicknmae);
    public void followApprove(Long followid);
    public List<User> followerList(String nickname);
    public List<User> followingList(String nickname);
}
