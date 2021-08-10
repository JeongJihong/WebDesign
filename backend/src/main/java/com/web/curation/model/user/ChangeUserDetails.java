package com.web.curation.model.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Valid
@ToString
@Getter
public class ChangeUserDetails {

    @ApiModelProperty(required = true)
    @NotNull
    private String nickname;
    private String introduction;
    private String fileLocation;
}
