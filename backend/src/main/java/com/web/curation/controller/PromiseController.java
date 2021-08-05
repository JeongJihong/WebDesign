package com.web.curation.controller;

import com.web.curation.dao.article.ArticleDao;
import com.web.curation.dao.follow.FollowDao;
import com.web.curation.dao.promise.PromiseDao;
import com.web.curation.dao.promise.PromisepeopleDao;
import com.web.curation.dao.scrap.ScrapDao;
import com.web.curation.dao.user.UserDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.promise.Promise;
import com.web.curation.model.promise.PromiseResponse;
import com.web.curation.model.promise.Promisepeople;
import com.web.curation.model.user.User;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.*;
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
public class PromiseController {

    @Autowired
    ArticleDao articleDao;

    @Autowired
    ScrapDao scrapDao;

    @Autowired
    UserDao userDao;

    @Autowired
    PromiseDao promiseDao;

    @Autowired
    PromisepeopleDao promisePeopleDao;

    @Autowired
    FollowDao followDao;

    @PostMapping("/promise")
    @ApiOperation(value = "약속 생성하기")
    public Object createPromise(@RequestBody Promise promise) {
        Authentication user = SecurityContextHolder.getContext().getAuthentication();
        ResponseEntity response = null;
        if(user.getPrincipal() == "anonymousUser"){
            response = new ResponseEntity<>("Fail", HttpStatus.UNAUTHORIZED);
            return response;
        }else{
            UserDetails user2 = (UserDetails) user.getPrincipal();
            Optional<User> userOpt = userDao.findByEmail(user2.getUsername());

            // 약속 정보 Promise table에 저장
            Long promiseID =  promiseDao.save(Promise.builder()
                            .createruid(userOpt.get().getUid())
                            .promisetime(promise.getPromisetime())
                            .type(promise.getType())
                            .place(promise.getPlace())
                            .title(promise.getTitle())
                            .num(promise.getNum())
                            .lat(promise.getLat())
                            .lon(promise.getLon())
                            .nickname(userOpt.get().getNickname())
                            .build()).getPromiseid();

            // 약속 생성자의 모든 팔로워/팔로잉의 UID 저장
            List<Long> allFollowingFollower = followDao.findBySrcidAndApprove(userOpt.get().getUid());
            allFollowingFollower.addAll(followDao.findByDstidAndApprove(userOpt.get().getUid()));

            // 팔로워/팔로잉 PromisePeople table에 저장
            for (int i = 0; i < allFollowingFollower.size(); i++) {
                User invitedUser = userDao.findByUid(allFollowingFollower.get(i)).get();
                promisePeopleDao.save(Promisepeople.builder()
                                .uid(allFollowingFollower.get(i))
                                .promiseid(promiseID)
                                .createruid(userOpt.get().getUid())
                                .nickname(invitedUser.getNickname())
                                .thumbnail(invitedUser.getThumbnail())
                                .approve(0)
                                .build());
            }

            // 약속 생성자 본인도 PromisePeople table에 저장
            User createrUser = userDao.findByUid(userOpt.get().getUid()).get();
            promisePeopleDao.save(Promisepeople.builder()
                    .uid(userOpt.get().getUid())
                    .promiseid(promiseID)
                    .createruid(userOpt.get().getUid())
                    .nickname(createrUser.getNickname())
                    .thumbnail(createrUser.getThumbnail())
                    .approve(1)
                    .build());
            return promiseID;
        }
    }

    @GetMapping("/promise")
    @ApiOperation(value = "약속 목록 확인하기")
    public Object getPromiseList() {
        Authentication user = SecurityContextHolder.getContext().getAuthentication();
        ResponseEntity response = null;
        if(user.getPrincipal() == "anonymousUser"){
            response = new ResponseEntity<>("Fail", HttpStatus.UNAUTHORIZED);
            return response;
        }else {
            UserDetails user2 = (UserDetails) user.getPrincipal();
            Optional<User> userOpt = userDao.findByEmail(user2.getUsername());

            // 내가 생성하고 약속 시간 전인 Promise의 List
            List<Promise> waitingList = promiseDao.waitingPromise(userOpt.get().getUid());

            // List<Promise>를 peopleNum을 추가한 List<PromiseResponse>로 변환
            Stream<Promise> waitingStream = waitingList.stream();
            List<PromiseResponse> waiting = waitingStream.map(promise -> new PromiseResponse(promise.getPromiseid(),
                    promise.getType(), promise.getNum(), promisePeopleDao.findAllByPromiseidAndApprove(promise.getPromiseid(), 1).size(),
                    promise.getTitle(), promise.getPromisetime()))
                    .collect(Collectors.toList());

            // 아직 약속 인원이 채워지지 않은 리스트 구하기
            for (int i = 0; i < waiting.size(); i++) {
                if(waiting.get(i).getNum() <= waiting.get(i).getPeopleNum()) {
                    waiting.remove(waiting.get(i));
                }
            }

            // 내가 참여할 예정인 약속 목록
            List<Promisepeople> upcomingList = promisePeopleDao.upcomingPromise(userOpt.get().getUid());

            // List<Promisepeople>를 peopleNum을 추가한 List<PromiseResponse>로 변환
            Stream<Promisepeople> upcomingStream = upcomingList.stream();
            List<PromiseResponse> upcoming = upcomingStream.map(promisepeople -> new PromiseResponse(promisepeople.getPromiseid(),
                            promiseDao.findByPromiseid(promisepeople.getPromiseid()).getType(),
                            promiseDao.findByPromiseid(promisepeople.getPromiseid()).getNum(),
                            promisePeopleDao.findAllByPromiseidAndApprove(promisepeople.getPromiseid(), 1).size(),
                            promiseDao.findByPromiseid(promisepeople.getPromiseid()).getTitle(),
                            promiseDao.findByPromiseid(promisepeople.getPromiseid()).getPromisetime()))
                    .collect(Collectors.toList());

            Map result = new HashMap<String, Object>();
            result.put("waiting", waiting);
            result.put("upcoming", upcoming);
            response = new ResponseEntity<>(result, HttpStatus.OK);

        }
        return response;
    }

}
