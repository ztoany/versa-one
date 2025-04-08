# Web Logging Boot Starter

## Tomcat

Tomcat access log will be enabled by introducing the starter.

Default log path: /var/log

Default log name: ${spring.application.name}-access.log or spring-access.log

## Logback

Level INFO and WARN will be written to log file.

Level FATAL and ERROR will be written to error log file.

Default log path: /var/log

Default log name: ${spring.application.name}.log or spring.log

Default error log name: ${spring.application.name}-error.log or spring-error.log