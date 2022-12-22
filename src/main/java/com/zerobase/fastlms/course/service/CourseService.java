package com.zerobase.fastlms.course.service;

import com.zerobase.fastlms.course.dto.CourseDto;
import com.zerobase.fastlms.course.model.CourseInput;
import com.zerobase.fastlms.course.model.CourseParam;
import com.zerobase.fastlms.course.model.ServiceResult;
import com.zerobase.fastlms.course.model.TakeCourseInput;

import java.util.List;

public interface CourseService {

    //강좌 등록
    boolean add(CourseInput parameter);

    List<CourseDto> list(CourseParam parameter);  //강좌 목록

    CourseDto getById(long id);  //강좌 상세 정보

    boolean set(CourseInput parameter);  //강좌 정보 수정

    boolean del(String idList);  //강좌 내용 삭제

    List<CourseDto> frontList(CourseParam courseParam);  //프론트 강좌 목록


    CourseDto frontDetail(long id);  //프론트 강좌 상세 정보

    //boolean req(TakeCourseInput parameter);  //수강 신청
    ServiceResult req(TakeCourseInput parameter);
}
