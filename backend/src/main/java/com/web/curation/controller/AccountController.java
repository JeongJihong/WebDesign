package com.web.curation.controller;

import com.google.api.Http;
import com.sun.org.apache.xpath.internal.operations.Mult;
import com.web.curation.config.security.JwtTokenProvider;
import com.web.curation.dao.article.ArticleDao;
import com.web.curation.dao.follow.FollowDao;
import com.web.curation.dao.image.ImageDao;
import com.web.curation.dao.user.UserDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.article.Article;
import com.web.curation.model.follow.Follow;
import com.web.curation.model.image.Image;
import com.web.curation.model.user.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.swing.filechooser.FileSystemView;
import javax.swing.text.html.Option;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.*;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

//@CrossOrigin(origins = { "http://localhost:3000" })
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
public class AccountController {

    @Autowired
    UserDao userDao;
    @Autowired
    ArticleDao articleDao;
    @Autowired
    FollowDao followDao;
    @Autowired
    ImageDao imageDao;

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/test")
    public String test(){
        return "test Success";
    }

    @PostMapping("/account/login")
    @ApiOperation(value = "로그인")
    public Object login(@RequestBody LoginRequest request) {

        User member = userDao.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-Mail 입니다."));
        if(!passwordEncoder.matches(request.getPassword(), member.getPassword())){
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        return jwtTokenProvider.createToken(member.getEmail(), member.getRoles());

    }

    @PostMapping("/account/signup")
    @ApiOperation(value = "가입하기")
    public Object signup(@Valid @RequestBody SignupRequest request) {

        return userDao.save(User.builder()
                .uid(null)
                .introduction("")
                .thumbnail(null)
                .email(request.getEmail())
                .nickname(request.getNickname())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Collections.singletonList("ROLE_USER"))
                .build()).getUid();
    }

    @PutMapping("/account/changePassword")
    @ApiOperation(value = "비밀번호변경")
    public Object changePassword(@RequestBody ChangePasswordRequest request){
        //이메일, 현재비밀번호, 바꿀 비밀번호 Parameter로 받아옴
        Optional<User> userOpt = userDao.findByEmail(request.getEmail());
        if(!passwordEncoder.matches(request.getOldPassword(), userOpt.get().getPassword())){
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        User user = new User(userOpt.get().getUid(), userOpt.get().getNickname(), request.getEmail(),
                passwordEncoder.encode(request.getNewPassword()), userOpt.get().getIntroduction(), userOpt.get().getThumbnail(),
                userOpt.get().getStatus(), userOpt.get().getAlarmtoken(), userOpt.get().getArticles(), userOpt.get().getRoles());
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
    @ResponseBody
    public Map list(){
        //권한체크
        Authentication user = SecurityContextHolder.getContext().getAuthentication();
        if(user.getPrincipal() == "anonymousUser"){
            System.out.println("Error");
        }
        UserDetails user2 = (UserDetails) user.getPrincipal();
        Optional<User> userOpt = userDao.findByEmail(user2.getUsername());
        Map result = new HashMap<String, Object>();
        result.put("uid", userOpt.get().getUid());
        result.put("nickname", userOpt.get().getNickname());
        result.put("email", userOpt.get().getUsername());
        result.put("roles", user2.getAuthorities().toString());
        return result;
    }

    @GetMapping("/account/profile")
    @ApiOperation(value = "유저의 프로필 정보 확인")
    public Object getOthersProfileInfo() {
        Authentication user = SecurityContextHolder.getContext().getAuthentication();
        ResponseEntity response = null;
        if(user.getPrincipal() == "anonymousUser"){
            response = new ResponseEntity<>("Fail", HttpStatus.UNAUTHORIZED);
        }else{
            UserDetails user2 = (UserDetails) user.getPrincipal();
            Optional<User> userOpt = userDao.findByEmail(user2.getUsername());
            response = new ResponseEntity<>(userOpt, HttpStatus.OK);
        }
        return response;
    }

    @PatchMapping(value = "/account/profile")
    @ApiOperation(value = "유저 프로필 정보 변경")
    public Object changeUserProfile(@RequestBody ChangeUserDetails request){
        Authentication user = SecurityContextHolder.getContext().getAuthentication();
        ResponseEntity response = null;
        if(user.getPrincipal() == "anonymousUser"){
            response = new ResponseEntity<>("Fail", HttpStatus.UNAUTHORIZED);
        }else{
            UserDetails user2 = (UserDetails) user.getPrincipal();
            //유저ID, 새로운 닉네임, 새로운 소개글을 받아온다
            Optional<User> userOpt = userDao.findByEmail(user2.getUsername());
            //User객체에 기존의 정보 담아가지고 오고 새로운 닉네임과 소개글로 세팅한다

            User user3 = new User(userOpt.get().getUid(), request.getNickname(), userOpt.get().getEmail(),
                    userOpt.get().getPassword(), request.getIntroduction(), request.getFileLocation(),
                    userOpt.get().getStatus(), userOpt.get().getAlarmtoken(), userOpt.get().getArticles(), userOpt.get().getRoles());
            userDao.save(user3);
            response = new ResponseEntity<>("Success", HttpStatus.OK);
        }
        return response;
    }


    @DeleteMapping("/account/profile")
    @ApiOperation(value = "회원 탈퇴")
    public Object changeUserProfile(){
        Authentication user = SecurityContextHolder.getContext().getAuthentication();
        ResponseEntity response = null;
        if(user.getPrincipal() == "anonymousUser"){
            response = new ResponseEntity<>("Fail", HttpStatus.UNAUTHORIZED);
        }else{
            UserDetails user2 = (UserDetails) user.getPrincipal();
            Optional<User> userOpt = userDao.findByEmail(user2.getUsername());
            userDao.deleteById(userOpt.get().getUid());
            response = new ResponseEntity<>("OK", HttpStatus.OK);
        }
        return response;
    }

    @GetMapping("/account/checkEmail")
    @ApiOperation(value = "이메일 중복 확인")
    public ResponseEntity<String> checkEmail(@RequestParam(required = true) String email){
        Optional<User> user = userDao.findByEmail(email);
        if(user.isPresent()){
            return new ResponseEntity<>("Fail", HttpStatus.IM_USED);
        }else{
            return new ResponseEntity<>("Success", HttpStatus.OK);
        }
    }

    @GetMapping("/account/checkNickname")
    @ApiOperation(value = "닉네임 중복 확인")
    public ResponseEntity<String> checkNickname(@RequestParam(required = true) String nickname){
        Optional<User> user = userDao.findByNickname(nickname);
        //
        if(user.isPresent()){
            return new ResponseEntity<>("Fail",HttpStatus.IM_USED);
        }else{
            return new ResponseEntity<>("Success", HttpStatus.OK);
        }
    }


    @GetMapping("/account/profile/{nickname}")
    @ApiOperation(value = "타 유저 피드 보기")
    public Object viewOtherFeed(@PathVariable("nickname") final String nickname){
        Authentication user = SecurityContextHolder.getContext().getAuthentication();
        ResponseEntity response = null;
        if(user.getPrincipal() == "anonymousUser"){
            response = new ResponseEntity<>("Fail", HttpStatus.UNAUTHORIZED);
        }else{
            UserDetails user2 = (UserDetails) user.getPrincipal();
            Optional<User> loginUser = userDao.findByEmail(user2.getUsername());
            Optional<User> otherUser = userDao.findByNickname(nickname);
            List<Article> article = null;
            if(!articleDao.existsArticleById(otherUser.get().getUid())){
                System.out.println("none");
            }else{
                article = articleDao.findAllById(otherUser.get().getUid());
            }
            boolean follow = false;
            Optional<Follow> followUser;
            Map result = new HashMap<String, Object>();
            if(!followDao.existsBySrcidAndDstid(loginUser.get().getUid(), otherUser.get().getUid())){
                follow = false;
            }else{
                follow = true;
                followUser = followDao.findBySrcidAndDstid(loginUser.get().getUid(), otherUser.get().getUid());
                result.put("followBoolean", followUser.get().getApprove());
            }
            result.put("follow", follow);
            result.put("userProfile", otherUser);
            result.put("article", article);
            response = new ResponseEntity<>(result, HttpStatus.OK);
        }
        return response;
    }

    @GetMapping("/account/profile/follow/{nickname}")
    @ApiOperation(value = "팔로잉 유무 확인")
    @ResponseBody
    public Object followingCheck(@PathVariable("nickname") final String othersNickname){
        Authentication user = SecurityContextHolder.getContext().getAuthentication();
        ResponseEntity response = null;
        if(user.getPrincipal() == "anonymousUser"){
            response = new ResponseEntity<>("Fail", HttpStatus.UNAUTHORIZED);
        }else{
            UserDetails user2 = (UserDetails) user.getPrincipal();
            Optional<User> me = userDao.findByEmail(user2.getUsername());
            Optional<User> otherUser = userDao.findByNickname(othersNickname);
            boolean otherToMe = false;
            Long followid = null;
            Long othersid = otherUser.get().getUid();
            Long myid = me.get().getUid();

            System.out.println("Srcid: " + othersid);
            System.out.println("Dstid: " + myid);
            if(!followDao.existsBySrcidAndDstidAndApprove(othersid, myid, false)){
                otherToMe = false;
                System.out.println("팔로잉 없음");
            }else{
                otherToMe = true;
                followid = followDao.findBySrcidAndDstid(othersid, myid).get().getFollowid();
            }

            Map result = new HashMap<String, Object>();
            result.put("otherToMe", otherToMe);
            result.put("followid", followid);

            response = new ResponseEntity<>(result, HttpStatus.OK);
        }
        return response;
    }
}