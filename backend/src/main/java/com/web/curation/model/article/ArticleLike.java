package com.web.curation.model.article;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.web.curation.model.comment.Comment;
import com.web.curation.model.image.Image;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.repository.Query;

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
@Table(name = "articlelike")
public class ArticleLike {
    @Id @Column(name = "articlelikeid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long articlelikeid;

    private Long id;
    private Long articleid;
}
