package com.web.curation.model.kakao;

import lombok.*;

import javax.validation.constraints.NotNull;

@NotNull
@ToString
@Getter
@Builder
@AllArgsConstructor
public class KakaoProfile {
    private String access_token;
    private String email;
    private String nickname;
    private String thumbnail;
}