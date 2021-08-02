package com.web.curation.model.follow;

import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Valid
@ToString
public class FollowRequest {
    @ApiModelProperty(required = true)
    @NotNull
    String srcnickname;

    @ApiModelProperty(required = true)
    @NotNull
    String dstnickname;

    public String getSrcnickname() {
        return srcnickname;
    }

    public String getDstnickname() {
        return dstnickname;
    }
}
