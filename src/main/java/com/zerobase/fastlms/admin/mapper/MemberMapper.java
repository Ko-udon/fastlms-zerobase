package com.zerobase.fastlms.admin.mapper;

import com.zerobase.fastlms.admin.dto.MemberDto;
import com.zerobase.fastlms.admin.model.MemberParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper

public interface MemberMapper {
    //@Mapper 쿼리를 실행할 수 있음

    long selectListCount(MemberParam parameter);
    List<MemberDto> selectList(MemberParam parameter);


}
