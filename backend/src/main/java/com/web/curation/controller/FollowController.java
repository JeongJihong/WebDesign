package com.web.curation.controller;

import com.google.api.Http;
import com.web.curation.dao.follow.FollowDao;
import com.web.curation.dao.user.UserDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.article.Article;
import com.web.curation.model.article.ViewArticleRequest;
import com.web.curation.model.follow.Follow;
import com.web.curation.model.follow.FollowRequest;
import com.web.curation.model.search.Search;
import com.web.curation.model.user.User;
import com.web.curation.service.follow.FollowServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

//@CrossOrigin(origins = { "http://localhost:3000" })
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
public class FollowController {

    private final FollowServiceImpl followService;

    @PostMapping("/account/profile/follow")
    @ApiOperation(value = "팔로우 요청")
    public ResponseEntity<Long> FollowRequest(@RequestBody FollowRequest request){
        Long result = followService.followReqeust(request);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/account/profile/follow")
    @ApiOperation(value = "팔로우 요청 취소 및 거부")
    public ResponseEntity<String> FollowReject(@RequestParam(required = true) final String srcnickname,
                               @RequestParam(required = true) final String dstnickname) {
        followService.followReject(srcnickname, dstnickname);
        return new ResponseEntity<>("팔로우 요청 거부 또는 취소 완료", HttpStatus.OK);
    }

    @PatchMapping("/account/profile/follow")
    @ApiOperation(value = "팔로우 요청 승인")
    public ResponseEntity<String> FollowApprove(@RequestParam(required = true) final Long followid){
        followService.followApprove(followid);
        return new ResponseEntity<>("팔로우 요청 승인 완료", HttpStatus.OK);
    }

    @GetMapping("/account/profile/{nickname}/follower")
    @ApiOperation(value = "팔로워 목록 반환")
    public ResponseEntity<List<User>> followerList(@PathVariable final String nickname){
        List<User> result = followService.followerList(nickname);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/account/profile/{nickname}/following")
    @ApiOperation(value = "팔로잉 목록 반환")
    public ResponseEntity<List<User>> followingList(@PathVariable final String nickname){
        List<User> result = followService.followingList(nickname);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}

