package com.zerobase.fastlms.admin.service;

import com.zerobase.fastlms.admin.Entity.Category;
import com.zerobase.fastlms.admin.dto.CategoryDto;
import com.zerobase.fastlms.admin.model.CategoryInput;
import com.zerobase.fastlms.admin.repository.CategoryRepository;
import com.zerobase.fastlms.course.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CategoryImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    private Sort getSortBySortValueDesc(){
        return  Sort.by(Sort.Direction.DESC,"sortValue");

    }


    @Override
    public List<CategoryDto> list() {

        List<Category> categories=categoryRepository.findAll(getSortBySortValueDesc());
        return CategoryDto.of(categories);
    }

    @Override
    public boolean add(String categoryName) {
        //카테고리명이 중복인거 체크


        Category category=Category.builder()
                .categoryName(categoryName)
                .usingYn(true)
                .sortValue(0)
                .build();
        categoryRepository.save(category);
        return true;
    }

    @Override
    public boolean update(CategoryDto parameter) {
        return false;
    }

    @Override
    public boolean del(long id) {
        categoryRepository.deleteById(id);


        return true;
    }

    @Override
    public boolean update(CategoryInput parameter) {
        Optional<Category> optionalCategory=categoryRepository.findById(parameter.getId());
        if(optionalCategory.isPresent()){
            Category category=optionalCategory.get();

            category.setCategoryName(parameter.getCategoryName());
            category.setSortValue(parameter.getSortValue());
            category.setUsingYn(parameter.isUsingYn());

            categoryRepository.save(category);

        }

        return true;
    }

    @Override
    public List<CategoryDto> frontList(CategoryDto parameter) {
       return categoryMapper.select(parameter);
    }
}
