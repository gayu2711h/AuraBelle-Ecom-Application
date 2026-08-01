package com.ecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecom.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>
{

	boolean existsByCategoryNameIgnoreCase(String categoryName);

}
