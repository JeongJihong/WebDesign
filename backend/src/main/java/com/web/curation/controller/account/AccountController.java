package com.web.curation.controller.account;

import java.util.Collections;
import java.util.Optional;

import javax.validation.Valid;

import com.web.curation.config.security.JwtTokenProvider;
import com.web.curation.dao.user.UserDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.user.SignupRequest;
import com.web.curation.model.user.User;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiOperation;

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
        System.out.println(member.getPassword());
        System.out.println("{noop}"+password);
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if(!passwordEncoder.matches(password, member.getPassword())){
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        return jwtTokenProvider.createToken(member.getUsername(), member.getRoles());

//        Optional<User> userOpt = userDao.findUserByEmailAndPassword(email, password);
//
//        ResponseEntity response = null;
//
//        if (userOpt.isPresent()) {
//            final BasicResponse result = new BasicResponse();
//            result.status = true;
//            result.data = "success";
//            response = new ResponseEntity<>(result, HttpStatus.OK);
//        } else {
//            response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//        }
//
//        return response;
    }

    @PostMapping("/account/signup")
    @ApiOperation(value = "가입하기")
    public Object signup(@Valid @RequestBody SignupRequest request) {
        System.out.println(request.getNickname());
        System.out.println(request.getEmail());
        return userDao.save(User.builder()
                .uid(null)
                .introduction(null)
                .thumbnail(null)
                .email(request.getEmail())
                .nickname(request.getNickname())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Collections.singletonList("ROLE_USER"))
                .build()).getUid();
        // 이메일, 닉네임 중복처리 필수
        // 회원가입단을 생성해 보세요.
        ResponseEntity response = null;
        // 중복되는 이메일이 있는지 확인 (닉네임 중복 아직)
        if(userDao.getUserByEmail(request.getEmail()) != null){
            response = new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }else{
            //데이터베이스에 정보 삽입
            userDao.save(new User(null, request.getNickname(), request.getEmail(), request.getPassword(), null, null));
            final BasicResponse result = new BasicResponse();
            result.status = true;
            result.data = "success";
            //Response OK 반환
            response = new ResponseEntity<>("OK",HttpStatus.OK);
        }

        return response;
    }

    @PatchMapping("/account/changePassword")
    @ApiOperation(value = "비밀번호변경")
    public Object changePassword(@RequestParam(required = true) final String oldPassword,
                                 @RequestParam(required = true) final String newPassword,
                                 @RequestParam(required = true) final String email){
        //이메일, 현재비밀번호, 바꿀 비밀번호 Parameter로 받아옴
        Optional<User> userOpt = userDao.findUserByEmailAndPassword(email, oldPassword);
        //User객체에 기존의 정보 담아가지고 오고 기존 비밀번호에 새로운 비밀번호를 set
//        userOpt.get().setPassword(newPassword);
        //디비에 저장 (바뀐 부분만 데이터베이스에 Update된다)
        userDao.save(userOpt.get());
        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = "success";
        ResponseEntity response = null;
        response = new ResponseEntity<>("OK", HttpStatus.OK);
        return response;
    }

    @PatchMapping("/account/profile")
    @ApiOperation(value = "유저 프로필 정보 변경")
    public Object changeUserProfile(@RequestParam(required = true) final Long uid,
                                    @RequestParam(required = true) final String nickname,
                                    @RequestParam(required = true) final String introduction){
        //유저ID, 새로운 닉네임, 새로운 소개글을 받아온다
        Optional<User> userOpt = userDao.findByUid(uid);
        System.out.println(userOpt.get().getEmail());
        //User객체에 기존의 정보 담아가지고 오고 새로운 닉네임과 소개글로 세팅한다
        userOpt.get().setNickname(nickname);
        userOpt.get().setIntroduction(introduction);
        //디비에 저장 (바뀐 부분만 데이터베이스에 Update된다)
        userDao.save(userOpt.get());
        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = "success";
        ResponseEntity response = null;
        response = new ResponseEntity<>("OK", HttpStatus.OK);
        return response;
    }
}