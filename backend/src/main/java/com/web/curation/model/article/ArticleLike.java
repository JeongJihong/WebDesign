package com.web.curation.model.article;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Table(name = "articlelike")
public class ArticleLike {
    @Id @Column(name = "articlelikeid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long articlelikeid;

    private Long id;
    private Long articleid;
}
