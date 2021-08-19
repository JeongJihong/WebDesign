package com.web.curation.controller;

import com.web.curation.model.BasicResponse;
import com.web.curation.model.alarm.AlarmRequest;
import com.web.curation.model.alarm.LikeFollowRequest;
import com.web.curation.service.alarm.AlarmServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

//@CrossOrigin(origins = { "http://localhost:3000" })
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Controller
@RequestMapping("/alarm")
public class AlarmController {

    private final AlarmServiceImpl alarmService;

    @PostMapping("/register")
    @ApiOperation(value = "로그인 시 유저 알람 토큰 저장")
    public ResponseEntity<String> register(@RequestBody String token) {
        alarmService.register(token);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @PostMapping("")
    @ApiOperation(value = "알람 전송")
    public ResponseEntity<String> sendAlarm(@RequestBody AlarmRequest request) {
        alarmService.sendAlarm(request);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @GetMapping("/like")
    @ApiOperation(value = "좋아요 알람 정보 가져오기")
    public ResponseEntity<List<LikeFollowRequest>> getLikeAlarm(){
        List<LikeFollowRequest> result = alarmService.getLikeAlarm();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/follow")
    @ApiOperation(value = "팔로우 알람 가져오기")
    public ResponseEntity<List<LikeFollowRequest>> getFollowAlarm(){
        List<LikeFollowRequest> result = alarmService.getFollowAlarm();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/promise")
    @ApiOperation(value = "약속 알람 가져오기")
    public ResponseEntity<Map> getPromiseAlarm()  {
        Map result = alarmService.getPromiseAlarm();
        return new ResponseEntity<>(result, HttpStatus.OK);

    }


}


