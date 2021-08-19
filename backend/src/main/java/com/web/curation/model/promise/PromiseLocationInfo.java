package com.web.curation.model.promise;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class PromiseLocationInfo {

    private String nickname;
    private String thumbnail;

    @UpdateTimestamp
    private LocalDateTime updatedtime;

    private BigDecimal lat;
    private BigDecimal lon;
}
