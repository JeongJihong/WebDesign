package com.web.curation.service.search;

import com.web.curation.dao.search.SearchDao;
import com.web.curation.dao.user.UserDao;
import com.web.curation.model.search.Search;
import com.web.curation.model.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

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
    public void saveSearch(Search request) {
        if(!searchDao.existsByIdAndName(request.getId(), request.getName())){
            searchDao.save(Search.builder()
                    .searchid(null)
                    .id(request.getId())
                    .searchDate(LocalDateTime.now())
                    .name(request.getName())
                    .build());
        }
    }

    @Override
    public List<Search> list(String nickname) {
        List<User> user = userDao.findByNicknameIsContaining(nickname);
        List<Search> live = new ArrayList<>();
        for(int i = 0; i < user.size(); i++){
            live.add(new Search(user.get(i).getUid(), user.get(i).getNickname(), user.get(i).getThumbnail()));
        }
        return live;
    }

    @Override
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
        List<Search> result = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            Optional<User> user = userDao.findByNickname(list.get(i).getName());
            result.add(new Search(list.get(i).getSearchid(), list.get(i).getId(),
                    list.get(i).getSearchDate(), list.get(i).getName(), user.get().getThumbnail()));
        }

        Collections.sort(result, Comparator.comparing(Search::getSearchDate));
        return list;
    }
}
