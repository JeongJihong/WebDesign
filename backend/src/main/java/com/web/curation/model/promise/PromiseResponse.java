package com.web.curation.model.promise;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class PromiseResponse {

    private Long promiseid;

    private String type;
    private String place;
    private int num;
    private int peopleNum;
    private String title;
    private LocalDateTime promisetime;
}
