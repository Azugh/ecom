package com.lyib.comm_back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lyib.comm_back.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

  public Category findByName(String name);

  @Query("SELECT c FROM Category c WHERE c.name=:name AND c.parentCategory.name=:parentCategoryName")
  public Category findByNameAndParent(@Param("name") String name,
      @Param("parentCategoryName") String parentCategoryName);
}
