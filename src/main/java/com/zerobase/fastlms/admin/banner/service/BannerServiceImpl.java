package com.zerobase.fastlms.admin.banner.service;

import com.zerobase.fastlms.admin.banner.Entity.Banner;
import com.zerobase.fastlms.admin.banner.dto.BannerDto;
import com.zerobase.fastlms.admin.banner.mapper.BannerMapper;
import com.zerobase.fastlms.admin.banner.model.BannerInput;
import com.zerobase.fastlms.admin.banner.model.BannerParam;
import com.zerobase.fastlms.admin.banner.repository.BannerRepository;
import com.zerobase.fastlms.course.dto.CourseDto;
import com.zerobase.fastlms.course.entity.Course;
import com.zerobase.fastlms.course.entity.TakeCourse;
import com.zerobase.fastlms.course.mapper.CourseMapper;
import com.zerobase.fastlms.course.model.CourseInput;
import com.zerobase.fastlms.course.model.CourseParam;
import com.zerobase.fastlms.course.model.ServiceResult;
import com.zerobase.fastlms.course.model.TakeCourseInput;
import com.zerobase.fastlms.course.repository.CourseRepository;
import com.zerobase.fastlms.course.repository.TakeCourseRepository;
import com.zerobase.fastlms.course.service.CourseService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@RequiredArgsConstructor
@Service
public class BannerServiceImpl implements BannerService {
  private final BannerRepository bannerRepository;

  private final BannerMapper bannerMapper;

  @Override
  public BannerDto getById(long id) {
    return bannerRepository.findById(id).map(BannerDto::of).orElse(null);
  }

  @Override
  public boolean set(BannerInput parameter) {
    Optional<Banner> optionalBanner= bannerRepository.findById(parameter.getId());
    //LocalDate saleEndDt=getLocalDate(parameter.getSaleEndDtText());

    if(!optionalBanner.isPresent()){
      return false;
    }
    Banner banner=optionalBanner.get();
    banner.setId(parameter.getId());
    banner.setBannerName(parameter.getBannerName());
    banner.setRegDt(parameter.getRegDt());
    banner.setOpenType(parameter.getOpenType());
    banner.setUrlName(parameter.getUrlName());
    banner.setPublic(parameter.isPublic());
    banner.setFilename(parameter.getFilename());
    banner.setUrlFilename(parameter.getUrlFilename());


    bannerRepository.save(banner);
    return true;
  }

  @Override
  public boolean add(BannerInput parameter) {


    Banner banner= Banner.builder()
        .id(parameter.getId())
        .bannerName(parameter.getBannerName())
        .regDt(LocalDateTime.now())
        .openType(parameter.getOpenType())
        .urlName(parameter.getUrlName())
        .isPublic(parameter.isPublic())
        .filename(parameter.getFilename())
        .urlFilename(parameter.getUrlFilename())
        .build();

    bannerRepository.save(banner);


    return true;
  }

  @Override
  public List<BannerDto> list(BannerParam parameter) {

    long totalCount= bannerMapper.selectListCount(parameter);

    List<BannerDto>list=bannerMapper.selectList(parameter);


    if(!CollectionUtils.isEmpty(list)){
      int i=0;
      for(BannerDto x: list){
        x.setTotalCount(totalCount);
        x.setSeq(totalCount - parameter.getPageStart()-i);
        i++;
      }
    }
    return list;
  }

  @Override
  public boolean del(String idList) {
    if(idList !=null && idList.length()>0){

      String[] ids=idList.split(",");
      for(String x:ids){
        long id=0L;
        try{
          id=Long.parseLong(x);
        }catch (Exception e){

        }

        if(id>0){
          bannerRepository.deleteById(id);
        }
      }
    }

    return true;
  }


}
