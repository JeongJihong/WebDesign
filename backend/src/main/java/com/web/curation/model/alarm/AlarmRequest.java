package com.web.curation.model.alarm;

import lombok.Getter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Valid
@ToString
@Getter
public class AlarmRequest {

    @NotNull
    private String receiverKickname;
    @NotNull
    private String title;
    @NotNull
    private String body;
    private Boolean check;
    private String category;
}
