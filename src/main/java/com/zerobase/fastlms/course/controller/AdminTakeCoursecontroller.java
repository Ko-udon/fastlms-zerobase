package com.zerobase.fastlms.course.controller;

import com.zerobase.fastlms.course.dto.TakeCourseDto;
import com.zerobase.fastlms.course.model.TakeCourseParam;
import com.zerobase.fastlms.course.service.TakeCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller

public class AdminTakeCoursecontroller extends BaseController{
    private final TakeCourseService takeCourseService;


    @GetMapping("/admin/takecourse/list.do")
    public String list(Model model, TakeCourseParam parameter){
        parameter.init();
        List<TakeCourseDto> courseList=takeCourseService.list(parameter);

        long totalCount=0;
        if(!CollectionUtils.isEmpty(courseList)){
            totalCount=courseList.get(0).getTotalCount();
        }

        String queryString=parameter.getQueryString();
        String paperHtml=getPaperHtml(totalCount,parameter.getPageSize(),parameter.getPageIndex(),queryString);

        model.addAttribute("list",courseList);
        model.addAttribute("totalCount",totalCount);
        model.addAttribute("pager",paperHtml);



        return "admin/takecourse/list";
    }
}