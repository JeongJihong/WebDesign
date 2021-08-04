package com.web.curation.model.article;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class ViewArticleRequest {

    private Article articleDetail;

    private Long userId;            // 로그인 된 사용자 아이디
    private String userNickname;    // 로그인 된 사용자 닉네임

    private int likeCount;          // Article의 articleid로 ArticleLike의 articleid를 통해 계산
    private boolean likeCheck;      // Article의 articleid로 ArticleLike의 articleid와 로그인한 유저의 uid를 통해 계산
    private boolean scrapCheck;     // Article의 articleid와 로그인한 유저의 uid를 통해 계산
}
