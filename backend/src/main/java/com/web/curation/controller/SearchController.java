package com.web.curation.controller;

import com.web.curation.dao.search.SearchDao;
import com.web.curation.dao.user.UserDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.search.Search;
import com.web.curation.model.user.User;
import com.web.curation.service.search.SearchServiceImpl;
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

    private final SearchServiceImpl searchService;

    @PostMapping("/search")
    @ApiOperation(value = "검색 시 정보 저장")
    public ResponseEntity<String> saveSearch(@RequestBody Search request){
        searchService.saveSearch(request);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @GetMapping("/search/live")
    @ApiOperation(value = "실시간으로 검색 창 결과 반환")
    public ResponseEntity<List<Search>> list(@RequestParam String nickname){
        List<Search> result = searchService.list(nickname);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/search")
    @ApiOperation(value = "최근 검색 삭제")
    public ResponseEntity<String> deleteSearch(@RequestParam Long searchid){
        searchService.deleteSearch(searchid);
        return new ResponseEntity<>("삭제 완료", HttpStatus.OK);
    }

    @GetMapping("/search")
    @ApiOperation(value = "검색 버튼 클릭 시 최근 검색 반환")
    public ResponseEntity<List<Search>> searchList(){
        List<Search> result = searchService.searchList();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
