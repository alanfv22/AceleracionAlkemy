package com.alkemy.ong.service.impl;

import com.alkemy.ong.domain.dto.CategoryBasicDTO;
import com.alkemy.ong.domain.dto.CategoryDTO;
import com.alkemy.ong.domain.entity.CategoryEntity;
import com.alkemy.ong.domain.mapper.CategoryMapper;
import com.alkemy.ong.exception.ParamNotFound;
import com.alkemy.ong.repository.CategoryRepository;
import com.alkemy.ong.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    CategoryMapper categoryMapper;
    @Override
    public List<CategoryBasicDTO> getAllCategories() {
        List<CategoryEntity> entities = categoryRepository.findAll();
        List<CategoryBasicDTO> result = categoryMapper.categoryEntity2DTOList(entities);
        return result;
    }

    @Override
    public CategoryDTO getDetailsById(UUID id) {
        Optional<CategoryEntity> entity = this.categoryRepository.findById(id);
        if(!entity.isPresent()){
            throw new ParamNotFound("Id not valid");
        }
        CategoryDTO categoryDTO = this.categoryMapper.categoryEntity2DTO(entity.get());
        return categoryDTO;
    }

    @Override
    public CategoryDTO save(CategoryDTO dto) {
        CategoryEntity entity = categoryMapper.categoryDTO2Entity(dto);
        CategoryEntity entitySaved = categoryRepository.save(entity);
        CategoryDTO result = categoryMapper.categoryEntity2DTO(entitySaved);
        return result;
    }

    @Override
    public void delete(UUID id) {
        Optional<CategoryEntity> entity= this.categoryRepository.findById(id);
        if(!entity.isPresent()){
            throw new ParamNotFound("Id not valid");
        }
        this.categoryRepository.delete(entity.get());
    }

    @Override
    public CategoryDTO update(UUID id, CategoryDTO category) {
        Optional<CategoryEntity> entity= this.categoryRepository.findById(id);
        if(!entity.isPresent()){
            throw new ParamNotFound("Id not valid");
        }
        this.categoryMapper.categoryEntityRefreshValues(entity.get(), category);
        CategoryEntity entitySaved = this.categoryRepository.save(entity.get());
        CategoryDTO result = this.categoryMapper.categoryEntity2DTO(entitySaved);

        return result;
    }



}