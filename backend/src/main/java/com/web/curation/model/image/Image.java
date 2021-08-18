package com.web.curation.model.image;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.web.curation.model.article.Article;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "image")
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class Image{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long imageid;


    private Long articleid;
    private String imgURL;

    @ManyToOne @JsonIgnore
    @JoinColumn(name = "articleid", insertable = false, updatable = false)
    private Article article;
}
