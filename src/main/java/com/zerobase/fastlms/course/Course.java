package com.zerobase.fastlms.course;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Course {



    @Id
    @GeneratedValue
    private Long id;


}
