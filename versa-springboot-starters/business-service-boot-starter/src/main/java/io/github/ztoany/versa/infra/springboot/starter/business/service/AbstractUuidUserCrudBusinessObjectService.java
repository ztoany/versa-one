package io.github.ztoany.versa.infra.springboot.starter.business.service;

import io.github.ztoany.versa.infra.springboot.auditing.UuidUserAuditing;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.UUID;

public abstract class AbstractUuidUserCrudBusinessObjectService<E, ID, INPUT, OUTPUT> extends AbstractSimpleCrudBusinessObjectService<E, ID, INPUT, OUTPUT> {
    protected abstract UuidUserAuditing getUserAuditing();

    @Override
    protected Optional<E> findEntityById(ID id) {
        var op = getUserAuditing().getCurrentUserId();
        Specification<E> spec = idEqual(id).and(userIdEqual(op.orElse(null)));
        return getEntityRepository().findOne(spec);
    }

    protected Specification<E> idEqual(ID id) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("id"), id);
    }

    protected Specification<E> userIdEqual(UUID userId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("userId"), userId);
    }
}
