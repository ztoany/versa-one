package io.github.ztoany.versa.infra.springboot.starter.business.service;

import io.github.ztoany.versa.infra.business.domain.model.BusinessObject;
import io.github.ztoany.versa.infra.common.exception.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public abstract class AbstractSimpleCrudBusinessObjectService<E, ID, INPUT, OUTPUT> implements BusinessObjectService<ID, INPUT, OUTPUT> {
    protected abstract JpaRepository<E, ID> getEntityRepository();

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OUTPUT create(INPUT input) {
        BusinessObject<E, INPUT> bo = newBusinessObject();
        bo.onCreate(input);
        var entityRepository = getEntityRepository();
        entityRepository.saveAndFlush(bo.getEntity());
        return businessObjectToDto(bo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OUTPUT update(ID id, INPUT input) {
        var entityRepository = getEntityRepository();
        var op = entityRepository.findById(id);
        var entity = op.orElseThrow(() -> entityNotFoundException(id));
        BusinessObject<E, INPUT> bo = buildBusinessObjectFromEntity(entity);
        bo.onUpdate(input);
        entityRepository.saveAndFlush(bo.getEntity());
        return businessObjectToDto(bo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(ID id) {
        var entityRepository = getEntityRepository();
        entityRepository.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OUTPUT getById(ID id) {
        var entityRepository = getEntityRepository();
        var op = entityRepository.findById(id);
        return op.map(e -> businessObjectToDto(buildBusinessObjectFromEntity(e)))
                .orElseThrow(() -> entityNotFoundException(id));
    }

    protected abstract EntityNotFoundException entityNotFoundException(ID id);
    protected abstract OUTPUT businessObjectToDto(BusinessObject<E, INPUT> bo);
    protected abstract BusinessObject<E, INPUT> buildBusinessObjectFromEntity(E entity);
    protected abstract BusinessObject<E, INPUT> newBusinessObject();
}
