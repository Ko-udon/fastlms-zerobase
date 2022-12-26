package com.zerobase.fastlms.admin.controller;

import com.zerobase.fastlms.admin.banner.dto.BannerDto;
import com.zerobase.fastlms.admin.banner.model.BannerInput;
import com.zerobase.fastlms.admin.banner.model.BannerParam;
import com.zerobase.fastlms.admin.banner.service.BannerService;
import com.zerobase.fastlms.course.controller.BaseController;
import com.zerobase.fastlms.course.dto.CourseDto;
import com.zerobase.fastlms.course.model.CourseInput;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
@Slf4j

public class AdminBannerController extends BaseController {

  private final BannerService bannerService;

  @GetMapping("/admin/banner/list.do")
  public String banner(Model model, BannerParam parameter){
    parameter.init();

    List<BannerDto> bannerList=bannerService.list(parameter);


    long totalCount = 0;
    if (!CollectionUtils.isEmpty(bannerList)) {
      totalCount = bannerList.get(0).getTotalCount();
    }
    String queryString = parameter.getQueryString();
    String pagerHtml = getPaperHtml(totalCount, parameter.getPageSize(), parameter.getPageIndex(), queryString);

    model.addAttribute("list", bannerList);
    model.addAttribute("totalCount", totalCount);
    model.addAttribute("pager", pagerHtml);

    return "/admin/banner/list";
  }

  @GetMapping({"/admin/banner/add.do","/admin/banner/edit.do"})
  public String addBanner(Model model, HttpServletRequest request, BannerInput parameter){
    //model.addAttribute("category",categoryService.list());


    //두개의 url로 동시에 처리하기
    boolean editMode=request.getRequestURI().contains("/edit.do");

    BannerDto detail=new BannerDto();

    if(editMode){  //배너 수정
      long id=parameter.getId();
      //System.out.println("%%%%%%%%%%%%%%% edit Id: " + id);
      BannerDto existBanner=bannerService.getById(id);
      if(existBanner==null){
        //error 처리
        model.addAttribute("message","배너 정보가 존재하지 않습니다.");
        return "common/error";
      }
      detail=existBanner;

    }

    model.addAttribute("editMode",editMode);
    model.addAttribute("detail",detail);
    return "admin/banner/add";
  }
  private String[] getNewSaveFile(String baseLocalPath, String baseUrlPath, String originalFilename){
    LocalDate now=LocalDate.now();
    String[] dirs = {
        String.format("%s/%d/", baseLocalPath,now.getYear()),
        String.format("%s/%d/%02d/", baseLocalPath, now.getYear(),now.getMonthValue()),
        String.format("%s/%d/%02d/%02d/", baseLocalPath, now.getYear(), now.getMonthValue(), now.getDayOfMonth())};

    String urlDir = String.format("%s/%d/%02d/%02d/", baseUrlPath, now.getYear(), now.getMonthValue(), now.getDayOfMonth());

    for(String dir : dirs) {
      File file = new File(dir);
      if (!file.isDirectory()) {
        file.mkdir();   //디렉토리가 없으면 생성하기
      }
    }
    String fileExtension = "";
    if (originalFilename != null) {
      int dotPos = originalFilename.lastIndexOf(".");
      if (dotPos > -1) {
        fileExtension = originalFilename.substring(dotPos + 1);
      }
    }

    String uuid = UUID.randomUUID().toString().replaceAll("-", "");
    String newFilename = String.format("%s%s", dirs[2], uuid);
    String newUrlFilename = String.format("%s%s", urlDir, uuid);
    if (fileExtension.length() > 0) {
      newFilename += "." + fileExtension;
      newUrlFilename += "." + fileExtension;
    }

    return new String[]{newFilename, newUrlFilename};


  }

  @PostMapping({"/admin/banner/add.do","/admin/banner/edit.do"})
  public String addBannerSubmit(Model model, HttpServletRequest request, BannerInput parameter
      , MultipartFile file) throws IOException {

    String saveFilename = "";
    String urlFilename = "";

    if (file != null) {
      String originalFilename = file.getOriginalFilename();

      String baseLocalPath = "D://IntelliJ/JavaWorkspace/fastlms-zerobase/files";

      String baseUrlPath = "/files";

      String[] arrFilename = getNewSaveFile(baseLocalPath, baseUrlPath, originalFilename);

      saveFilename = arrFilename[0];
      urlFilename = arrFilename[1];

      try {
        File newFile = new File(saveFilename);
        FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(newFile));
      } catch (IOException e) {
        log.info("############################ - 1");
        log.info(e.getMessage());
      }
    }

    parameter.setFilename(saveFilename);
    parameter.setUrlFilename(urlFilename);
    if(parameter.getId()==null){
      parameter.setId(1L);
    }
    //parameter.setId(1L);  //더미 데이터 필요,,


    boolean editMode=request.getRequestURI().contains("/edit.do");

    if(editMode){  //배너 수정
      long id=parameter.getId();
      //System.out.println("%%%%%%%%%%%%%%% edit  addBannerSubmit Id: " + id);
      BannerDto existBanner=bannerService.getById(id);

      if(existBanner==null){
        //error 처리
        model.addAttribute("message","배너 정보가 존재하지 않습니다.");
        return "common/error";
      }
      boolean result=bannerService.set(parameter);

    }else{
      boolean result=bannerService.add(parameter);
    }

    return "redirect:/admin/banner/list.do";
  }

  @PostMapping({"/admin/banner/delete.do"})
  public String delete(Model model, HttpServletRequest request,BannerInput parameter){

    boolean result=bannerService.del(parameter.getIdList());

    return "redirect:/admin/banner/list.do";
  }

}
