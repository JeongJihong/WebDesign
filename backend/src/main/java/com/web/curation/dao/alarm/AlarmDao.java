package com.web.curation.dao.alarm;

import com.web.curation.model.alarm.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmDao extends JpaRepository<Alarm, Long> {


}
