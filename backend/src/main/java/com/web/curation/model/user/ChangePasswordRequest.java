package com.web.curation.model.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@NotNull
@ToString
@Getter
@Builder
@AllArgsConstructor
public class ChangePasswordRequest {

    private String email;
    private String oldPassword;
    private String newPassword;
}
