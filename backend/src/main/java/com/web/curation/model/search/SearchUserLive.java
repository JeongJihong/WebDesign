package com.web.curation.model.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import javax.validation.Valid;
import java.time.LocalDateTime;

@Valid
@ToString
@Getter
@AllArgsConstructor
public class SearchUserLive {

    private Long searchid;
    private String name;
    private String url;
    private LocalDateTime searchDate;
    private Long id;

    public SearchUserLive(Long searchid, String name, String url){
        this.searchid = searchid;
        this.name = name;
        this.url = url;
    }


}
