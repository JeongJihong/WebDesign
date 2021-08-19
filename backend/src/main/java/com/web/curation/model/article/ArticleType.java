package com.web.curation.model.article;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.web.curation.model.comment.Comment;
import com.web.curation.model.image.Image;
import com.web.curation.model.user.User;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class ArticleType {
    private Long articleid;
    private Long id;
    private Long promiseid;

    @CreationTimestamp
    private LocalDateTime createdtime;

    @UpdateTimestamp
    private LocalDateTime updatedtime;
    private String review;

    @OneToMany(mappedBy = "article", cascade={CascadeType.ALL})
    private List<Image> images = new ArrayList<>();

    @OneToMany(mappedBy = "article", cascade={CascadeType.ALL})
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "id", insertable = false, updatable = false)
    private User user;
    String type;
}
