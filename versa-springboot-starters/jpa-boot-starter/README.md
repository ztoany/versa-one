# Jpa Boot Starter

## Snowflake Id for Hibernate

```java
@Entity
public class Customer {
    @Id
    @SnowflakeId
    private Long id;
    
    // other codes
}
```