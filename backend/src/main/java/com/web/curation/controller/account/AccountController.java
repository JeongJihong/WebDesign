package com.web.curation.controller.account;

import java.util.Collections;
import java.util.Optional;

import javax.validation.Valid;

import com.web.curation.config.security.JwtTokenProvider;
import com.web.curation.dao.user.UserDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.user.SignupRequest;
import com.web.curation.model.user.User;

import io.swagger.annotations.ApiImplicitParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    @GetMapping("/account/checkJWT")
    public String list(){
        //권한체크
        Authentication user = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user2 = (UserDetails) user.getPrincipal();
        return user.getAuthorities().toString()+" / "+user2.getUsername()+" / "+user2.getPassword();

    }


}