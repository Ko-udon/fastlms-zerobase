package com.zerobase.fastlms.course.service;

import com.zerobase.fastlms.course.dto.TakeCourseDto;
import com.zerobase.fastlms.course.model.TakeCourseParam;

import java.util.List;

public interface TakeCourseService {



    List<TakeCourseDto> list(TakeCourseParam parameter);  //수강 목록


}