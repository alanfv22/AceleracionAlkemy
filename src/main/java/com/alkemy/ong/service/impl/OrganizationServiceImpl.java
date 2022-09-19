package com.alkemy.ong.service.impl;

import com.alkemy.ong.domain.entity.OrganizationEntity;
import com.alkemy.ong.domain.dto.OrganizationBasicDTO;
import com.alkemy.ong.domain.mapper.OrganizationMapper;
import com.alkemy.ong.repository.OrganizationRepository;
import com.alkemy.ong.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrganizationServiceImpl implements OrganizationService {
    @Autowired
    OrganizationMapper mapper;
    @Autowired
    OrganizationRepository repository;

    @Override
    public OrganizationBasicDTO getOrganization(Long id) {
            Optional<OrganizationEntity> entityOptional = repository.findById(id);
            OrganizationEntity entity = entityOptional.get();
            OrganizationBasicDTO result = mapper.toOrganizationBasicDTO(entity);
            return result;
    }
}
