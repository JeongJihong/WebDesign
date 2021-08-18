package com.web.curation.controller;

import com.web.curation.model.BasicResponse;
import com.web.curation.model.search.Search;
import com.web.curation.model.search.SearchLive;
import com.web.curation.service.search.SearchServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

//@CrossOrigin(origins = { "http://localhost:3000" })
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/search")
public class SearchController {

    private final SearchServiceImpl searchService;

    @PostMapping("")
    @ApiOperation(value = "검색 시 정보 저장")
    public ResponseEntity<String> saveSearch(@RequestBody Search request){
        searchService.saveSearch(request);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @GetMapping("/live")
    @ApiOperation(value = "실시간으로 검색 창 결과 반환")
    public ResponseEntity<List<SearchLive>> list(@RequestParam String nickname){
        List<SearchLive> result = searchService.list(nickname);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("")
    @ApiOperation(value = "최근 검색 삭제")
    public ResponseEntity<String> deleteSearch(@RequestParam Long searchid){
        searchService.deleteSearch(searchid);
        return new ResponseEntity<>("삭제 완료", HttpStatus.OK);
    }

    @GetMapping("")
    @ApiOperation(value = "검색 버튼 클릭 시 최근 검색 반환")
    public ResponseEntity<List<SearchLive>> searchList(){
        List<SearchLive> result = searchService.searchList();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
