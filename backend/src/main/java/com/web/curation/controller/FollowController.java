package com.web.curation.controller;

import com.web.curation.dao.follow.FollowDao;
import com.web.curation.dao.user.UserDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.follow.Follow;
import com.web.curation.model.follow.FollowRequest;
import com.web.curation.model.search.Search;
import com.web.curation.model.user.User;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

//@CrossOrigin(origins = { "http://localhost:3000" })
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
public class FollowController {

    @Autowired
    FollowDao followDao;

    @Autowired
    UserDao userDao;

    @PostMapping("/account/profile/follow")
    @ApiOperation(value = "팔로우 요청")
    public Object FollowRequest(@RequestBody FollowRequest request){
        return followDao.save(Follow.builder()
                .followid(null)
                .srcid(userDao.getUidFromNickname(request.getSrcnickname()))
                .dstid(userDao.getUidFromNickname(request.getDstnickname()))
                .approve(false)
                .build()).getFollowid();
    }

    @DeleteMapping("/account/profile/follow")
    @ApiOperation(value = "팔로우 요청 거부")
    public Object FollowReject(@RequestParam(required = true) final Long followid){
        followDao.deleteById(followid);
        ResponseEntity response = new ResponseEntity<>("팔로우 요청 거부 완료", HttpStatus.OK);
        return response;
    }

    @PatchMapping("/account/profile/follow")
    @ApiOperation(value = "팔로우 요청 승인")
    public Object FollowApprove(@RequestParam(required = true) final Long followid){

        Optional<Follow> follow = followDao.findByFollowid(followid);
        Follow newFollow = new Follow(followid, follow.get().getSrcid(), follow.get().getDstid(), true);
        followDao.save(newFollow);
        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = "success";
        ResponseEntity response = null;
        response = new ResponseEntity<>("OK", HttpStatus.OK);
        return response;
    }

    @GetMapping("/account/profile/{nickname}/follower")
    @ApiOperation(value = "팔로워 목록 반환")
    public ResponseEntity<List<User>> followerList(@PathVariable final String nickname){
        // 닉네임에 해당하는 User를 찾는다.
        Optional<User> user = userDao.findByNickname(nickname);

        // 해당 유저를 팔로잉하고 있는 유저의 uid를 저장한다.
        List<Long> followerId = followDao.findByDstidAndApprove(user.get().getUid());
        // uid에 해당하는 유저 정보를 저장한다.
        List<User> followerUser = userDao.findByUidIn(followerId);
        return new ResponseEntity<>(followerUser, HttpStatus.OK);
    }

    @GetMapping("/account/profile/{nickname}/following")
    @ApiOperation(value = "팔로잉 목록 반환")
    public ResponseEntity<List<User>> followingList(@PathVariable final String nickname){
        // 닉네임에 해당하는 User를 찾는다.
        Optional<User> user = userDao.findByNickname(nickname);

        // 해당 유저가 팔로잉하고 있는 유저의 uid를 저장한다.
        List<Long> followingId = followDao.findBySrcidAndApprove(user.get().getUid());
        // uid에 해당하는 유저 정보를 저장한다.
        List<User> followerUser = userDao.findByUidIn(followingId);
        return new ResponseEntity<>(followerUser, HttpStatus.OK);
    }
}

