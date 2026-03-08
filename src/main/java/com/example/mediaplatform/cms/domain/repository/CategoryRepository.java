package com.example.mediaplatform.cms.domain.repository;

import com.example.mediaplatform.cms.domain.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
