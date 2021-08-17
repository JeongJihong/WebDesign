package com.web.curation.controller;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.fasterxml.jackson.databind.JsonNode;
import com.web.curation.config.security.JwtTokenProvider;
import com.web.curation.dao.user.UserDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.kakao.KakaoProfile;
import com.web.curation.model.kakao.KakaoRestapi;
import com.web.curation.model.kakao.KakaoUserInfo;
import com.web.curation.model.kakao.OAuth2Token;
import com.web.curation.model.user.SignupRequest;
import com.web.curation.model.user.User;
import com.web.curation.service.kakaoLogin.KakaoAPIService;
import com.web.curation.service.kakaoLogin.KakaoServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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