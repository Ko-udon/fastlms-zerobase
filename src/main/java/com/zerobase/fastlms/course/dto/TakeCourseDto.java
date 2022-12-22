package com.zerobase.fastlms.course.dto;

import lombok.Data;

import javax.persistence.*;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

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

    public String getRegDtText() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
        return regDt != null ? regDt.format(formatter) : "";
    }


}
