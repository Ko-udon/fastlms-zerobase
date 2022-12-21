package com.zerobase.fastlms.course.controller;

import com.zerobase.fastlms.admin.dto.MemberDto;
import com.zerobase.fastlms.admin.service.CategoryService;
import com.zerobase.fastlms.course.dto.CourseDto;
import com.zerobase.fastlms.course.model.CourseInput;
import com.zerobase.fastlms.course.model.CourseParam;
import com.zerobase.fastlms.course.service.CourseService;
import com.zerobase.fastlms.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.dom4j.rule.Mode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.awt.event.MouseMotionAdapter;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminCourseController extends BaseController {



    private final CourseService courseService;
    private final CategoryService categoryService;



    @GetMapping("/admin/course/list.do")
    public String list(Model model, CourseParam parameter){
        parameter.init();

        List<CourseDto> courseList=courseService.list(parameter);


        long totalCount=0;
        if(!CollectionUtils.isEmpty(courseList)){
            totalCount=courseList.get(0).getTotalCount();
        }


        String queryString=parameter.getQueryString();
        String paperHtml=super.getPaperHtml(totalCount,parameter.getPageSize(),parameter.getPageIndex(),queryString);  //extend BaseController 참고

        model.addAttribute("list",courseList);
        model.addAttribute("totalCount",totalCount);
        model.addAttribute("pager",paperHtml);



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

    @PostMapping({"/admin/course/add.do","/admin/course/edit.do"})
    public String addSubmit(Model model, HttpServletRequest request,CourseInput parameter){

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
