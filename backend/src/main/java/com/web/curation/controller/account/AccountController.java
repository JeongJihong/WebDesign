package com.web.curation.controller.account;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.validation.Valid;

import com.web.curation.dao.user.UserDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.user.SignupRequest;
import com.web.curation.model.user.User;

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
@RestController
public class AccountController {

    @Autowired
    UserDao userDao;

    @GetMapping("/account/login")
    @ApiOperation(value = "로그인")
    public Object login(@RequestParam(required = true) final String email,
                        @RequestParam(required = true) final String password) {

        Optional<User> userOpt = userDao.findUserByEmailAndPassword(email, password);

        ResponseEntity response = null;

        if (userOpt.isPresent()) {
            final BasicResponse result = new BasicResponse();
            result.status = true;
            result.data = "success";
            response = new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return response;
    }

    @PostMapping("/account/signup")
    @ApiOperation(value = "가입하기")
    public Object signup(@Valid @RequestBody SignupRequest request) {
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
        userOpt.get().setPassword(newPassword);
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