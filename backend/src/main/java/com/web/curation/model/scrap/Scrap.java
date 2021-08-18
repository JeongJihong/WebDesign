package com.web.curation.model.scrap;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;


@Entity
@Getter
@Table(name = "scrap")
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class Scrap {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long scrapid;

    private Long id;
    private Long articleid;
    private String thumnailURL;

}
