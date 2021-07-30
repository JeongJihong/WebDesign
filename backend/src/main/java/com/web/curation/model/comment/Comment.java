package com.web.curation.model.comment;

import lombok.*;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "comment")
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class Comment{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentid;

    private Long articleid;
    private Long id;
    @CreationTimestamp
    @Column(name = "createdtime", nullable = true)
    private LocalDateTime createdtime;
    @CreationTimestamp
    @Column(name = "updatedtime", nullable = true)
    private LocalDateTime updatedtime;
    private String comment;
}
