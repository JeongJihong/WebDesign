package com.web.curation.controller;

import com.web.curation.dao.search.SearchDao;
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
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@CrossOrigin(origins = { "http://localhost:3000" })
@RequiredArgsConstructor
@RestController
public class SearchController {

    @Autowired
    SearchDao searchDao;

    @PostMapping("/search")
    @ApiOperation(value = "검색 시 정보 저장")
    public ResponseEntity<String> saveSearch(@RequestBody Search request){
        Optional<User> userOpt = searchDao.findByName(request.getName());
        System.out.println(userOpt.get().getEmail());
//        searchDao.save(userOpt.get().getUid(), request.getId(),
//                LocalDateTime.now(), null, request.getName());
//        )
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
        List<Search> user = searchDao.findByNameIsContaining(nickname);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/search")
    @ApiOperation(value = "검색 버튼 클릭 시 최근 검색 기록")
    public ResponseEntity<List<LocalDateTime>> searchList(@RequestParam Long id){
        List<Search> list = searchDao.findById(id);
        List<Search> timeList = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
//            if(list.get(i).getUpdateTime() == null){
//                timeList.add(list.get(i));
//            }else{
//                timeList.add(list.get(i));
//            }
        }
//        Collections.sort(timeList);
//        return new ResponseEntity<>(timeList, HttpStatus.OK);
        return null;
    }
}
