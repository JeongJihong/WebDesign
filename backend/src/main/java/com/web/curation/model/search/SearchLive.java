package com.web.curation.model.search;

import lombok.Getter;
import lombok.ToString;

import javax.validation.Valid;
import java.time.LocalDateTime;

@Valid
@ToString
@Getter
public class SearchLive {

    private Long id;
    private LocalDateTime searchDate;
    private String name;
    private String thumbnail;

    public SearchLive(Long id, String name, String thumbnail) {
        this.id = id;
        this.name = name;
        this.thumbnail = thumbnail;
    }

    public SearchLive(Long id, LocalDateTime searchDate, String name, String thumbnail) {
        this.id = id;
        this.searchDate = searchDate;
        this.name = name;
        this.thumbnail = thumbnail;
    }
}
