package com.zerobase.fastlms.course.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor

public class Course {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String imagePath;
    String keyword;
    String subject;

    @Column(length = 1000)
    String summary;

    @Lob
    String contents;
    long price;
    long salePrice;
    LocalDateTime saleEndDt;
    
    LocalDateTime regDt; //등록일
    LocalDateTime udtDt; //수정일

}
