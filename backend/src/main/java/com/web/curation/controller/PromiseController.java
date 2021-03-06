package com.web.curation.controller;

import com.web.curation.dao.alarm.AlarmDao;
import com.web.curation.dao.article.ArticleDao;
import com.web.curation.dao.follow.FollowDao;
import com.web.curation.dao.promise.PromiseDao;
import com.web.curation.dao.promise.PromisepeopleDao;
import com.web.curation.dao.scrap.ScrapDao;
import com.web.curation.dao.user.UserDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.alarm.Alarm;
import com.web.curation.model.article.Article;
import com.web.curation.model.article.ViewArticleRequest;
import com.web.curation.model.promise.Promise;
import com.web.curation.model.promise.PromiseLocationInfo;
import com.web.curation.model.promise.PromiseResponse;
import com.web.curation.model.promise.Promisepeople;
import com.web.curation.model.user.User;
import com.web.curation.service.alarm.NotificationService;
import com.web.curation.service.promise.PromiseServiceImpl;
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

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    @ApiOperation(value = "?????? ????????????")
    public ResponseEntity<Long> createPromise(@RequestBody Promise promise) {
        Long result = promiseService.createPromise(promise);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("")
    @ApiOperation(value = "?????? ?????? ?????? ????????????")
    public ResponseEntity<Map> getPromiseList() {
        Map result = promiseService.getPromiseList();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{promiseid}")
    @ApiOperation(value = "?????? ??????")
    public ResponseEntity<String> deletePromise(@PathVariable final Long promiseid){
        promiseService.deletePromise(promiseid);
        return new ResponseEntity<>("?????? ?????? ??????", HttpStatus.OK);
    }

    @GetMapping("/{promiseid}")
    @ApiOperation(value = "?????? ?????? ?????? ????????????")
    public ResponseEntity<Map> getPromiseList(@PathVariable(required = true) final Long promiseid) {
        Map result = promiseService.getPromiseList(promiseid);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/people/{promiseid}")
    @ApiOperation(value = "?????? ??????")
    public ResponseEntity<String > rejectPromise(@PathVariable final Long promiseid){
        promiseService.rejectPromise(promiseid);
        return new ResponseEntity<>("?????? ?????? ??????", HttpStatus.OK);
    }

    @PutMapping("/people/{promiseid}")
    @ApiOperation(value = "?????? ????????????")
    public ResponseEntity<Map> participatePromise(@PathVariable final Long promiseid) {
        Map result = promiseService.participatePromise(promiseid);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/place/{promiseid}")
    @ApiOperation(value = "?????? ?????? ???????????? ??????, ????????? ????????????")
    public ResponseEntity<Map> participatePromise(@PathVariable final Long promiseid,
                                     @RequestParam(required = true) BigDecimal lat, @RequestParam(required = true) BigDecimal lon) {
        Map result = promiseService.participatePromise(promiseid, lat, lon);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/place/{promiseid}")
    @ApiOperation(value = "?????? ??????????????? ????????????, ?????????")
    public ResponseEntity<List<PromiseLocationInfo>> locationInfo(@PathVariable final Long promiseid) {
        List<PromiseLocationInfo> result = promiseService.locationInfo(promiseid);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
