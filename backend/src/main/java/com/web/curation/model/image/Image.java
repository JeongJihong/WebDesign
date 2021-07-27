package com.web.curation.model.image;

import lombok.*;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonInclude;

@Getter
@Entity
@Table(name = "image")
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class Image{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageid;

    private Long articleid;
    private String imgURL;
}
