package com.web.curation.controller;

import com.web.curation.dao.alarm.AlarmDao;
import com.web.curation.dao.promise.PromiseDao;
import com.web.curation.dao.user.UserDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.alarm.Alarm;
import com.web.curation.model.alarm.AlarmRequest;
import com.web.curation.model.alarm.LikeFollowRequest;
import com.web.curation.model.promise.Promise;
import com.web.curation.model.user.User;
import com.web.curation.service.alarm.NotificationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

//@CrossOrigin(origins = { "http://localhost:3000" })
@CrossOrigin(origins = "*")
@Controller
public class AlarmController {

    private final NotificationService notificationService;

    public AlarmController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Autowired
    UserDao userDao;

    @Autowired
    AlarmDao alarmDao;

    @Autowired
    PromiseDao promiseDao;

    @PostMapping("/alarm/register")
    @ApiOperation(value = "로그인 시 유저 알람 토큰 저장")
    public ResponseEntity register(@RequestBody String token) {
        Authentication user = SecurityContextHolder.getContext().getAuthentication();
        ResponseEntity response = null;
        if (user.getPrincipal() == "anonymousUser") {
            response = new ResponseEntity<>("Fail", HttpStatus.UNAUTHORIZED);
            return response;
        } else {
            UserDetails user2 = (UserDetails) user.getPrincipal();
            Optional<User> userOpt = userDao.findByEmail(user2.getUsername());
            User user3 = new User(userOpt.get().getUid(), userOpt.get().getNickname(), userOpt.get().getEmail(),
                    userOpt.get().getPassword(), userOpt.get().getIntroduction(), userOpt.get().getThumbnail(),
                    token, userOpt.get().getArticles(), userOpt.get().getRoles());
            userDao.save(user3);
            response = new ResponseEntity<>("Success", HttpStatus.OK);
        }
        return response;
    }

    @PostMapping("/alarm")
    @ApiOperation(value = "알람 전송")
    public ResponseEntity sendAlarm(@RequestBody AlarmRequest request) {
        Authentication user = SecurityContextHolder.getContext().getAuthentication();
        ResponseEntity response = null;
        if (user.getPrincipal() == "anonymousUser") {
            response = new ResponseEntity<>("Fail", HttpStatus.UNAUTHORIZED);
            return response;
        } else {
            UserDetails user2 = (UserDetails) user.getPrincipal();
            Optional<User> sender = userDao.findByEmail(user2.getUsername());
            Optional<User> receiver = userDao.findByNickname(request.getReceiverNickname());
            Long alarmId = alarmDao.save(Alarm.builder()
                    .alarmid(null)
                    .receiveuid(receiver.get().getUid())
                    .senderuid(sender.get().getUid())
                    .title(request.getTitle())
                    .body(request.getBody())
                    .checkalarm(false)
                    .category(request.getCategory())
                    .detail(request.getDetail()).build()
            ).getAlarmid();
            notificationService.sendNotification(alarmDao.getOne(alarmId), receiver.get().getAlarmtoken());
            response = new ResponseEntity<>("OK", HttpStatus.OK);
        }
        return response;
    }

    @GetMapping("/alarm/like")
    @ApiOperation(value = "좋아요 알람 정보 가져오기")
    public Object getLikeAlarm(){
        Authentication user = SecurityContextHolder.getContext().getAuthentication();
        ResponseEntity response = null;
        if (user.getPrincipal() == "anonymousUser") {
            response = new ResponseEntity<>("Fail", HttpStatus.UNAUTHORIZED);
            return response;
        } else {
            UserDetails user2 = (UserDetails) user.getPrincipal();
            Optional<User> loginUser = userDao.findByEmail(user2.getUsername());
            List<Alarm> alarm = alarmDao.findAllByReceiveuidAndCategory(loginUser.get().getUid(), "Like");
            List<LikeFollowRequest> result = new ArrayList<>();
            for(int i = 0; i < alarm.size(); i++){
                Optional<User> tempUser = userDao.findByUid(alarm.get(i).getSenderuid());
                result.add(new LikeFollowRequest(tempUser.get().getUid(), tempUser.get().getNickname(),
                        tempUser.get().getThumbnail(), alarm.get(i).getTitle(), alarm.get(i).getBody(),
                        alarm.get(i).getCheckalarm(), alarm.get(i).getCategory(), alarm.get(i).getDetail()));
            }
            response = new ResponseEntity<>(result, HttpStatus.OK);

        }
        return response;
    }

    @GetMapping("/alarm/follow")
    @ApiOperation(value = "팔로우 알람 가져오기")
    public Object getFollowAlarm()  {
        Authentication user = SecurityContextHolder.getContext().getAuthentication();
        ResponseEntity response = null;
        if (user.getPrincipal() == "anonymousUser") {
            response = new ResponseEntity<>("Fail", HttpStatus.UNAUTHORIZED);
            return response;
        } else {
            UserDetails user2 = (UserDetails) user.getPrincipal();
            Optional<User> loginUser = userDao.findByEmail(user2.getUsername());
            List<Alarm> alarm = alarmDao.findAllByReceiveuidAndCategory(loginUser.get().getUid(), "Follow");
            List<LikeFollowRequest> result = new ArrayList<>();
            for(int i = 0; i < alarm.size(); i++){
                Optional<User> tempUser = userDao.findByUid(alarm.get(i).getSenderuid());
                result.add(new LikeFollowRequest(tempUser.get().getUid(), tempUser.get().getNickname(),
                        tempUser.get().getThumbnail(), alarm.get(i).getTitle(), alarm.get(i).getBody(),
                        alarm.get(i).getCheckalarm(), alarm.get(i).getCategory(), null));
            }
            response = new ResponseEntity<>(result, HttpStatus.OK);
        }

        return response;
    }

    @GetMapping("/alarm/promise")
    @ApiOperation(value = "약속 알람 가져오기")
    public Object getPromiseAlarm()  {
        Authentication user = SecurityContextHolder.getContext().getAuthentication();
        ResponseEntity response = null;
        if (user.getPrincipal() == "anonymousUser") {
            response = new ResponseEntity<>("Fail", HttpStatus.UNAUTHORIZED);
            return response;
        } else {
            UserDetails user2 = (UserDetails) user.getPrincipal();
            Optional<User> loginUser = userDao.findByEmail(user2.getUsername());
            List<Alarm> alarm = alarmDao.findAllByReceiveuidAndCategory(loginUser.get().getUid(), "Promise");
            List<LikeFollowRequest> result = new ArrayList<>();
            Map mapResult = new HashMap<String, Object>();
            for(int i = 0; i < alarm.size(); i++){
                Optional<User> tempUser = userDao.findByUid(alarm.get(i).getSenderuid());
                Promise promiseUser= promiseDao.findByPromiseid(alarm.get(0).getDetail());
                String type = promiseUser.getType();
                result.add(new LikeFollowRequest(tempUser.get().getUid(), tempUser.get().getNickname(),
                        tempUser.get().getThumbnail(), alarm.get(i).getTitle(), alarm.get(i).getBody(),
                        alarm.get(i).getCheckalarm(), alarm.get(i).getCategory(), alarm.get(i).getDetail()));
                mapResult.put(type, result);

            }
            response = new ResponseEntity<>(mapResult, HttpStatus.OK);
        }

        return response;
    }


}


