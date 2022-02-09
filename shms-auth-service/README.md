# JWT 인증서버 구현
## application-local.yml
-> profile에 따른 처리 적용 (application.yml에서 spring.profile.include 참고) \
-> 기본포트 8095 적용 \
-> swagger-ui를 위한 별도 설정 적용 \
-> eureka url은 local환경에 따라 변경필요
```
logging:
  level:
    root: warn
    com.skt.shms.shmsauthservice: debug #debug info warn error fatal off

server:
  port: 8095

spring:
  config:
    activate:
      on-profile: local
  mvc:
    pathmatch:
      matching-strategy: ant_path_matcher #for swagger-ui
  datasource:
    #url: jdbc:h2:tcp://localhost/~/restApi
    url: jdbc:h2:mem:testdb
    driver-class-name: org.h2.Driver
    username: sa
    password:
  jpa:
    database-platform: org.hibernate.dialect.H2Dialect
    properties.hibernate:
      hbm2ddl.auto : update
      format_sql: true
    show-sql: true
    generate-ddl: true
    open-in-view: false

eureka:
  instance:
    prefer-ip-address: true
  client:
    register-with-eureka: true
    fetch-registry: true
    service-url:
      defaultZone: http://peer1.localhost:8761/eureka,http://peer2.localhost:8762/eureka
```
* * *
## SWAGGER UI 로 호출확인
-> 접속주소 : http://localhost:8095/swagger-ui.html \
-> Sign Controller 대신 Login Controller로 변경중 \
-> /auth/signup 대신 /auth/join 으로 변경중 \
-> /auth/logout은 구현중 \
![image](https://user-images.githubusercontent.com/16300042/152896480-710bf2b5-7cd1-48f0-9307-34ba9d516c1d.png)
* * *
## H2 DB 확인
-> USER, USER_ROLE, REFRESH_TOKEN 테이블 확인가능 \
-> logout시, 만료일자 현재일로 처리하는 부분 구현필요 \
-> REFRESH_TOKEN으로만 ACCESS_TOKEN 재발급부분 구현필요 \
-> JPA 대신 mybatis로 구현필요 \
![image](https://user-images.githubusercontent.com/16300042/152822055-fb50dd8e-f011-4113-9f36-ffd6bc1c9362.png)
