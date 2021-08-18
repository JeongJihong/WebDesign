package com.web.curation.model.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Valid
@ToString
public class LoginRequest {
    @ApiModelProperty(required = true)
    @NotNull
    String email;
    @ApiModelProperty(required = true)
    @NotNull
    String password;

    public String getEmail(){
        return email;
    }
    public String getPassword(){
        return password;
    }
}
