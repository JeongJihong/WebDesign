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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long searchid;
    @Column(name = "id", nullable = false)
    private Long id;
    @CreationTimestamp
    @Column(name = "searchdate", nullable = true)
    private LocalDateTime searchDate;
    @Column(name = "name", length = 255)
    private String name;


    @Builder
    public Search(Long searchid, Long id, LocalDateTime searchDate, String name, String thumbnail) {
        this.searchid = searchid;
        this.id = id;
        this.searchDate = searchDate;
        this.name = name;
    }

    public Search() {

    }
}
