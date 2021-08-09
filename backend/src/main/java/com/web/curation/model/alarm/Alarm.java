package com.web.curation.model.alarm;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sun.tools.javah.Gen;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "alarm")
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class Alarm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long alarmid;

//    @Column(name = "receiveuid")
    private Long receiveuid;
//    @Column(name = "senderuid")
    private Long senderuid;
//    @Column(name = "title")
    private String title;
//    @Column(name = "body")
    private String body;
//    @Column(name = "check")
    private Boolean checkalarm;
//    @Column(name = "category")
    private String category;

    private Long detail;

//    @Builder
//    public Alarm( Long receiveuid, Long senderuid, String title, String body, Boolean check, String category){
//
//        this.receiveuid = receiveuid;
//        this.senderuid = senderuid;
//        this.title = title;
//        this.body = body;
//        this.check = check;
//        this.category = category;
//    }
//

}
