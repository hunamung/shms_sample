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
    sql-script-encoding: UTF-8
  jpa:
    hibernate:
      ddl-auto: none
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
![image](https://user-images.githubusercontent.com/16300042/153410628-ce51025e-ef6c-4703-a647-939076757d11.png)
* * *
## H2 DB 확인
-> logout시, 만료일자 처리 구현 \
-> REFRESH_TOKEN으로만 ACCESS_TOKEN 재발급부분 구현 \
-> JPA 대신 mybatis로 구현 \
![image](https://user-images.githubusercontent.com/16300042/153411108-7997838d-f161-4843-890a-51f077d72ac8.png)
