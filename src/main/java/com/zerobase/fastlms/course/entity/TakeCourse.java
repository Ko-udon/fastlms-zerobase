package com.zerobase.fastlms.course.entity;

import com.zerobase.fastlms.course.dto.TakeCourseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor

public class TakeCourse implements TakeCourseCode{





    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    long courseId;
    String userId;

    long payPrice; //결제금액
    String status; //상태(수강신청, 결제완료, 수강취소)

    
    LocalDateTime regDt; //신청일

}
