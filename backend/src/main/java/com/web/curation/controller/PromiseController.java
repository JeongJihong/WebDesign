package com.web.curation.controller;

import com.web.curation.model.BasicResponse;
import com.web.curation.model.promise.PromiseLocationInfo;
import com.web.curation.model.promise.PromiseRequest;
import com.web.curation.service.promise.PromiseServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

//@CrossOrigin(origins = { "http://localhost:3000" })
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/promise")
public class PromiseController {

    private final PromiseServiceImpl promiseService;

    @PostMapping("")
    @ApiOperation(value = "약속 생성하기")
    public ResponseEntity<Long> createPromise(@RequestBody PromiseRequest promise) {
        Long result = promiseService.createPromise(promise);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("")
    @ApiOperation(value = "약속 전체 목록 확인하기")
    public ResponseEntity<Map> getPromiseList() {
        Map result = promiseService.getPromiseList();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{promiseid}")
    @ApiOperation(value = "약속 삭제")
    public ResponseEntity<String> deletePromise(@PathVariable final Long promiseid){
        promiseService.deletePromise(promiseid);
        return new ResponseEntity<>("약속 삭제 완료", HttpStatus.OK);
    }

    @GetMapping("/{promiseid}")
    @ApiOperation(value = "특정 약속 정보 확인하기")
    public ResponseEntity<Map> getPromiseList(@PathVariable(required = true) final Long promiseid) {
        Map result = promiseService.getPromiseList(promiseid);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/people/{promiseid}")
    @ApiOperation(value = "약속 불참")
    public ResponseEntity<String > rejectPromise(@PathVariable final Long promiseid){
        promiseService.rejectPromise(promiseid);
        return new ResponseEntity<>("약속 불참 완료", HttpStatus.OK);
    }

    @PutMapping("/people/{promiseid}")
    @ApiOperation(value = "약속 참가하기")
    public ResponseEntity<Map> participatePromise(@PathVariable final Long promiseid) {
        Map result = promiseService.participatePromise(promiseid);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/place/{promiseid}")
    @ApiOperation(value = "특정 약속 참가자의 위도, 경도를 업데이트")
    public ResponseEntity<Map> participatePromise(@PathVariable final Long promiseid,
                                     @RequestParam(required = true) BigDecimal lat, @RequestParam(required = true) BigDecimal lon) {
        Map result = promiseService.participatePromise(promiseid, lat, lon);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/place/{promiseid}")
    @ApiOperation(value = "약속 참가자들의 최근위치, 목적지")
    public ResponseEntity<List<PromiseLocationInfo>> locationInfo(@PathVariable final Long promiseid) {
        List<PromiseLocationInfo> result = promiseService.locationInfo(promiseid);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
