package com.zerobase.fastlms.admin.banner.service;

import com.zerobase.fastlms.admin.banner.dto.BannerDto;
import com.zerobase.fastlms.admin.banner.model.BannerInput;
import com.zerobase.fastlms.admin.banner.model.BannerParam;
import com.zerobase.fastlms.course.dto.CourseDto;
import com.zerobase.fastlms.course.model.CourseInput;
import com.zerobase.fastlms.course.model.CourseParam;
import java.util.List;

public interface BannerService {
  BannerDto getById(long id);  //강좌 상세 정보

  boolean set(BannerInput parameter);

  boolean add(BannerInput parameter);

  List<BannerDto> list(BannerParam parameter);  //배너 목록

  boolean del(String idList);  //배너 내용 삭제
}
