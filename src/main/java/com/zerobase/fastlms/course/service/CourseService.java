package com.zerobase.fastlms.course.service;

import com.zerobase.fastlms.course.dto.CourseDto;
import com.zerobase.fastlms.course.model.CourseInput;
import com.zerobase.fastlms.course.model.CourseParam;

import java.util.List;

public interface CourseService {

    //강좌 등록
    boolean add(CourseInput parameter);

    List<CourseDto> list(CourseParam parameter);  //강좌 목록

    CourseDto getById(long id);  //강좌 상세 정보

    boolean set(CourseInput parameter);  //강좌 정보 수정
}
