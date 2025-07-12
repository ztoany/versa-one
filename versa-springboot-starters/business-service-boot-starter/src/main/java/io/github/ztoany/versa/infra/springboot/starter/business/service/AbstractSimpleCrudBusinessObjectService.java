package io.github.ztoany.versa.infra.springboot.starter.business.service;

import io.github.ztoany.versa.infra.business.domain.model.BusinessObject;
import io.github.ztoany.versa.infra.common.exception.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public abstract class AbstractSimpleCrudBusinessObjectService<E, ID, INPUT, OUTPUT> implements BusinessObjectService<ID, INPUT, OUTPUT> {
    protected abstract JpaSpecificationRepository<E, ID> getEntityRepository();

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OUTPUT create(INPUT input) {
        BusinessObject<E, INPUT> bo = newBusinessObject();
        beforeCreate(bo);
        bo.onCreate(input);
        var entityRepository = getEntityRepository();
        entityRepository.saveAndFlush(bo.getEntity());
        return businessObjectToDto(bo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OUTPUT update(ID id, INPUT input) {
        var entityRepository = getEntityRepository();
        var op = findEntityById(id);
        var entity = op.orElseThrow(() -> entityNotFoundException(id));
        BusinessObject<E, INPUT> bo = buildBusinessObjectFromEntity(entity);
        beforeUpdate(bo);
        bo.onUpdate(input);
        entityRepository.saveAndFlush(bo.getEntity());
        return businessObjectToDto(bo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(ID id) {
        deleteByIdInternal(id, true);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OUTPUT getById(ID id) {
        var op = findEntityById(id);
        return op.map(e -> businessObjectToDto(buildBusinessObjectFromEntity(e)))
                .orElseThrow(() -> entityNotFoundException(id));
    }

    protected void deleteByIdInternal(ID id, boolean physicalDelete) {
        var entityRepository = getEntityRepository();
        var op = findEntityById(id);
        if(op.isPresent()) {
            var entity = op.get();
            BusinessObject<E, INPUT> bo = buildBusinessObjectFromEntity(entity);
            bo.onDelete();
            if(physicalDelete) {
                entityRepository.deleteById(id);
            }
        }
    }

    protected Optional<E> findEntityById(ID id) {
        return getEntityRepository().findById(id);
    }

    protected abstract EntityNotFoundException entityNotFoundException(ID id);
    protected abstract OUTPUT businessObjectToDto(BusinessObject<E, INPUT> bo);
    protected abstract BusinessObject<E, INPUT> buildBusinessObjectFromEntity(E entity);
    protected abstract BusinessObject<E, INPUT> newBusinessObject();
    protected void beforeCreate(BusinessObject<E, INPUT> bo) {}
    protected void beforeUpdate(BusinessObject<E, INPUT> bo) {}
}
