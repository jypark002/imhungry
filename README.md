# 뭐먹지! - 메뉴 추천 시스템

본 과제는 MSA/DDD/Event Storming/EDA 를 포괄하는 분석/설계/구현/운영 전단계를 커버하도록 구성하였습니다.  
이는 CNA (Cloud Native Application)의 개발에 요구되는 체크포인트들을 통과하기 위한 개인 과제 수행 결과입니다.

# 서비스 시나리오
## 기능적 요구사항
1. 고객이 먹고싶은 메뉴타입을 선택하고 메뉴 추천을 요청한다. (메뉴타입 - 1:한식 | 2:중식 | 3:분식 | 0:아무거나)
1. 결정 서비스에서 메뉴타입에 따라 메뉴를 선택한다.
1. 메뉴가 선택되면 주문 서비스에 메뉴를 주문한다.
1. 메뉴 주문이 되면 주문 정보는 메뉴 요청 정보에 업데이트 된다.
1. 고객이 메뉴 요청을 취소할 수 있다.
1. 메뉴 요청이 취소되면 메뉴 선택이 취소되고 메뉴 주문이 취소된다.
## 비기능적 요구사항
- 트랜잭션  
      1. 메뉴가 결정되지 않으면 메뉴 추천되지 않는다. `Sync 호출`
- 장애격리  
      2. 주문 서비스 기능이 수행되지 않더라도 메뉴 추천은 365일 24시간 가능해야 한다. `Async (event-driven)`, `Eventual Consistency`  
      3. 결정 서비스의 부하가 과중되면 추천 요청을 잠시동안 받지 않고 잠시후에 요청하도록 유도한다. `Circuit breaker`, `fallback`
- 성능  
      4. 고객은 메뉴 추천 현황을 언제든지 확인할 수 있어야 한다. `CQRS`  
      5. 주문이 생성/취소되면 알림을 줄 수 있어야 한다. `Event driven`

# 체크포인트
- 체크포인트 : https://workflowy.com/s/assessment-check-po/T5YrzcMewfo4J6LW
1. Saga
1. CQRS
1. Correlation
1. Req/Resp
1. Gateway
1. Deploy/ Pipeline
1. Circuit Breaker
1. Autoscale (HPA)
1. Zero-downtime deploy (Readiness Probe)
1. Config Map/ Persistence Volume
1. Polyglot
1. Self-healing (Liveness Probe)

# 분석/설계
## EventStorming 결과
* MSAEZ 모델링한 이벤트스토밍 결과: http://www.msaez.io/#/storming/xShudiMcElbc0zB5RbXcULNN9mz1/mine/9679a9198f1043f3845600bf8eb5b119

### 이벤트 도출
=>화면캡처
### 부적격 이벤트 탈락
=>화면캡처
### 액터, 커맨드 부착하여 읽기 좋게
![imhungry-69](https://user-images.githubusercontent.com/80938080/122349218-ff41c600-cf86-11eb-8d78-a2b2669f3418.png)
### 어그리게잇으로 묶기
![imhungry-79](https://user-images.githubusercontent.com/80938080/122349112-e0dbca80-cf86-11eb-8f02-56958f2d612f.png)
### 바운디드 컨텍스트로 묶기
![imhungry-89](https://user-images.githubusercontent.com/80938080/122349047-cb66a080-cf86-11eb-95bc-9de307d4dcc8.png)

### 기능적 요구사항 검증
![imhungry-99-1](https://user-images.githubusercontent.com/80938080/122352941-893f5e00-cf8a-11eb-9d15-90eb7f3b1721.png)
1. 고객이 먹고싶은 메뉴타입을 선택하고 메뉴 추천을 요청한다. (메뉴타입 - 1:한식 | 2:중식 | 3:분식 | 0:아무거나)
1. 결정 서비스에서 메뉴타입에 따라 메뉴를 선택한다.
1. 메뉴가 선택되면 주문 서비스에 메뉴를 주문한다.
1. 메뉴 주문이 되면 주문 정보는 메뉴 요청 정보에 업데이트 된다.
1. 고객이 메뉴 요청을 취소할 수 있다.
1. 메뉴 요청이 취소되면 메뉴 선택이 취소되고 메뉴 주문이 취소된다.
### 비기능 요구사항 검증
![imhungry-99-2](https://user-images.githubusercontent.com/80938080/122352875-77f65180-cf8a-11eb-9b1a-1ca35b1672d7.png)
  - 트랜잭션  
        1. 메뉴가 결정되지 않으면 메뉴 추천되지 않는다. `Sync 호출`
  - 장애격리  
        2. 주문 서비스 기능이 수행되지 않더라도 메뉴 추천은 365일 24시간 가능해야 한다. `Async (event-driven)`, `Eventual Consistency`  
        3. 결정 서비스의 부하가 과중되면 추천 요청을 잠시동안 받지 않고 잠시후에 요청하도록 유도한다. `Circuit breaker`, `fallback`
  - 성능  
        4. 고객은 메뉴 추천 현황을 언제든지 확인할 수 있어야 한다. `CQRS`  
        5. 주문이 생성/취소되면 알림을 줄 수 있어야 한다. `Event driven`
### 완성된 모델
![imhungry-99](https://user-images.githubusercontent.com/80938080/122348955-ab36e180-cf86-11eb-962c-ed4ffd9e0458.png)

## 헥사고날 아키텍처 다이어그램 도출
- 외부에서 들어오는 요청을 인바운드 포트를 호출해서 처리하는 인바운드 어댑터와 비즈니스 로직에서 들어온 요청을 회부 서비스를 호출해서 처리하는 아웃바운드 어댑터로 분리
- 호출관계에서 Pub/Sub 과 Req/Resp 를 구분  
  
![Hexagonal](https://user-images.githubusercontent.com/80938080/122354721-3b2b5a00-cf8c-11eb-8f02-d938cb45169c.png)

# 구현
분석/설계 단계에서 도출된 헥사고날 아키텍처에 따라, 각 Bounded Context별로 마이크로서비스들을 스프링부트로 구현하였다. 구현한 각 서비스를 로컬에서 실행하는 방법은 아래와 같다. (각 서비스의 포트넘버는 8081 ~ 8084, 8088 이다)
```shell
cd request
mvn spring-boot:run

cd dicision
mvn spring-boot:run 

cd order
mvn spring-boot:run  

cd myInfo
mvn spring-boot:run 

cd gateway
mvn spring-boot:run
```

## Req/Resp

## Gateway
- API Gateway를 통하여 마이크로서비스들의 진입점을 단일화하였습니다.
> gateway > application.xml 설정
```yaml
spring:
  profiles: docker
  cloud:
    gateway:
      routes:
        - id: dicision
          uri: http://dicision:8080
          predicates:
            - Path=/dicisions/** 
        - id: order
          uri: http://order:8080
          predicates:
            - Path=/orders/** 
        - id: request
          uri: http://request:8080
          predicates:
            - Path=/requests/** 
        - id: myInfo
          uri: http://myInfo:8080
          predicates:
            - Path= /myPages/**
      globalcors:
        corsConfigurations:
          '[/**]':
            allowedOrigins:
              - "*"
            allowedMethods:
              - "*"
            allowedHeaders:
              - "*"
            allowCredentials: true

server:
  port: 8080
```

## CQRS

- Materialized View 구현을 통해 다른 마이크로서비스의 데이터 원본에 접근없이 내 서비스의 화면 구성과 잦은 조회가 가능하게 하였습니다. 본 과제에서 View 서비스는 MyInfo 서비스가 수행하며 메뉴요청 상태를 보여준다. 
> 메뉴 선택 요청 후 myPage 조회 결과
![CQRS](https://user-images.githubusercontent.com/80938080/122403782-99bafd00-cfb9-11eb-8427-ee352786dd9f.png)

## Polyglot

- 회의(Request)의 경우 H2 DB인 결제(Dicision)/회의실(Order) 서비스와 달리 Hsql로 구현하여 MSA의 서비스간 서로 다른 종류의 DB에도 문제없이 동작하여 다형성을 만족하는지 확인하였다.

> Dicision, Order 서비스의 pom.xml 설정
```xml
    <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
        <scope>runtime</scope>
    </dependency>
```
> Request 서비스의 pom.xml 설정
```xml
    <dependency>
        <groupId>org.hsqldb</groupId>
        <artifactId>hsqldb</artifactId>
        <scope>runtime</scope>
    </dependency>
```


# 운영

## Deploy
- git에서 소스 가져오기
```shell
git clone https://github.com/jypark002/imhungry.git
```
- Build 하기
```shell
cd /imhungry
cd gateway
mvn package

cd ../dicision
mvn package

cd ../order
mvn package

cd ../request
mvn package

cd ../myInfo
mvn package
```
- Docker Image Push/Deploy/서비스 생성(yaml 이용)
```shell
# Namespace 설정
kubectl config set-context --current --namespace=jypark

# Namespace 생성
kubectl create ns jypark

cd gateway
az acr build --registry skccjypark --image skccjypark.azurecr.io/gateway:latest .

cd kubernetes
kubectl apply -f deployment.yml
kubectl apply -f service.yml

cd ../dicision
az acr build --registry skccjypark --image skccjypark.azurecr.io/dicision:latest .

cd kubernetes
kubectl apply -f deployment.yml
kubectl apply -f service.yml

cd ../order
az acr build --registry skccjypark --image skccjypark.azurecr.io/order:latest .

cd kubernetes
kubectl apply -f deployment.yml
kubectl apply -f service.yml

cd ../request
az acr build --registry skccjypark --image skccjypark.azurecr.io/request:latest .

cd kubernetes
kubectl apply -f deployment.yml
kubectl apply -f service.yml

cd ../myInfo
az acr build --registry skccjypark --image skccjypark.azurecr.io/myInfo:latest .

cd kubernetes
kubectl apply -f deployment.yml
kubectl apply -f service.yml
```
- Deploy 결과 확인
=>화면캡처

## Config Map
> 변경될 수 있는 설정을 ConfigMap을 사용하여 관리한다.  
> - Request 서비스에서 Req/Reply로 호출되는 Dicision 서비스의 Url을 Config Map을 사용하여 구현

- application.yml 파일의 $(configurl)} 설정
```yaml
  api:
    url:
      dicision: ${configurl}
```
- Config Map 사용 (/request/src/main/java/jyrestaurant/external/DicisionService.java)
```java
  @FeignClient(name="dicision", url="${api.url.Dicision")
  public interface DicisionService {
  
      @RequestMapping(method= RequestMethod.GET, path="/dicisions")
      public void menuSelect(@RequestBody Dicision dicision);
  }
```
- Deployment.yml에 ConfigMap 적용
```yaml
          env:
            - name: configurl
              valueFrom:
                configMapKeyRef:
                  name: apiurl
                  key: url
```

- ConfigMap 생성
```shell
kubestl create configmap apiurl --from-literal=url=//http://dicision:8080 -n jypark
```
kubectl get configmap apirul -o yaml
=>화면캡처

## Circuit Breaker

## Autoscale (HPA)

## Zero-downtime deploy (Readiness Probe)

## Self-healing (Liveness Probe)
