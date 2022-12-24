package com.zerobase.fastlms.admin.mapper;

import com.zerobase.fastlms.admin.dto.LoginHistoryDto;
import com.zerobase.fastlms.admin.dto.MemberDto;
import com.zerobase.fastlms.admin.model.MemberParam;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper

public interface LoginHistoryMapper {
    //@Mapper 쿼리를 실행할 수 있음

    long selectListCount(MemberParam parameter);
    List<LoginHistoryDto> selectList(MemberParam parameter);


}
