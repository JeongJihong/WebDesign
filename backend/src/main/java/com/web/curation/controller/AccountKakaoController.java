package com.web.curation.controller;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.fasterxml.jackson.databind.JsonNode;
import com.web.curation.dao.user.UserDao;
import com.web.curation.model.kakao.KakaoProfile;
import com.web.curation.model.kakao.KakaoRestapi;
import com.web.curation.model.kakao.KakaoUserInfo;
import com.web.curation.model.kakao.OAuth2Token;
import com.web.curation.model.user.SignupRequest;
import com.web.curation.model.user.User;
import com.web.curation.service.kakaoLogin.KakaoAPIService;
import io.swagger.annotations.ApiOperation;
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

@Controller
@RequestMapping("/kakao")
public class AccountKakaoController {

    @Autowired
    UserDao userDao;

    @Autowired
    KakaoAPIService kakaoAPIService;

    private KakaoRestapi kakaoRestapi = new KakaoRestapi();

    @PostMapping("")
    @ApiOperation(value = "카카오 로그인")
    public ResponseEntity<String> kakaoLogin(@Valid @RequestParam String access_token) {

        HashMap<String, Object> userInfo = kakaoAPIService.getUserInfo(access_token);

        if(userDao.findByUid(Long.valueOf(userInfo.get("id").toString())).isPresent()) { // 만약 같은 이메일이 있다면 회원 테이블에 저장하지 않고 토큰만 반환
            return new ResponseEntity<>(access_token, HttpStatus.OK);
        }

        userDao.save(User.builder()
                .uid(Long.valueOf(userInfo.get("id").toString()))
                .introduction("")
                .thumbnail(userInfo.get("thumbnail").toString())
                .email(userInfo.get("email").toString())
                .nickname(userInfo.get("nickname").toString())
                .password(null)
                .status(0L)
                .roles(Collections.singletonList("ROLE_USER"))
                .build()).getUid();

        return new ResponseEntity<>(access_token, HttpStatus.OK);
    }

    @RequestMapping(value="/oauth",method= RequestMethod.GET)
    public String kakaoConnect() {

        StringBuffer url = new StringBuffer();
        url.append("https://kauth.kakao.com/oauth/authorize?");
        url.append("client_id=" + "0fa10a70c9660a76e5542202f29797b1");
        url.append("&redirect_uri=http://localhost:3000/kakao/callback");
        url.append("&response_type=code");

        return "redirect:" + url.toString();
    }

    @RequestMapping(value="/callback",produces="application/json",method= {RequestMethod.GET, RequestMethod.POST})
    public String kakaoLogin(@RequestParam("code")String code,RedirectAttributes ra,HttpSession session,HttpServletResponse response )throws IOException {

        System.out.println("kakao code:"+code);
        JsonNode access_token=kakaoRestapi.getKakaoAccessToken(code);
        access_token.get("access_token");
        System.out.println("access_token:" + access_token.get("access_token"));

        JsonNode userInfo = KakaoUserInfo.getKakaoUserInfo(access_token.get("access_token"));

        // Get id
        String member_id = userInfo.get("id").asText();

        String member_name = null;

        // 유저정보 카카오에서 가져오기 Get properties
        JsonNode properties = userInfo.path("properties");
        JsonNode kakao_account = userInfo.path("kakao_account");
        member_name = properties.path("nickname").asText(); //이름 정보 가져오는 것
        // email = kakao_account.path("email").asText();
        if(member_name!=null) {
            session.setAttribute("isLogOn",true);
            session.setAttribute("member_id",member_name);        //여기 if문 안에 내용은 다 삭제해도 됩니다. 제 프로젝트에만 필요한 코드임.
        }
        System.out.println("id : " + member_id);    //여기에서 값이 잘 나오는 것 확인 가능함.
        System.out.println("name : " + member_name);
        // System.out.println("email : " + email);

        return "redirect:/index.do";
    }
}