package com.alkemy.ong.repository;

import com.alkemy.ong.domain.entity.OrganizationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrganizationRepository extends JpaRepository<OrganizationEntity, UUID> {
    OrganizationEntity findByEmail(String email);
}
