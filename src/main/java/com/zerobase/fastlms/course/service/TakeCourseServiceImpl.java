package com.zerobase.fastlms.course.service;

import com.zerobase.fastlms.course.dto.CourseDto;
import com.zerobase.fastlms.course.dto.TakeCourseDto;
import com.zerobase.fastlms.course.mapper.TakeCourseMapper;
import com.zerobase.fastlms.course.model.TakeCourseParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TakeCourseServiceImpl implements TakeCourseService {


    private final TakeCourseMapper takeCourseMapper;



    @Override
    public List<TakeCourseDto> list(TakeCourseParam parameter) {
        long totalCount= takeCourseMapper.selectListCount(parameter);

        List<TakeCourseDto>list=takeCourseMapper.selectList(parameter);


        if(!CollectionUtils.isEmpty(list)){
            int i=0;
            for(TakeCourseDto x: list){
                x.setTotalCount(totalCount);
                x.setSeq(totalCount - parameter.getPageStart()-i);
                i++;
            }
        }
        return list;
    }
}
