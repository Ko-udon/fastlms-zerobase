package com.zerobase.fastlms.course.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Lob;
import java.time.LocalDateTime;

@Data
public class CourseDto {

    Long id;
    String imagePath;
    String keyword;
    String subject;
    String summary;
    String contents;
    long price;
    long salePrice;
    LocalDateTime saleEndDt;
    LocalDateTime regDt; //등록일
    LocalDateTime udtDt; //수정일


    long totalCount;
    long seq;
}
