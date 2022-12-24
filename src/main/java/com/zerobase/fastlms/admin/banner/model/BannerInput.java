package com.zerobase.fastlms.admin.banner.model;


import java.time.LocalDateTime;
import lombok.Data;

@Data
public class BannerInput {
    Long id;

    String bannerName;

    String imgPath;
    LocalDateTime regDt;

    long seq;  //정렬 순서

    String openType; // 오픈 방법
    String urlName;  // 링크 주소?

    boolean isPublic; // 공개 여부



    //add
    String filename;
    String urlFilename;

    //삭제를 위한
    String idList;

}
