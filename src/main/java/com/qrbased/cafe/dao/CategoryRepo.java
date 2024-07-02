package com.qrbased.cafe.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.qrbased.cafe.dto.Category;

public interface CategoryRepo extends JpaRepository<Category, Long>{

}
