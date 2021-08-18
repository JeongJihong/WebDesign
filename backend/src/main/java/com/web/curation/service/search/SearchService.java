package com.web.curation.service.search;

import com.web.curation.model.search.Search;
import com.web.curation.model.search.SearchLive;

import java.util.List;

public interface SearchService {
    public void saveSearch(Search request);
    public List<SearchLive> list(String nickname);
    public void deleteSearch(Long searchid);
    public List<SearchLive> searchList();
}
