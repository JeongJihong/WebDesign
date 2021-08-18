package com.web.curation.controller;

import com.web.curation.model.BasicResponse;
import com.web.curation.model.user.ChangePasswordRequest;
import com.web.curation.model.user.LoginRequest;
import com.web.curation.model.user.SignupRequest;
import com.web.curation.model.user.User;
import com.web.curation.service.account.AccountServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

//@CrossOrigin(origins = { "http://localhost:3000" })
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountServiceImpl accountService;

    @PostMapping("/login")
    @ApiOperation(value = "로그인")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        String token = accountService.login(request);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/signup")
    @ApiOperation(value = "가입하기")
    public ResponseEntity<Long> signup(@Valid @RequestBody SignupRequest request) {
        Long uid = accountService.signup(request);
        return new ResponseEntity<>(uid, HttpStatus.OK);
    }

    @PutMapping("/changePassword")
    @ApiOperation(value = "비밀번호변경")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest request){
        accountService.changePassword(request);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @GetMapping("/checkJWT")
    @ApiOperation(value = "token통해서 정보 가져오기")
    @ResponseBody
    public ResponseEntity<Map> list(){
        Map userInfo = accountService.list();
        return new ResponseEntity<>(userInfo, HttpStatus.OK);
    }

    @GetMapping("/profile")
    @ApiOperation(value = "유저의 프로필 정보 확인")
    public ResponseEntity<Optional<User>> getMyProfileInfo() {
        Optional<User> userInfo = accountService.getMyProfileInfo();
        return new ResponseEntity<>(userInfo, HttpStatus.OK);
    }

    @PostMapping(value = "/profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "유저 프로필 정보 변경")
    public ResponseEntity<String> changeUserProfile(@RequestPart(required = true) String nickname,
                                                    @RequestPart(required = false) String introduction,
                                                    @RequestPart(required = false) MultipartFile thumbnail){
        accountService.changeUserProfile(nickname, introduction, thumbnail);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @DeleteMapping("/profile")
    @ApiOperation(value = "회원 탈퇴")
    public ResponseEntity<String> deleteUserProfile(){
        accountService.deleteUserProfile();
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @GetMapping("/checkEmail")
    @ApiOperation(value = "이메일 중복 확인")
    public ResponseEntity<String> checkEmail(@RequestParam(required = true) String email){
        Boolean check = accountService.checkEmail(email);
        if(!check){
            return new ResponseEntity<>("Fail", HttpStatus.IM_USED);
        }else {
            return new ResponseEntity<>("Success", HttpStatus.OK);
        }
    }

    @GetMapping("/checkNickname")
    @ApiOperation(value = "닉네임 중복 확인")
    public ResponseEntity<String> checkNickname(@RequestParam(required = true) String nickname){
        Boolean check = accountService.checkNickname(nickname);
        if(!check){
            return new ResponseEntity<>("Fail",HttpStatus.IM_USED);
        }else{
            return new ResponseEntity<>("Success", HttpStatus.OK);
        }
    }

    @GetMapping("/profile/{nickname}")
    @ApiOperation(value = "타 유저 피드 보기")
    public ResponseEntity<Map> viewOtherFeed(@PathVariable("nickname") final String nickname){
        Map result = accountService.viewOtherFeed(nickname);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/profile/follow/{nickname}")
    @ApiOperation(value = "팔로잉 유무 확인")
    @ResponseBody
    public ResponseEntity<Map> followingCheck(@PathVariable("nickname") final String othersNickname){
        Map result = accountService.followingCheck(othersNickname);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/status/{nickname}")
    @ApiOperation(value = "유저의 status 반환")
    public ResponseEntity<Long> getStatus(@PathVariable("nickname") final String nickname) {
        Long status = accountService.getStatus(nickname);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @PutMapping(value = "/status/{nickname}")
    @ApiOperation(value = "유저의 status 수정")
    public ResponseEntity<String> changeStatus(@PathVariable("nickname") final String nickname,
                               @RequestParam(required = true) Long status){
        accountService.changeStatus(nickname, status);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

}