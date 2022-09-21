package com.alkemy.ong.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "slides")
public class SlideEntity {

    @Id
    @GeneratedValue
    @Type(type ="uuid-char")
    @Column(name = "id_slide", nullable = false)
    private UUID id;


    @Column(name = "image_url")
    private String imageUrl;

    private String text;
    private String orde;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "organization_id", insertable = false, updatable = false) // Solo para buscar informacion
    private OrganizationEntity organization;

    @Column(name = "organization_id", nullable = false) // Para guardar y actualizar
    private UUID organizationId;
}

