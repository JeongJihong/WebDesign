package com.web.curation.service.account;

import com.web.curation.model.user.ChangePasswordRequest;
import com.web.curation.model.user.LoginRequest;
import com.web.curation.model.user.SignupRequest;
import com.web.curation.model.user.User;
import org.springframework.web.multipart.MultipartFile;
import java.util.Map;
import java.util.Optional;

public interface AccountService {

    public String test();
    public String login(LoginRequest request);
    public Long signup(SignupRequest request);
    public void changePassword(ChangePasswordRequest request);
    public Map list();
    public Optional<User> getMyProfileInfo();
    public void changeUserProfile(String nickname, String introduction, MultipartFile thumbnail);
    public void deleteUserProfile();
    public Boolean checkEmail(String email);
    public Boolean checkNickname(String nickname);
    public Map viewOtherFeed(String nickname);
    public Map followingCheck(String othersNickname);
    public Long getStatus(String nickname);
    public void changeStatus(String nickname, Long status);

}
