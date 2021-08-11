package com.web.curation.model.article;

import lombok.Getter;
import lombok.ToString;

import javax.validation.Valid;
import java.util.List;

@Valid
@ToString
@Getter
public class ArticleWrite {

    private String content;
    private List<String> files;
    private Long promiseid;
}
