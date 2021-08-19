package com.web.curation.controller;

import com.web.curation.model.BasicResponse;
import com.web.curation.service.kakaoLogin.KakaoServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

//@CrossOrigin(origins = { "http://localhost:3000" })
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/kakao")
public class AccountKakaoController {

    private final KakaoServiceImpl kakaoService;

    @PostMapping("")
    @ApiOperation(value = "카카오 로그인")
    public ResponseEntity<String> kakaoLogin(@Valid @RequestParam String access_token) {

        String token = kakaoService.getJwtToken(access_token);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}