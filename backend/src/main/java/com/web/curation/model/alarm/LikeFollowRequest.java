package com.web.curation.model.alarm;

import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.Valid;

@Valid
@ToString
@Getter
public class LikeFollowRequest {

    private Long senderUid;
    private String senderNickname;
    private String thumbnail;
    private String title;
    private String body;
    private Boolean checkAlarm;
    private String category;
    private Long detail;

    @Builder
    public LikeFollowRequest(Long senderUid, String senderNickname, String thumbnail, String title, String body, Boolean checkAlarm, String category, Long detail) {
        this.senderUid = senderUid;
        this.senderNickname = senderNickname;
        this.thumbnail = thumbnail;
        this.title = title;
        this.body = body;
        this.checkAlarm = checkAlarm;
        this.category = category;
        this.detail = detail;
    }
}
