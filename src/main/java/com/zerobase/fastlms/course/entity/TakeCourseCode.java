package com.zerobase.fastlms.course.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;


public interface TakeCourseCode {

    String STATUS_REQ="REQ"; //수강 신청 상태
    String STATUS_COMPLETE="COMPLETE"; //결제 완료 상태
    String STATUS_CANCEL="CANCEL"; //수강 취소





}
