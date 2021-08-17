package com.web.curation.service.search;

import com.web.curation.model.search.Search;

import java.util.List;

public interface SearchService {
    public void saveSearch(Search request);
    public List<Search> list(String nickname);
    public void deleteSearch(Long searchid);
    public List<Search> searchList();
}
