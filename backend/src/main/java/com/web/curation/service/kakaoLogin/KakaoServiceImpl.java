package com.web.curation.service.kakaoLogin;

import com.web.curation.config.security.JwtTokenProvider;
import com.web.curation.dao.user.UserDao;
import com.web.curation.model.follow.FollowRequest;
import com.web.curation.model.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;

@RequiredArgsConstructor
@Service
public class KakaoServiceImpl implements KakaoService{


    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserDao userDao;

    @Autowired
    KakaoAPIService kakaoAPIService;

    @Override
    public String getJwtToken(String access_token) {

        HashMap<String, Object> userInfo = kakaoAPIService.getUserInfo(access_token);

        if(!userDao.findByEmail(userInfo.get("email").toString()).isPresent()) { // 만약 같은 이메일이 없다면 DB에 회원 정보 저장
            userDao.save(User.builder()
                    .uid(Long.valueOf(userInfo.get("id").toString()))
                    .introduction("")
                    .email(userInfo.get("email").toString())
                    .nickname(userInfo.get("nickname").toString())
                    .password(null)
                    .status(0L)
                    .roles(Collections.singletonList("ROLE_USER"))
                    .build()).getUid();
        }

        User member = userDao.findByEmail(userInfo.get("email").toString()).get();
        String token = jwtTokenProvider.createToken(member.getEmail(), member.getRoles());
        return token;
    }
}
