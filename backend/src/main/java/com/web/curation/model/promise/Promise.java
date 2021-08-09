package com.web.curation.model.promise;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class Promise {

    @Id
    @Column(name = "promiseid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long promiseid;

    private Long createruid;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime promisetime;

    @CreationTimestamp
    private LocalDateTime createdtime;

    private String type;
    private String place;
    private String title;
    private int num;
    private BigDecimal lat;
    private BigDecimal lon;
    private String nickname;
}
