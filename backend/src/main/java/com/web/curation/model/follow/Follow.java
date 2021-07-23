package com.web.curation.model.follow;

import lombok.*;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonInclude;

@Getter
@Entity
@Table(name = "follow")
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class Follow{
    private Long srcid;
    private Long dstid;
    private Boolean approve;
}
