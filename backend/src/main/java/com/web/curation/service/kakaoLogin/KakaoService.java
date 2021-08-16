package com.web.curation.service.kakaoLogin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

public interface KakaoService {

    public String getJwtToken(String access_token);
}
