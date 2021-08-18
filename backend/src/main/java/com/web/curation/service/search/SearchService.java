package com.web.curation.service.search;

import com.web.curation.model.search.SearchLive;
import com.web.curation.model.search.SearchRequest;

import java.util.List;

public interface SearchService {
    public void saveSearch(SearchRequest request);
    public List<SearchLive> list(String nickname);
    public void deleteSearch(Long searchid);
    public List<SearchLive> searchList();
}
