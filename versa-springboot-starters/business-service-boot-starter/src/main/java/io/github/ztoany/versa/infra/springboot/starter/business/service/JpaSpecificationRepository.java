package io.github.ztoany.versa.infra.springboot.starter.business.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface JpaSpecificationRepository<E, ID> extends JpaRepository<E, ID>, JpaSpecificationExecutor<E> {
}
