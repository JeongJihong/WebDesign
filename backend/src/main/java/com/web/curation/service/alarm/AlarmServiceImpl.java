package com.web.curation.service.alarm;

import com.web.curation.dao.alarm.AlarmDao;
import com.web.curation.dao.promise.PromiseDao;
import com.web.curation.dao.user.UserDao;
import com.web.curation.model.alarm.Alarm;
import com.web.curation.model.alarm.AlarmRequest;
import com.web.curation.model.alarm.LikeFollowRequest;
import com.web.curation.model.promise.Promise;
import com.web.curation.model.user.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@AllArgsConstructor
@Service
public class AlarmServiceImpl implements AlarmService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private AlarmDao alarmDao;

    @Autowired
    private PromiseDao promiseDao;

    private final NotificationService notificationService;

    public Optional<User> Authentication() {
        Authentication user = SecurityContextHolder.getContext().getAuthentication();
        if (user.getPrincipal() == "anonymousUser") {
            throw new IllegalArgumentException("토큰이 불일치하거나 만료되었습니다.");
        }
        UserDetails user2 = (UserDetails) user.getPrincipal();
        Optional<User> userOpt = userDao.findByEmail(user2.getUsername());
        return userOpt;
    }

    @Override
    @Transactional
    public void register(String token) {
        Optional<User> userOpt = Authentication();
        User user3 = new User(userOpt.get().getUid(), userOpt.get().getNickname(), userOpt.get().getEmail(),
                userOpt.get().getPassword(), userOpt.get().getIntroduction(), userOpt.get().getThumbnail(),
                userOpt.get().getStatus(), token, userOpt.get().getArticles(), userOpt.get().getRoles());
        userDao.save(user3);
    }

    @Override
    public void sendAlarm(AlarmRequest request) {
        Optional<User> sender = Authentication();
        Optional<User> receiver = userDao.findByNickname(request.getReceiverNickname());
        Long alarmId = alarmDao.save(Alarm.builder()
                .alarmid(null)
                .receiveuid(receiver.get().getUid())
                .senderuid(sender.get().getUid())
                .title(request.getTitle())
                .body(request.getBody())
                .checkalarm(false)
                .category(request.getCategory())
                .detail(request.getDetail()).build()
        ).getAlarmid();
        notificationService.sendNotification(alarmDao.getOne(alarmId), receiver.get().getAlarmtoken());
    }

    @Override
    public List<LikeFollowRequest> getLikeAlarm() {
        Optional<User> loginUser = Authentication();
        List<Alarm> alarm = alarmDao.findAllByReceiveuidAndCategory(loginUser.get().getUid(), "Like");
        List<LikeFollowRequest> result = new ArrayList<>();
        for (int i = 0; i < alarm.size(); i++) {
            Optional<User> tempUser = userDao.findByUid(alarm.get(i).getSenderuid());
            result.add(new LikeFollowRequest(tempUser.get().getUid(), tempUser.get().getNickname(),
                    tempUser.get().getThumbnail(), alarm.get(i).getTitle(), alarm.get(i).getBody(),
                    alarm.get(i).getCheckalarm(), alarm.get(i).getCategory(), alarm.get(i).getDetail()));
        }
        return result;
    }

    @Override
    public List<LikeFollowRequest> getFollowAlarm() {
        Optional<User> loginUser = Authentication();
        List<Alarm> alarm = alarmDao.findAllByReceiveuidAndCategory(loginUser.get().getUid(), "Follow");
        List<LikeFollowRequest> result = new ArrayList<>();
        for (int i = 0; i < alarm.size(); i++) {
            Optional<User> tempUser = userDao.findByUid(alarm.get(i).getSenderuid());
            result.add(new LikeFollowRequest(tempUser.get().getUid(), tempUser.get().getNickname(),
                    tempUser.get().getThumbnail(), alarm.get(i).getTitle(), alarm.get(i).getBody(),
                    alarm.get(i).getCheckalarm(), alarm.get(i).getCategory(), null));
        }
        return result;
    }

    @Override
    public Map getPromiseAlarm() {
        Optional<User> loginUser = Authentication();
        List<Alarm> alarm = alarmDao.findAllByReceiveuidAndCategory(loginUser.get().getUid(), "Promise");
        Map mapResult = new HashMap<String, Object>();
        List<LikeFollowRequest> Game = new ArrayList<>();
        List<LikeFollowRequest> Travel = new ArrayList<>();
        List<LikeFollowRequest> Restaurant = new ArrayList<>();
        List<LikeFollowRequest> Exercise = new ArrayList<>();
        List<LikeFollowRequest> Study = new ArrayList<>();
        List<LikeFollowRequest> Art = new ArrayList<>();
        List<LikeFollowRequest> Etc = new ArrayList<>();
        //Game, Travel, Restaurant, Exercise, Study, Art, Etc
        for (int i = 0; i < alarm.size(); i++) {
            Optional<User> tempUser = userDao.findByUid(alarm.get(i).getSenderuid());
            Promise promiseUser = promiseDao.findByPromiseid(alarm.get(0).getDetail());
            String type = promiseUser.getType();
            if (type.equals("Game")) {
                Game.add(new LikeFollowRequest(tempUser.get().getUid(), tempUser.get().getNickname(),
                        tempUser.get().getThumbnail(), alarm.get(i).getTitle(), alarm.get(i).getBody(),
                        alarm.get(i).getCheckalarm(), alarm.get(i).getCategory(), alarm.get(i).getDetail()));
            } else if (type.equals("Travel")) {
                Travel.add(new LikeFollowRequest(tempUser.get().getUid(), tempUser.get().getNickname(),
                        tempUser.get().getThumbnail(), alarm.get(i).getTitle(), alarm.get(i).getBody(),
                        alarm.get(i).getCheckalarm(), alarm.get(i).getCategory(), alarm.get(i).getDetail()));
            } else if (type.equals("Restaurant")) {
                Restaurant.add(new LikeFollowRequest(tempUser.get().getUid(), tempUser.get().getNickname(),
                        tempUser.get().getThumbnail(), alarm.get(i).getTitle(), alarm.get(i).getBody(),
                        alarm.get(i).getCheckalarm(), alarm.get(i).getCategory(), alarm.get(i).getDetail()));
            } else if (type.equals("Exercise")) {
                Exercise.add(new LikeFollowRequest(tempUser.get().getUid(), tempUser.get().getNickname(),
                        tempUser.get().getThumbnail(), alarm.get(i).getTitle(), alarm.get(i).getBody(),
                        alarm.get(i).getCheckalarm(), alarm.get(i).getCategory(), alarm.get(i).getDetail()));
            } else if (type.equals("Study")) {
                Study.add(new LikeFollowRequest(tempUser.get().getUid(), tempUser.get().getNickname(),
                        tempUser.get().getThumbnail(), alarm.get(i).getTitle(), alarm.get(i).getBody(),
                        alarm.get(i).getCheckalarm(), alarm.get(i).getCategory(), alarm.get(i).getDetail()));
            } else if (type.equals("Art")) {
                Art.add(new LikeFollowRequest(tempUser.get().getUid(), tempUser.get().getNickname(),
                        tempUser.get().getThumbnail(), alarm.get(i).getTitle(), alarm.get(i).getBody(),
                        alarm.get(i).getCheckalarm(), alarm.get(i).getCategory(), alarm.get(i).getDetail()));
            } else {
                Etc.add(new LikeFollowRequest(tempUser.get().getUid(), tempUser.get().getNickname(),
                        tempUser.get().getThumbnail(), alarm.get(i).getTitle(), alarm.get(i).getBody(),
                        alarm.get(i).getCheckalarm(), alarm.get(i).getCategory(), alarm.get(i).getDetail()));
            }
        }
        mapResult.put("Game", Game);
        mapResult.put("Travel", Travel);
        mapResult.put("Restaurant", Restaurant);
        mapResult.put("Exercise", Exercise);
        mapResult.put("Study", Study);
        mapResult.put("Art", Art);
        mapResult.put("Etc", Etc);

        return mapResult;
    }
}
