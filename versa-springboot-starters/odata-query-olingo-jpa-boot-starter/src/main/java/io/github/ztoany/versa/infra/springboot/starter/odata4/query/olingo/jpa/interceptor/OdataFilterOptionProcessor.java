package io.github.ztoany.versa.infra.springboot.starter.odata4.query.olingo.jpa.interceptor;

public abstract class OdataFilterOptionProcessor extends AbstractOdataQueryOptionProcessor {
    @Override
    String getQueryOptionName() {
        return "$filter";
    }
}
