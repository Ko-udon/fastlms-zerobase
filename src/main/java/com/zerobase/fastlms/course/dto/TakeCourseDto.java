package com.zerobase.fastlms.course.dto;

import com.zerobase.fastlms.course.entity.TakeCourse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

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

    public static TakeCourseDto of(TakeCourse x) {
        return TakeCourseDto.builder()
            .id(x.getId())
            .courseId(x.getCourseId())
            .userId(x.getUserId())

            .payPrice(x.getPayPrice())
            .status(x.getStatus())

            .regDt(x.getRegDt())
            .build();
    }

    public String getRegDtText() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
        return regDt != null ? regDt.format(formatter) : "";
    }




}
