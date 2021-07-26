package com.web.curation.controller;

import com.web.curation.config.security.JwtTokenProvider;
import com.web.curation.dao.user.UserDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.user.SignupRequest;
import com.web.curation.model.user.User;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Optional;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@CrossOrigin(origins = { "http://localhost:3000" })
@RequiredArgsConstructor
@RestController
public class AccountController {

    @Autowired
    UserDao userDao;

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;



    @GetMapping("/account/login")
    @ApiOperation(value = "로그인")
    public Object login(@RequestParam(required = true) final String email,
                        @RequestParam(required = true) final String password) {

        User member = userDao.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-Mail 입니다."));
        if(!passwordEncoder.matches(password, member.getPassword())){
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        return jwtTokenProvider.createToken(member.getEmail(), member.getRoles());

    }

    @PostMapping("/account/signup")
    @ApiOperation(value = "가입하기")
    public Object signup(@Valid @RequestBody SignupRequest request) {

        return userDao.save(User.builder()
                .uid(null)
                .introduction(null)
                .thumbnail(null)
                .email(request.getEmail())
                .nickname(request.getNickname())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Collections.singletonList("ROLE_USER"))
                .build()).getUid();
    }

    @PatchMapping("/account/changePassword")
    @ApiOperation(value = "비밀번호변경")
    public Object changePassword(@RequestParam(required = true) final String oldPassword,
                                 @RequestParam(required = true) final String newPassword,
                                 @RequestParam(required = true) final String email){
        //이메일, 현재비밀번호, 바꿀 비밀번호 Parameter로 받아옴
        Optional<User> userOpt = userDao.findByEmail(email);
        if(!passwordEncoder.matches(oldPassword, userOpt.get().getPassword())){
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        User user = new User(userOpt.get().getUid(), userOpt.get().getNickname(), email,
                passwordEncoder.encode(newPassword), userOpt.get().getIntroduction(), userOpt.get().getThumbnail(), userOpt.get().getRoles());
        //디비에 저장 (바뀐 부분만 데이터베이스에 Update된다)
        userDao.save(user);
        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = "success";
        ResponseEntity response = null;
        response = new ResponseEntity<>("OK", HttpStatus.OK);
        return response;
    }
    @GetMapping("/account/checkJWT")
    @ApiOperation(value = "token통해서 정보 가져오기")
    public String list(){
        //권한체크
        Authentication user = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user2 = (UserDetails) user.getPrincipal();
        Optional<User> userOpt = userDao.findByEmail(user2.getUsername());
        return userOpt.get().getUid() + " / " + user2.getUsername() + " / " + user2.getAuthorities().toString();

    }

    @GetMapping("/account/profile")
    @ApiOperation(value = "본인의 프로필 정보 확인")
    public Object getMyProfileInfo() {
        ResponseEntity response = null;
        response = new ResponseEntity<>("OK", HttpStatus.OK);
        return response;
    }

    @GetMapping("/account/profile/{nickname}")
    @ApiOperation(value = "다른 유저의 프로필 정보 확인")
    public Object getOthersProfileInfo(@PathVariable final String nickname) {
        ResponseEntity response = null;
        response = new ResponseEntity<>(userDao.findByNickname(nickname), HttpStatus.OK);
        return response;
    }

    @PatchMapping("/account/profile")
    @ApiOperation(value = "유저 프로필 정보 변경")
    public Object changeUserProfile(@RequestParam(required = true) final Long uid,
                                    @RequestParam(required = true) final String nickname,
                                    @RequestParam(required = true) final String introduction){
        //유저ID, 새로운 닉네임, 새로운 소개글을 받아온다
        Optional<User> userOpt = userDao.findByUid(uid);
        //User객체에 기존의 정보 담아가지고 오고 새로운 닉네임과 소개글로 세팅한다
        User user = new User(uid, nickname, userOpt.get().getEmail(),
                userOpt.get().getPassword(), introduction, userOpt.get().getThumbnail(), userOpt.get().getRoles());
        userDao.save(user);
        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = "success";
        ResponseEntity response = null;
        response = new ResponseEntity<>("OK", HttpStatus.OK);
        return response;
    }

    @DeleteMapping("/account/profile")
    @ApiOperation(value = "회원 탈퇴")
    public Object changeUserProfile(@RequestParam(required = true) final Long uid){
        userDao.deleteById(uid);
        ResponseEntity response = new ResponseEntity<>("회원정보 삭제 완료", HttpStatus.OK);
        return response;
    }
}