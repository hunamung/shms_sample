# 안전보건관리시스템 샘플
## 1-1. Spring Boot 구동화면
![Alt text](/chapture_img/Boot_Dashboard.png)
```
- shms-sample-eureka : Spring Cloud Netflix Eureka
- shms-sample-gateway : Spring Cloud Gateway
- shms-sample-web : Spring Boot Webapp

** hosts 파일에 추가필요 **
127.0.0.1       peer1.localhost
127.0.0.1       peer2.localhost
```
* * *
## 1-2. Spring Cloud Eureka (local)
![Alt text](/chapture_img/Spring_Eureka_1.png)
```
- EUREKA 2기 : 이중화 (DS Replicas 설정)
- GATEWAY 1기 : 8080포트 호출로 WAS 라우팅
```
* * *
## 1-2. Spring Cloud Gateway 호출 (local)
![Alt text](/chapture_img/Spring_Gateway_1.png)
