package com.web.curation.service.promise;

import com.web.curation.model.promise.Promise;
import com.web.curation.model.promise.PromiseLocationInfo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface PromiseService {
    public Long createPromise(Promise promise);
    public Map getPromiseList();
    public void deletePromise(Long promiseid);
    public Map getPromiseList(Long promiseid);
    public void rejectPromise(Long promiseid);
    public Map participatePromise(Long promiseid);
    public Map participatePromise(Long promiseid, BigDecimal lat, BigDecimal lon);
    public List<PromiseLocationInfo> locationInfo(Long promiseid);
}
