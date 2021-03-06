package com.web.curation.service.promise;

import com.web.curation.dao.alarm.AlarmDao;
import com.web.curation.dao.article.ArticleDao;
import com.web.curation.dao.follow.FollowDao;
import com.web.curation.dao.promise.PromiseDao;
import com.web.curation.dao.promise.PromisepeopleDao;
import com.web.curation.dao.scrap.ScrapDao;
import com.web.curation.dao.user.UserDao;
import com.web.curation.model.alarm.Alarm;
import com.web.curation.model.article.Article;
import com.web.curation.model.promise.Promise;
import com.web.curation.model.promise.PromiseLocationInfo;
import com.web.curation.model.promise.PromiseResponse;
import com.web.curation.model.promise.Promisepeople;
import com.web.curation.model.user.User;
import com.web.curation.service.alarm.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class PromiseServiceImpl implements PromiseService{

    private final NotificationService notificationService;

    @Autowired
    ArticleDao articleDao;

    @Autowired
    ScrapDao scrapDao;

    @Autowired
    UserDao userDao;

    @Autowired
    PromiseDao promiseDao;

    @Autowired
    PromisepeopleDao promisePeopleDao;

    @Autowired
    FollowDao followDao;

    @Autowired
    AlarmDao alarmDao;

    public Optional<User> Authentication() {
        Authentication user = SecurityContextHolder.getContext().getAuthentication();
        if (user.getPrincipal() == "anonymousUser") {
            throw new IllegalArgumentException("????????? ?????????????????? ?????????????????????.");
        }
        UserDetails user2 = (UserDetails) user.getPrincipal();
        Optional<User> userOpt = userDao.findByEmail(user2.getUsername());
        return userOpt;
    }

    @Override
    public Long createPromise(Promise promise) {
        Optional<User> userOpt = Authentication();
        // ?????? ?????? Promise table??? ??????
        Long promiseID =  promiseDao.save(Promise.builder()
                .createruid(userOpt.get().getUid())
                .promisetime(promise.getPromisetime())
                .type(promise.getType())
                .place(promise.getPlace())
                .title(promise.getTitle())
                .num(promise.getNum())
                .lat(promise.getLat())
                .lon(promise.getLon())
                .nickname(userOpt.get().getNickname())
                .build()).getPromiseid();

        // ?????? ???????????? ?????? ?????????/???????????? UID ??????
        List<Long> allFollowingFollower = followDao.findBySrcidAndApprove(userOpt.get().getUid());
        // ?????????/????????? PromisePeople table??? ??????
        for (int i = 0; i < allFollowingFollower.size(); i++) {
            User invitedUser = userDao.findByUid(allFollowingFollower.get(i)).get();
            promisePeopleDao.save(Promisepeople.builder()
                    .uid(allFollowingFollower.get(i))
                    .promiseid(promiseID)
                    .createruid(userOpt.get().getUid())
                    .nickname(invitedUser.getNickname())
                    .thumbnail(invitedUser.getThumbnail())
                    .approve(0)
                    .build());

            //????????? ??????????????? ?????? ?????? ??????
            Long alarmId = alarmDao.save(Alarm.builder()
                    .alarmid(null)
                    .receiveuid(invitedUser.getUid())
                    .senderuid(userOpt.get().getUid())
                    .title("Promise")
                    .body(userOpt.get().getNickname()+"??? ????????? ?????????????????????.")
                    .checkalarm(false)
                    .category("Promise")
                    .detail(promiseID).build()).getAlarmid();

            notificationService.sendNotification(alarmDao.getOne(alarmId), invitedUser.getAlarmtoken());
        }
        // ?????? ????????? ????????? PromisePeople table??? ??????
        User createrUser = userDao.findByUid(userOpt.get().getUid()).get();
        promisePeopleDao.save(Promisepeople.builder()
                .uid(userOpt.get().getUid())
                .promiseid(promiseID)
                .createruid(userOpt.get().getUid())
                .nickname(createrUser.getNickname())
                .thumbnail(createrUser.getThumbnail())
                .approve(1)
                .build());
        return promiseID;
    }

    @Override
    public Map getPromiseList() {
        Optional<User> userOpt = Authentication();
        // ?????? ???????????? ?????? ?????? ?????? Promise??? List
        List<Promise> waitingList = promiseDao.waitingPromise(userOpt.get().getUid());
        // List<Promise>??? peopleNum??? ????????? List<PromiseResponse>??? ??????
        Stream<Promise> waitingStream = waitingList.stream();
        List<PromiseResponse> waiting = waitingStream.map(promise -> new PromiseResponse(promise.getPromiseid(),
                promise.getType(), promise.getPlace(),promise.getNum(), promisePeopleDao.findAllByPromiseidAndApprove(promise.getPromiseid(), 1).size(),
                promise.getTitle(), promise.getPromisetime()))
                .collect(Collectors.toList());

        // ?????? ?????? ????????? ???????????? ?????? ????????? ?????????
        for (int i = 0; i < waiting.size(); i++) {
            if(waiting.get(i).getNum() <= waiting.get(i).getPeopleNum()) {
                waiting.remove(waiting.get(i));
            }
        }

        // ?????? ????????? ????????? ?????? ??????
        List<Promisepeople> upcomingList = promisePeopleDao.upcomingPromise(userOpt.get().getUid());

        // List<Promisepeople>??? peopleNum??? ????????? List<PromiseResponse>??? ??????
        Stream<Promisepeople> upcomingStream = upcomingList.stream();
        List<PromiseResponse> upcoming = upcomingStream.map(promisepeople -> new PromiseResponse(promisepeople.getPromiseid(),
                promiseDao.findByPromiseid(promisepeople.getPromiseid()).getType(),
                promiseDao.findByPromiseid(promisepeople.getPromiseid()).getPlace(),
                promiseDao.findByPromiseid(promisepeople.getPromiseid()).getNum(),
                promisePeopleDao.findAllByPromiseidAndApprove(promisepeople.getPromiseid(), 1).size(),
                promiseDao.findByPromiseid(promisepeople.getPromiseid()).getTitle(),
                promiseDao.findByPromiseid(promisepeople.getPromiseid()).getPromisetime()))
                .collect(Collectors.toList());

        Map result = new HashMap<String, Object>();
        result.put("waiting", waiting);
        result.put("upcoming", upcoming);
        return result;
    }

    @Override
    public void deletePromise(Long promiseid) {
        Optional<User> userOpt = Authentication();
        // ?????? ?????? ????????? ??? ????????? ?????? ???????????? UID??? ?????????(?????? ?????? ??????)
        if(userOpt.get().getUid() == promiseDao.findByPromiseid(promiseid).getCreateruid()) {
            promisePeopleDao.deleteAllByPromiseid(promiseid);
            articleDao.deleteByPromiseid(promiseid);
            promiseDao.deleteByPromiseid(promiseid);
        } else {
            throw new IllegalArgumentException("?????? ?????? ?????? ??????.");
        }

    }

    @Override
    public Map getPromiseList(Long promiseid) {
        Optional<User> userOpt = Authentication();
        // ?????? ???????????? ?????? ??????
        Promise promise = promiseDao.findByPromiseid(promiseid);
        Article article = articleDao.findByPromiseid(promiseid);

        // ?????? ????????? ????????? ?????? ??????
        Promisepeople myPromise = promisePeopleDao.findByPromiseidAndUid(promiseid, userOpt.get().getUid());
        int approve = -1;
        if(myPromise != null) {
            approve = myPromise.getApprove();
        }
        // ?????? ????????? ???????????? ??????
        List<Promisepeople> promisepeopleList = promisePeopleDao.findAllByPromiseidAndApprove(promiseid, 1);

        Boolean isCreater = false;
        // ?????? ?????? ?????? ????????? ???????????????
        if(userOpt.get().getUid() == promise.getCreateruid()) {
            isCreater = true;
        }

        Map result = new HashMap<String, Object>();
        result.put("isCreater", isCreater);
        result.put("title", promise.getTitle());
        result.put("type", promise.getType());
        result.put("promisetime", promise.getPromisetime());
        result.put("num", promise.getNum());
        result.put("createrUid", promise.getCreateruid());
        result.put("createrNickname", promise.getNickname());
        result.put("peopleNum", promisepeopleList.size());
        result.put("promisePeople", promisepeopleList);
        result.put("place", promise.getPlace());
        result.put("lat", promise.getLat());
        result.put("lon", promise.getLon());
        result.put("approve", approve);
        result.put("articleId", article.getArticleid());
        return result;
    }

    @Override
    public void rejectPromise(Long promiseid) {
        Optional<User> userOpt = Authentication();
        promisePeopleDao.deleteByPromiseidAndUid(promiseid, userOpt.get().getUid());
    }

    @Override
    public Map participatePromise(Long promiseid) {
        Optional<User> userOpt = Authentication();
        Promisepeople promisepeople = promisePeopleDao.findByPromiseidAndUid(promiseid, userOpt.get().getUid());
        Promisepeople newpeople = new Promisepeople(promisepeople.getPromisepeopleid(),
                promisepeople.getUid(), promisepeople.getPromiseid(), promisepeople.getCreateruid(),
                promisepeople.getUpdatedtime(), promisepeople.getNickname(), promisepeople.getLat(),
                promisepeople.getLon(), promisepeople.getThumbnail(), 1);

        promisePeopleDao.save(newpeople);

        Map result = new HashMap<String, Object>();
        result.put("nickname", newpeople.getNickname());
        result.put("updatedtime",newpeople.getUpdatedtime());
        result.put("lat", newpeople.getLat());
        result.put("lon", newpeople.getLon());
        return result;
    }

    @Override
    public Map participatePromise(Long promiseid, BigDecimal lat, BigDecimal lon) {
        Optional<User> userOpt = Authentication();
        Promisepeople promisepeople = promisePeopleDao.findByPromiseidAndUid(promiseid, userOpt.get().getUid());
        Promisepeople newpeople = new Promisepeople(promisepeople.getPromisepeopleid(),
                promisepeople.getUid(), promisepeople.getPromiseid(), promisepeople.getCreateruid(),
                promisepeople.getUpdatedtime(), promisepeople.getNickname(), lat,
                lon, promisepeople.getThumbnail(), promisepeople.getApprove());

        promisePeopleDao.save(newpeople);

        Map result = new HashMap<String, Object>();
        result.put("lat", newpeople.getLat());
        result.put("lon", newpeople.getLon());
        return result;
    }

    @Override
    public List<PromiseLocationInfo> locationInfo(Long promiseid) {
        Optional<User> userOpt = Authentication();
        List<Promisepeople> participationList = promisePeopleDao.findAllByPromiseidAndApprove(promiseid, 1);
        Stream<Promisepeople> participationStream = participationList.stream();
        List<PromiseLocationInfo> locationInfoList =participationStream.map(promisepeople -> new PromiseLocationInfo(
                promisepeople.getNickname(), promisepeople.getThumbnail(), promisepeople.getUpdatedtime(),
                promisepeople.getLat(), promisepeople.getLon()
        )).collect(Collectors.toList());
        return locationInfoList;
    }
}
