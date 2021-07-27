package com.web.curation.controller;

import com.web.curation.config.firebase.FirebaseCloudMessageService;
import com.web.curation.model.BasicResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@CrossOrigin(origins = { "http://localhost:3000" })
@Controller
public class AlarmController {

    private final FirebaseCloudMessageService firebaseCloudMessageService;

    public AlarmController(FirebaseCloudMessageService firebaseCloudMessageService) {
        this.firebaseCloudMessageService = firebaseCloudMessageService;
    }
    @GetMapping("/alarm/send")
    @ApiOperation(value = "Test Alarm Token")
    public void sendNotification(@RequestParam String registrationToken,
                                 @RequestParam String title,
                                 @RequestParam String Body) throws IOException{
        firebaseCloudMessageService.sendMessageTo(registrationToken, title, Body);

    }
}
