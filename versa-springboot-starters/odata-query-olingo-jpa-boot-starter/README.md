# OData4 Query Olingo JPA Boot Starter

## Prerequisites

Developed based on [SAP olingo-jpa-processor-v4](https://github.com/SAP/olingo-jpa-processor-v4).

Need to install olingo-jpa-processor-v4 to maven local.

```shell
git clone https://github.com/SAP/olingo-jpa-processor-v4.git
cd olingo-jpa-processor-v4
git checkout 2.2.0
cd jpa
mvn clean install
```

## Usage

The default base path of OData query is /api/odata.

Example url:

```shell
# the url of metadata
http://localhost:8080/api/odata/$metadata

# the url of querying customer
# if there is a JPA entity named Customer
http://localhost:8080/api/odata/Customers

# filter
http://localhost:8080/api/odata/Customers?$filter=name eq 'John'

# order by
http://localhost:8080/api/odata/Customers?$filter=name eq 'John'&$orderby=name asc,createdAt desc
```

## Configuration

```yaml
# set base url
# default value: /api/odata
odata.path-mapping

# default value: true
# enable RFC 9457 Problem Details
odata.enableProblemDetails

# set persistence unit name
# default value: default
# no need to modify the default value
odata.jpa.persistenceUnitName

# set the first letter of the attribute name to lowercase
# default value: true
odata.jpa.propNameFirstToLower

# enable IEEE754Compatible
# default value: true
odata.jpa.forceIeee754Compatible

# customized edm type packages
odata.jpa.typePackages
```

## Development

### Enum Type Support

Use `@EdmEnumeration` on enum type.

```java
@EdmEnumeration
public enum Gender {
    NONE,
    MALE,
    FEMALE
}
```

Configure `odata.jpa.typePackages`, the value is the package where the enum type is located.

```yaml
odata:
  jpa:
    type-packages:
      - xxx.xxx
```

OData query filter

```shell
# The value of enum type is <PersistenceUnitName>.<enumTypeName>'<StringValue>'
$filter=gender eq default.Gender'MALE'
```

### Intercepting OData Query Filter Option

```java
@Configuration
public class OdataConfig {
    private final OdataRequestInterceptor odataRequestInterceptor;

    public OdataConfig(OdataRequestInterceptor odataRequestInterceptor) {
        this.odataRequestInterceptor = odataRequestInterceptor;
    }

    @PostConstruct
    public void init() {
        if(odataRequestInterceptor instanceof OdataRequestCompositeInterceptor compositeInterceptor) {
            var queryOptionInterceptor = new OdataRequestQueryOptionInterceptor();
            var filterOptionByEdmTypeProcessor = new OdataFilterOptionByEdmTypeProcessor();
            // register your logic
            // the key is edm type name
            filterOptionByEdmTypeProcessor.registerOperation("ContactOverviews", (req, text) -> String.format("(%s) and name eq 'aaccdd'", text));
            queryOptionInterceptor.addProcessor(filterOptionByEdmTypeProcessor);
            compositeInterceptor.addInterceptor(queryOptionInterceptor);
        }
    }
}
```




