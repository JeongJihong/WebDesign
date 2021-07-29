package com.web.curation.model.article;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.web.curation.model.comment.Comment;
import com.web.curation.model.image.Image;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class PostArticleRequest {   // 게시글 포스팅할 때 필요한 정보
    @Id @Column(name = "articleid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long articleid;
    private String content;

    @OneToMany(cascade={CascadeType.ALL})
    @JoinColumn(name="articleid", insertable = false)
    private List<Image> images = new ArrayList<>();
}