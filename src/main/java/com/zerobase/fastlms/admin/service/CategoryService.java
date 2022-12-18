package com.zerobase.fastlms.admin.service;

import com.zerobase.fastlms.admin.Entity.Category;
import com.zerobase.fastlms.admin.dto.CategoryDto;
import com.zerobase.fastlms.admin.model.CategoryInput;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> list();

    boolean add(String categoryName);  //카테고리 추가
    boolean update(CategoryDto parameter);  //z카테고리 수정
    boolean del(long id);  //카테고리 삭제

    boolean update(CategoryInput parameter);



}
