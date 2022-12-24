package com.zerobase.fastlms.admin.banner.repository;

import com.zerobase.fastlms.admin.banner.Entity.Banner;
import com.zerobase.fastlms.course.entity.Course;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BannerRepository extends JpaRepository<Banner,Long> {



}
