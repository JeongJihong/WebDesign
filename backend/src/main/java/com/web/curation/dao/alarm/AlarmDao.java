package com.web.curation.dao.alarm;

import com.web.curation.model.alarm.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlarmDao extends JpaRepository<Alarm, Long> {
    List<Alarm> findAllByReceiveuidAndCategoryOrderByAlarmidDesc(Long receiveUid, String category);

}
