package com.zerobase.fastlms.course.controller;

import com.zerobase.fastlms.admin.dto.MemberDto;
import com.zerobase.fastlms.admin.service.CategoryService;
import com.zerobase.fastlms.course.dto.CourseDto;
import com.zerobase.fastlms.course.model.CourseInput;
import com.zerobase.fastlms.course.model.CourseParam;
import com.zerobase.fastlms.course.service.CourseService;
import com.zerobase.fastlms.util.PageUtil;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.rule.Mode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.awt.event.MouseMotionAdapter;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AdminCourseController extends BaseController {



    private final CourseService courseService;
    private final CategoryService categoryService;



    @GetMapping("/admin/course/list.do")
    public String list(Model model, CourseParam parameter){
        parameter.init();
        List<CourseDto> courseList = courseService.list(parameter);

        long totalCount = 0;
        if (!CollectionUtils.isEmpty(courseList)) {
            totalCount = courseList.get(0).getTotalCount();
        }
        String queryString = parameter.getQueryString();
        String pagerHtml = getPaperHtml(totalCount, parameter.getPageSize(), parameter.getPageIndex(), queryString);

        model.addAttribute("list", courseList);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pager", pagerHtml);

        return "admin/course/list";
    }

    @GetMapping({"/admin/course/add.do","/admin/course/edit.do"})
    public String add(Model model, HttpServletRequest request,CourseInput parameter){
        model.addAttribute("category",categoryService.list());


        //두개의 url로 동시에 처리하기
        boolean editMode=request.getRequestURI().contains("/edit.do");
        CourseDto detail=new CourseDto();

        if(editMode){  //강좌 수정
            long id=parameter.getId();
            CourseDto existCourse=courseService.getById(id);
            if(existCourse==null){
                //error 처리
                model.addAttribute("message","강좌정보가 존재하지 않습니다.");
                return "common/error";
            }
            detail=existCourse;

        }

        model.addAttribute("editMode",editMode);
        model.addAttribute("detail",detail);
        return "admin/course/add";
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

    @PostMapping({"/admin/course/add.do","/admin/course/edit.do"})
    public String addSubmit(Model model, HttpServletRequest request,CourseInput parameter
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



        boolean editMode=request.getRequestURI().contains("/edit.do");

        if(editMode){  //강좌 수정
            long id=parameter.getId();
            CourseDto existCourse=courseService.getById(id);
            if(existCourse==null){
                //error 처리
                model.addAttribute("message","강좌정보가 존재하지 않습니다.");
                return "common/error";
            }
            boolean result=courseService.set(parameter);

        }else{
            boolean result=courseService.add(parameter);
        }

        return "redirect:/admin/course/list.do";
    }

    @PostMapping({"/admin/course/delete.do"})
    public String delete(Model model, HttpServletRequest request,CourseInput parameter){

        boolean result=courseService.del(parameter.getIdList());
        return "redirect:/admin/course/list.do";
    }




}
