package com.web.curation.model.article;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long articleid;

    private String id;

    @CreationTimestamp
    private LocalDateTime createdtime;

    @UpdateTimestamp
    private LocalDateTime updatedtime;
    private String review;

//    @OneToMany
//    @JoinColumn(name="articleid")
//    private List<Image> images = new ArrayList<>();
//
//    @OneToMany
//    @JoinColumn(name="articleid")
//    private List<Comment> comments = new ArrayList<>();
//
//    @OneToMany
//    @JoinColumn(name="articleid")
//    private List<ArticleLike> articlelikes = new ArrayList<>();

//    @ManyToOne
//    @JoinColumn(name = "uid")
//    private User user;
}
