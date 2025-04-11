package io.github.ztoany.versa.infra.springboot.starter.odata4.query.olingo.jpa;

import jakarta.validation.constraints.Pattern;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "odata")
@Validated
public class OdataJpaProperties {
    @Pattern(regexp = "^(\\/?[\\w\\-\\/]+[^\\/])$", message = "invalid path mapping, path can not end with /")
    private String pathMapping = "/api/odata";
    private Jpa jpa = new Jpa();

    public String getPathMapping() {
        return pathMapping;
    }

    public void setPathMapping(String pathMapping) {
        this.pathMapping = pathMapping;
    }

    public Jpa getJpa() {
        return jpa;
    }

    public void setJpa(Jpa jpa) {
        this.jpa = jpa;
    }

    public static class Jpa {
        private String persistenceUnitName = "default";
        private Boolean propNameFirstToLower = true;
        private Boolean forceIeee754Compatible = true;
        private List<String> typePackages = new ArrayList<>();

        public String getPersistenceUnitName() {
            return persistenceUnitName;
        }

        public void setPersistenceUnitName(String persistenceUnitName) {
            this.persistenceUnitName = persistenceUnitName;
        }

        public Boolean getPropNameFirstToLower() {
            return propNameFirstToLower;
        }

        public void setPropNameFirstToLower(Boolean propNameFirstToLower) {
            this.propNameFirstToLower = propNameFirstToLower;
        }

        public Boolean getForceIeee754Compatible() {
            return forceIeee754Compatible;
        }

        public void setForceIeee754Compatible(Boolean forceIeee754Compatible) {
            this.forceIeee754Compatible = forceIeee754Compatible;
        }

        public List<String> getTypePackages() {
            return typePackages;
        }

        public void setTypePackages(List<String> typePackages) {
            this.typePackages = typePackages;
        }
    }
}
