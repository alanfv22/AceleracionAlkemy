package com.alkemy.ong.service;

import com.alkemy.ong.domain.dto.CategoryBasicDTO;
import com.alkemy.ong.domain.dto.CategoryDTO;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    List<CategoryBasicDTO> getAllCategories();

    CategoryDTO getDetailsById(UUID id);
}
