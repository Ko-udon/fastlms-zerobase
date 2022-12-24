package com.zerobase.fastlms.admin.banner.dto;

import com.zerobase.fastlms.admin.banner.Entity.Banner;
import com.zerobase.fastlms.course.dto.CourseDto;
import com.zerobase.fastlms.course.entity.Course;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

public class BannerDto {


  Long id;

  String bannerName;

  String imgPath;
  LocalDateTime regDt;

  long seq;  //정렬 순서

  String openType; // 오픈 방법
  String urlName;  // 링크 주소?

  boolean isPublic; // 공개 여부


  long totalCount;

  //add
  String filename;
  String urlFilename;

  public String getRegDtText() {
    DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
    return regDt != null ? regDt.format(formatter) : "";
  }



  public static BannerDto of(Banner banner) {
    return BannerDto.builder()
        .id(banner.getId())
        .bannerName(banner.getBannerName())
        .imgPath(banner.getImgPath())
        .regDt(banner.getRegDt())
        .seq(banner.getSeq())
        .openType(banner.getOpenType())
        .urlName(banner.getUrlName())
        .isPublic(banner.isPublic())
        .filename(banner.getFilename())
        .urlFilename(banner.getUrlFilename())
        .build();
  }

  public static List<BannerDto> of(List<Banner> banners){

    if(banners == null){
      return null;
    }
    List<BannerDto> bannerList = new ArrayList<>();
    for(Banner x : banners){
      bannerList.add(BannerDto.of(x));
    }
    return bannerList;

  }


}
