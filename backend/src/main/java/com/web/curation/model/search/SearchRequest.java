package com.web.curation.model.search;

import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.validation.Valid;
import java.time.LocalDateTime;

@Valid
@ToString
@Getter
public class SearchRequest {
    private Long id;
    private String name;
}
