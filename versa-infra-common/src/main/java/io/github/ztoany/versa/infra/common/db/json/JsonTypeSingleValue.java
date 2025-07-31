package io.github.ztoany.versa.infra.common.db.json;

public interface JsonTypeSingleValue {
    String getV();
    void setV(String v);
    default void trim() {
        if(getV() != null) {
            setV(getV().trim());
        }
    }
}
