package com.web.curation.controller;

import com.web.curation.dao.follow.FollowDao;
import com.web.curation.dao.user.UserDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.follow.Follow;
import com.web.curation.model.user.User;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sun.net.www.MimeTable;

import java.util.Collections;
import java.util.Optional;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@CrossOrigin(origins = { "http://localhost:3000" })
@RequiredArgsConstructor
@RestController
public class FollowController {

    @Autowired
    FollowDao followDao;

    @PostMapping("/account/profile/{nickname}/follow")
    @ApiOperation(value = "팔로우 요청")
    public Object FollowRequest(@RequestParam(required = true) final Long srcid,
                                    @RequestParam(required = true) final Long dstid){
        // 팔로우 요청을 보내는 사람의 ID, 받는 사람의 ID를 저장 & approve는 default로 false로 저장

        return followDao.save(Follow.builder()
                .followid(null)
                .srcid(srcid)
                .dstid(dstid)
                .approve(false)
                .build()).getFollowid();
    }

    @DeleteMapping("/account/profile/{nickname}/follow")
    @ApiOperation(value = "팔로우 요청 거부")
    public Object changeUserProfile(@RequestParam(required = true) final Long followid){
        followDao.deleteById(followid);
        ResponseEntity response = new ResponseEntity<>("팔로우 요청 거부 완료", HttpStatus.OK);
        return response;
    }
}

