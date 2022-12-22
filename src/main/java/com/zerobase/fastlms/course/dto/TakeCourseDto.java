package com.zerobase.fastlms.course.dto;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class TakeCourseDto {

    long id;
    long courseId;
    String userId;

    long payPrice;
    String status;

    //JOIN column
    String userName;
    String phone;
    String subject;

    long totalCount;
    long seq;
    LocalDateTime regDt;


}
