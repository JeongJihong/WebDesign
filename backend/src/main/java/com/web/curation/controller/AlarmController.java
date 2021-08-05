package com.web.curation.controller;

import com.web.curation.dao.alarm.AlarmDao;
import com.web.curation.dao.user.UserDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.alarm.Alarm;
import com.web.curation.model.alarm.AlarmRequest;
import com.web.curation.model.user.User;
import com.web.curation.service.alarm.NotificationService;
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

import java.util.Optional;

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

    @PostMapping("/alarm/register")
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
    public ResponseEntity sendAlarm(@RequestBody AlarmRequest request) {
        Authentication user = SecurityContextHolder.getContext().getAuthentication();
        ResponseEntity response = null;
        if (user.getPrincipal() == "anonymousUser") {
            response = new ResponseEntity<>("Fail", HttpStatus.UNAUTHORIZED);
            return response;
        } else {
            UserDetails user2 = (UserDetails) user.getPrincipal();
            Optional<User> sender = userDao.findByEmail(user2.getUsername());
            Optional<User> receiver = userDao.findByNickname(request.getReceiverKickname());
            Long alarmId = alarmDao.save(Alarm.builder()
                    .alarmid(null)
                    .receiveuid(receiver.get().getUid())
                    .senderuid(sender.get().getUid())
                    .title(request.getTitle())
                    .body(request.getBody())
                    .checkalarm(false)
                    .category(request.getCategory()).build()
            ).getAlarmid();
            notificationService.sendNotification(alarmDao.getOne(alarmId), receiver.get().getAlarmtoken());
            response = new ResponseEntity<>("OK", HttpStatus.OK);
        }
        return response;
    }

}


