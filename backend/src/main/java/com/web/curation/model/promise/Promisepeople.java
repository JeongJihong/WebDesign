package com.web.curation.model.promise;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class Promisepeople {

    @Id
    @Column(name = "promisepeopleid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long promisepeopleid;

    private Long uid;
    private Long promiseid;
    private Long createruid;

    @UpdateTimestamp
    private LocalDateTime updatedtime;

    private String nickname;
    private BigDecimal lat;
    private BigDecimal lon;
    private String thumbnail;
    private int approve;
}
