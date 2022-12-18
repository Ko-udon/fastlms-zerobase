package com.zerobase.fastlms.admin.model;

import lombok.Data;

@Data
public class CategoryInput {
    String categoryName;

    Long id;
    int sortValue;
    boolean usingYn;

}
