package com.web.curation.model.search;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Getter
@Table(name = "Search")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Search {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long searchid;
    @Column(name = "id", nullable = false)
    private Long id;
    @CreationTimestamp
    @Column(name = "searchdate", nullable = true)
    private LocalDateTime searchDate;
    @Column(name = "name", length = 255)
    private String name;

    private String thumbnail;

    @Builder
    public Search(Long searchid, Long id, LocalDateTime searchDate, String name, String thumbnail) {
        this.searchid = searchid;
        this.id = id;
        this.searchDate = searchDate;
        this.name = name;
        this.thumbnail = thumbnail;
    }

    @Builder
    public Search(Long searchid, Long id, LocalDateTime searchDate, String name) {
        this.searchid = searchid;
        this.id = id;
        this.searchDate = searchDate;
        this.name = name;
    }

    @Builder
    public Search(Long searchid, String name, String thumbnail){
        this.searchid = searchid;
        this.name = name;
        this.thumbnail = thumbnail;
    }

    public Search() {

    }
}
