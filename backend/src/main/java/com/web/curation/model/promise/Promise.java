package com.web.curation.model.promise;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.web.curation.model.comment.Comment;
import com.web.curation.model.image.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private LocalDateTime promisetime;

    @CreationTimestamp
    private LocalDateTime createdtime;

    private String type;
    private String place;
    private String title;
    private Long num;
    private BigDecimal lat;
    private BigDecimal lon;
    private String nickname;
}
