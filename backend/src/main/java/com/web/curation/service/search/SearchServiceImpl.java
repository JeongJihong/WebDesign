package com.web.curation.service.search;

import com.web.curation.dao.search.SearchDao;
import com.web.curation.dao.user.UserDao;
import com.web.curation.model.search.Search;
import com.web.curation.model.search.SearchUserLive;
import com.web.curation.model.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
@Service
public class SearchServiceImpl implements SearchService{

    @Autowired
    SearchDao searchDao;
    @Autowired
    UserDao userDao;

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
    public void saveSearch(Search request) {
        Optional<User> userOpt = userDao.findByNickname(request.getName());
        searchDao.save(Search.builder()
                .searchid(userOpt.get().getUid())
                .id(request.getId())
                .searchDate(LocalDateTime.now())
                .name(request.getName())
//                .thumbnail(userOpt.get().getThumbnail())
                .build());
    }

    @Override
    public List<SearchUserLive> list(String nickname) {
        List<User> user = userDao.findByNicknameIsContaining(nickname);
        List<SearchUserLive> live = new ArrayList<>();
        for(int i = 0; i < user.size(); i++){
            live.add(new SearchUserLive(user.get(i).getUid(), user.get(i).getNickname(), user.get(i).getThumbnail()));
        }
        return live;
    }

    @Override
    @Transactional
    public void deleteSearch(Long searchid) {
        Optional<User> userOpt = Authentication();
        if(searchDao.deleteBySearchidAndId(searchid, userOpt.get().getUid()) == 0){
            throw new IllegalArgumentException("삭제 실패 오류");
        }
    }

    @Override
    public List<Search> searchList() {
        Optional<User> userOpt = Authentication();
        List<Search> list = searchDao.findById(userOpt.get().getUid());
        List<SearchUserLive> result = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            Optional<User> user = userDao.findByUid(userOpt.get().getUid());
            result.add(new SearchUserLive(list.get(i).getSearchid(), list.get(i).getName(),
                    user.get().getThumbnail(), list.get(i).getSearchDate(), list.get(i).getId()));
        }
        Collections.sort(result, Comparator.comparing(SearchUserLive::getSearchDate));
        return list;
    }
}
