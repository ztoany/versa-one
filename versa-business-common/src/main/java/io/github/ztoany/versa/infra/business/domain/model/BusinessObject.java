package io.github.ztoany.versa.infra.business.domain.model;

public interface BusinessObject<E, INPUT> {
    void onCreate(INPUT input);
    void onUpdate(INPUT input);
    default void onDelete() {}
    E getEntity();
}

