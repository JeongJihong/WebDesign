package com.web.curation.controller;

import com.web.curation.dao.user.UserDao;
import com.web.curation.model.BasicResponse;
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

@CrossOrigin(origins = { "http://localhost:3000" })
@Controller
public class AlarmController {

    private final NotificationService notificationService;

    public AlarmController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Autowired
    UserDao userDao;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody String token) {
        Authentication user = SecurityContextHolder.getContext().getAuthentication();
        ResponseEntity response = null;
        if (user.getPrincipal() == "anonymousUser") {
            response = new ResponseEntity<>("Fail", HttpStatus.UNAUTHORIZED);
            return response;
        } else {
            UserDetails user2 = (UserDetails) user.getPrincipal();
            Optional<User> loginUser = userDao.findByEmail(user2.getUsername());
            notificationService.register(loginUser.get().getUid(), token);
            return ResponseEntity.ok().build();
        }
    }

//    @RequestMapping(value = "/alarmTest")
//    public String main(){
//        return "script.html";
//    }




//    @GetMapping("/alarm/send")
//    @ApiOperation(value = "Test Alarm Token")
//    public void sendNotification(@RequestParam String registrationToken,
//                                 @RequestParam String title,
//                                 @RequestParam String Body) throws IOException{
//        firebaseCloudMessageService.sendMessageTo(registrationToken, title, Body);
//
//    }
}
