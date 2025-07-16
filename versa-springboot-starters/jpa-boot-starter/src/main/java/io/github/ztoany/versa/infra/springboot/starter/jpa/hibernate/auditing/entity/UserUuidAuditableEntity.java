package io.github.ztoany.versa.infra.springboot.starter.jpa.hibernate.auditing.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class UserUuidAuditableEntity {
    @CreatedBy
    @Column(name = "user_id")
    private UUID userId;
}
