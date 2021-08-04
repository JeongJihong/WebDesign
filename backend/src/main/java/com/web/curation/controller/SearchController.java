package com.web.curation.controller;

import com.web.curation.dao.search.SearchDao;
import com.web.curation.dao.user.UserDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.search.Search;
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
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

//@CrossOrigin(origins = { "http://localhost:3000" })
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
public class SearchController {

    @Autowired
    SearchDao searchDao;
    @Autowired
    UserDao userDao;

    @PostMapping("/search")
    @ApiOperation(value = "검색 시 정보 저장")
    public ResponseEntity<String> saveSearch(@RequestBody Search request){
        Optional<User> userOpt = userDao.findByNickname(request.getName());
        System.out.println(userOpt.get().getUsername());
        searchDao.save(Search.builder()
                .searchid(userOpt.get().getUid())
                .id(request.getId())
                .searchDate(LocalDateTime.now())
                .name(request.getName())
                .build());
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @GetMapping("/search/live")
    @ApiOperation(value = "실시간으로 검색 창 결과 반환")
    public ResponseEntity<List<Search>> list(@RequestParam String nickname){
        List<User> user = userDao.findByNicknameIsContaining(nickname);
        List<Search> live = new ArrayList<>();
        for(int i = 0; i < user.size(); i++){
            live.add(new Search(user.get(i).getUid(), user.get(i).getNickname()));
        }

        return new ResponseEntity<>(live, HttpStatus.OK);
    }

    @DeleteMapping("/search")
    @ApiOperation(value = "최근 검색 삭제")
    public ResponseEntity<String> deleteSearch(@RequestParam Long searchid){
        Authentication user = SecurityContextHolder.getContext().getAuthentication();
        ResponseEntity response = null;
        if(user.getPrincipal() == "anonymousUser"){
            response = new ResponseEntity<>("Fail", HttpStatus.UNAUTHORIZED);
        }else{
            UserDetails user2 = (UserDetails) user.getPrincipal();
            Optional<User> userOpt = userDao.findByEmail(user2.getUsername());
            if(searchDao.deleteBySearchidAndId(searchid, userOpt.get().getUid()) == 0){
                response =  new ResponseEntity<>("Fail", HttpStatus.BAD_REQUEST);
            }else{
                response =  new ResponseEntity<>("Success", HttpStatus.OK);
            }
        }
        return response;
    }

    @GetMapping("/search")
    @ApiOperation(value = "검색 버튼 클릭 시 최근 검색 반환")
    public ResponseEntity<List<Search>> searchList(){
        Authentication user = SecurityContextHolder.getContext().getAuthentication();
        ResponseEntity response = null;
        if(user.getPrincipal() == "anonymousUser"){
            response = new ResponseEntity<>("Fail", HttpStatus.UNAUTHORIZED);
        }else{
            UserDetails user2 = (UserDetails) user.getPrincipal();
            Optional<User> userOpt = userDao.findByEmail(user2.getUsername());
            List<Search> list = searchDao.findById(userOpt.get().getUid());
            Collections.sort(list, Comparator.comparing(Search::getSearchDate));
            response = new ResponseEntity<>(list, HttpStatus.OK);
        }
        return response;

    }

}
