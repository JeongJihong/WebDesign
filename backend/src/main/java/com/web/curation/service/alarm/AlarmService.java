package com.web.curation.service.alarm;

import com.web.curation.model.alarm.AlarmRequest;
import com.web.curation.model.alarm.LikeFollowRequest;

import java.util.List;
import java.util.Map;

public interface AlarmService {

    public void register(String token);
    public void sendAlarm(AlarmRequest request);
    public List<LikeFollowRequest> getLikeAlarm();
    public List<LikeFollowRequest> getFollowAlarm();
    public Map getPromiseAlarm();
}
