package io.github.ztoany.versa.infra.springboot.starter.business.service;

public interface BusinessObjectService<ID, INPUT, OUTPUT> {
    OUTPUT create(INPUT input);
    OUTPUT update(ID id, INPUT input);
    void deleteById(ID id);
    OUTPUT getById(ID id);
}
