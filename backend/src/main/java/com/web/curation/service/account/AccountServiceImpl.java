package com.web.curation.service.account;

import com.web.curation.config.security.JwtTokenProvider;
import com.web.curation.dao.article.ArticleDao;
import com.web.curation.dao.follow.FollowDao;
import com.web.curation.dao.user.UserDao;
import com.web.curation.model.article.Article;
import com.web.curation.model.follow.Follow;
import com.web.curation.model.user.*;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import java.io.File;
import java.io.IOException;
import java.util.*;

@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService{

    @Autowired
    private UserDao userDao;
    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private FollowDao followDao;

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    final String rootPath = System.getProperty("user.dir");
    String basePath = rootPath.substring(0, rootPath.length()-7) + "frontend/src/assets/images/";

    private Optional<User> Authentication() {
        Authentication user = SecurityContextHolder.getContext().getAuthentication();
        if(user.getPrincipal() == "anonymousUser"){
            throw new IllegalArgumentException("토큰이 불일치하거나 만료되었습니다.");
        }
        UserDetails user2 = (UserDetails) user.getPrincipal();
        Optional<User> userOpt = userDao.findByEmail(user2.getUsername());
        return userOpt;
    }
    public String saveFiles(MultipartFile files) throws IOException {
        String pathName = null;
        File Folder = new File(basePath);
        if(!Folder.exists()){
            try{
                Folder.mkdir();
            }catch (Exception e){
                e.getStackTrace();
            }
        }
        UUID uuid = null;

        uuid = UUID.randomUUID();
        String extension = FilenameUtils.getExtension(files.getOriginalFilename());
        String filename = 0 + "_" + uuid + "." + extension;
        String rename = basePath + filename;
        File dest = new File(rename);
        files.transferTo(dest);
        pathName = filename;


        return pathName;
    }


    @Override
    public String test() {
        return "hello";
    }

    @Override
    public String login(LoginRequest request) {
        User member = userDao.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-Mail 입니다."));
        if(!passwordEncoder.matches(request.getPassword(), member.getPassword())){
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        return jwtTokenProvider.createToken(member.getEmail(), member.getRoles());
    }

    @Override
    @Transactional
    public Long signup(SignupRequest request) {
        Long uid = null;
        if(request.getPassword().equals(null)){
            uid = userDao.save(User.builder()
                    .uid(null)
                    .introduction("")
                    .thumbnail(null)
                    .email(request.getEmail())
                    .nickname(request.getNickname())
                    .password(null)
                    .status(0L)
                    .roles(Collections.singletonList("ROLE_USER"))
                    .build()).getUid();
        }else{
            uid = userDao.save(User.builder()
                    .uid(null)
                    .introduction("")
                    .thumbnail(null)
                    .email(request.getEmail())
                    .nickname(request.getNickname())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .status(0L)
                    .roles(Collections.singletonList("ROLE_USER"))
                    .build()).getUid();
        }
        return uid;
    }

    @Override
    @Transactional
    public void changePassword(ChangePasswordRequest request) {
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
    }

    @Override
    public Map list() {
        Optional<User> userOpt = Authentication();
        Map result = new HashMap<String, Object>();
        result.put("uid", userOpt.get().getUid());
        result.put("nickname", userOpt.get().getNickname());
        result.put("email", userOpt.get().getUsername());
        return result;
    }

    @Override
    public Optional<User> getMyProfileInfo() {
        Optional<User> userOpt = Authentication();
        return userOpt;
    }

    @Override
    @Transactional
    public void changeUserProfile(String nickname, String introduction, MultipartFile thumbnail) {
        Optional<User> userOpt = Authentication();
        String pathName = null;
        if(thumbnail == null){
            pathName = userOpt.get().getThumbnail();
        }else{
            try {
                pathName = saveFiles(thumbnail);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        User user3 = new User(userOpt.get().getUid(), nickname, userOpt.get().getEmail(),
                userOpt.get().getPassword(), introduction, pathName,
                userOpt.get().getStatus(), userOpt.get().getAlarmtoken(), userOpt.get().getArticles(), userOpt.get().getRoles());
        userDao.save(user3);
    }

    @Override
    @Transactional
    public void deleteUserProfile() {
        Optional<User> userOpt = Authentication();
        userDao.deleteById(userOpt.get().getUid());
    }

    @Override
    public Boolean checkEmail(String email) {
        Optional<User> loginUser = userDao.findByEmail(email);
        if(loginUser.isPresent())
            return false;
        else
            return true;
    }

    @Override
    public Boolean checkNickname(String nickname) {
        Optional<User> loginUser = userDao.findByNickname(nickname);
        if(loginUser.isPresent())
            return false;
        else
            return true;
    }

    @Override
    public Map viewOtherFeed(String nickname) {
        Optional<User> loginUser = Authentication();
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
        return result;
    }

    @Override
    public Map followingCheck(String othersNickname) {
        Optional<User> loginUser = Authentication();
        Optional<User> otherUser = userDao.findByNickname(othersNickname);
        boolean otherToMe = false;
        Long followid = null;
        Long othersid = otherUser.get().getUid();
        Long myid = loginUser.get().getUid();

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
        return result;

    }

    @Override
    public Long getStatus(String nickname) {
        Optional<User> userOpt = Authentication();
        Long status = userOpt.get().getStatus();
        return status;
    }

    @Override
    @Transactional
    public void changeStatus(String nickname, Long status) {
        Optional<User> userOpt = Authentication();
        Long newStat = Math.max(0, userOpt.get().getStatus() + status);

        User user3 = new User(userOpt.get().getUid(), userOpt.get().getNickname(), userOpt.get().getEmail(),
                userOpt.get().getPassword(), userOpt.get().getIntroduction(), userOpt.get().getThumbnail(),
                newStat, userOpt.get().getAlarmtoken(), userOpt.get().getArticles(), userOpt.get().getRoles());
        userDao.save(user3);
    }
}
