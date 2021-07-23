package com.web.curation.model.search;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;


@Entity
@Getter
@Table(name = "Search")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class Search {

    @Id
    private Long searchid;

    @Column(name = "id", nullable = false)
    private Long id;
    @CreationTimestamp
    @Column(name = "searchdate", nullable = true)
    private LocalDateTime searchDate;
    @Column(name = "name", length = 255)
    private String name;
}
