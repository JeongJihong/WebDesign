package com.web.curation.model.image;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.web.curation.model.article.Article;
import com.web.curation.model.user.User;
import lombok.*;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.File;

@Getter
@Entity
@Table(name = "image")
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class Image{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageid;

    private Long articleid;
    private String imgURL;

    @ManyToOne @JsonIgnore
    @JoinColumn(name = "articleid", insertable = false, updatable = false)
    private Article article;
}
