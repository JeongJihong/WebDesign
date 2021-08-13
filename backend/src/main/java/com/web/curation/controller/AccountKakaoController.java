package com.web.curation.controller;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.JsonNode;
import com.web.curation.dao.user.UserDao;
import com.web.curation.model.kakao.KakaoProfile;
import com.web.curation.model.kakao.KakaoRestapi;
import com.web.curation.model.kakao.KakaoUserInfo;
import com.web.curation.model.kakao.OAuth2Token;
import com.web.curation.model.user.User;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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

@Controller
@RequestMapping("/kakao")
public class AccountKakaoController {

//    private KakaoRestapi kakaoRestapi = new KakaoRestapi();
//
//    @GetMapping(value="/oauth")
//    public String kakaoConnect() {
//
//        System.out.println("/kakao/oauth 잘 되고 있음");
//        StringBuffer url = new StringBuffer();
//        url.append("https://kauth.kakao.com/oauth/authorize?");
//        url.append("client_id=" + "0947e20aea7d614504cbb83bd1191b96");
//        url.append("&redirect_uri=http://localhost:3000/kakao/callback");
//        url.append("&response_type=code");
//
//        return "redirect:" + url.toString();
//    }
//
//    @RequestMapping(value="/callback",produces="application/json",method= {RequestMethod.GET, RequestMethod.POST})
//    public String kakaoLogin(@RequestParam("code")String code,RedirectAttributes ra,HttpSession session,HttpServletResponse response )throws IOException {
//
//        System.out.println("kakao code:"+code);
//        JsonNode access_token=kakaoRestapi.getKakaoAccessToken(code);
//        // access_token.get("access_token");
//        //   System.out.println("access_token:" + access_token.get("access_token"));
//
//        JsonNode userInfo = KakaoUserInfo.getKakaoUserInfo(access_token.get("access_token"));
//
//        // Get id
//        String member_id = userInfo.get("id").asText();
//
//        String member_name = null;
//
//        // 유저정보 카카오에서 가져오기 Get properties
//        JsonNode properties = userInfo.path("properties");
//        JsonNode kakao_account = userInfo.path("kakao_account");
//        member_name = properties.path("nickname").asText(); //이름 정보 가져오는 것
//        // email = kakao_account.path("email").asText();
//        if(member_name!=null) {
//            session.setAttribute("isLogOn",true);
//            session.setAttribute("member_id",member_name);        //여기 if문 안에 내용은 다 삭제해도 됩니다. 제 프로젝트에만 필요한 코드임.
//        }
//        System.out.println("id : " + member_id);    //여기에서 값이 잘 나오는 것 확인 가능함.
//        System.out.println("name : " + member_name);
//        // System.out.println("email : " + email);
//
//        return "redirect:/index.do";
//    }

    @Autowired
    UserDao userDao;

    @PostMapping("/login")
    public Object kakaoConnect(@RequestParam(required = true) String token, @RequestParam(required = true) String email,
                               @RequestParam(required = true) String nickname, @RequestParam(required = true) String thumbnail) {

        userDao.save(User.builder()
                .uid(111111L)
                .introduction("")
                .thumbnail(thumbnail)
                .email(email)
                .nickname(nickname)
                .password(null)
                .status(0L)
                .roles(Collections.singletonList("ROLE_USER"))
                .build());

        return token;
    }
}