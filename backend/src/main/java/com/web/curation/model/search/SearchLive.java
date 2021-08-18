package com.web.curation.model.search;

import lombok.Getter;
import lombok.ToString;

import javax.validation.Valid;
import java.time.LocalDateTime;

@Valid
@ToString
@Getter
public class SearchLive {

    private Long searchid;
    private LocalDateTime searchDate;
    private String name;
    private String thumbnail;

    public SearchLive(Long searchid, String name, String thumbnail) {
        this.searchid = searchid;
        this.name = name;
        this.thumbnail = thumbnail;
    }

    public SearchLive(Long searchid, LocalDateTime searchDate, String name, String thumbnail) {
        this.searchid = searchid;
        this.searchDate = searchDate;
        this.name = name;
        this.thumbnail = thumbnail;
    }
}
