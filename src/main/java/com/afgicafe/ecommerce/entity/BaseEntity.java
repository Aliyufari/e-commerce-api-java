package com.afgicafe.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @CreationTimestamp
    @CreatedDate
    @Column(
            name = "created_at",
            updatable = false
    )
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @LastModifiedDate
    @Column(
            name = "updated_at",
            insertable = false
    )
    private LocalDateTime updatedAt;

    @CreatedBy
    @Column(
            name = "created_by",
            updatable = false
    )
    private UUID createdBy;

    @LastModifiedBy
    @Column(
            name = "updated_by",
            insertable = false
    )
    private UUID updatedBy;
}
