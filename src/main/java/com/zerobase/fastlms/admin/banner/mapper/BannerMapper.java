package com.zerobase.fastlms.admin.banner.mapper;

import com.zerobase.fastlms.admin.banner.dto.BannerDto;
import com.zerobase.fastlms.admin.banner.model.BannerParam;
import com.zerobase.fastlms.course.dto.CourseDto;
import com.zerobase.fastlms.course.model.CourseParam;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BannerMapper {

    long selectListCount(BannerParam parameter);
    List<BannerDto> selectList(BannerParam parameter);
    List<BannerDto> selectMainBanner(BannerParam parameter);

}
