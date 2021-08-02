package com.web.curation.model.scrap;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;


@Entity
@Getter
@Table(name = "scrap")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class Scrap {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scrapid;

    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "articleid", nullable = false)
    private Long articleid;

}
