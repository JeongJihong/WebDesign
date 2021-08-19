package com.web.curation.model.comment;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class CommentResponse {

    private Long commentid;

    private Long articleid;
    private Long id;
    private String nickname;
    private LocalDateTime createdtime;
    private LocalDateTime updatedtime;
    private String comment;

    private String thumbnail;

}
