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
분석/설계 단계에서 도출된 헥사고날 아키텍처에 따라, 각 Bounded Context 별로 마이크로서비스들을 스프링부트로 구현하였다. 구현한 각 서비스를 로컬에서 실행하는 방법은 아래와 같다. (각 서비스의 포트넘버는 8081 ~ 8084, 8088 이다)
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
## DDD (Domain-Driven-Design)의 적용
- msaez.io 에서 이벤트스토밍을 통해 DDD를 작성하고 Aggregate 단위로 Entity를 선언하여 서비스 구현을 진행하였다.

> Request (메뉴추천요청) 서비스의 Request.java
```java
package imhungry;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;
import java.util.Date;

@Entity
@Table(name="Request_table")
public class Request {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String status;
    private String menuType;
    private Long requestId;
    private Long menuId;
    private Long orderId;

    @PostPersist
    public void onPostPersist(){

        if ("REQUESTED".equals(this.getStatus())) {
            imhungry.external.Dicision dicision = new imhungry.external.Dicision();
            dicision.setId(this.getId());
            dicision.setStatus(this.getStatus());
            dicision.setMenuType(this.getMenuType());
            dicision.setRequestId(this.getId());

            RequestApplication.applicationContext.getBean(imhungry.external.DicisionService.class)
                    .menuSelect(dicision);

            Requested requested = new Requested();
            requested.setId(this.getId());
            requested.setStatus(this.getStatus());
            requested.setMenuType(this.getMenuType());
            requested.setRequestId(this.getId());
            requested.publishAfterCommit();
        }
    }

    @PreUpdate
    public void onPreUpdate() {

        if ("CANCELED".equals(this.getStatus())) {
            RequestCanceled requestCanceled = new RequestCanceled();
            BeanUtils.copyProperties(this, requestCanceled);
            requestCanceled.publishAfterCommit();
        }
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public String getMenuType() {
        return menuType;
    }
    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public Long getRequestId() {
        return requestId;
    }
    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public Long getMenuId() {
        return menuId;
    }
    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Long getOrderId() {
        return orderId;
    }
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
```

> Request (메뉴추천요청) 서비스의 PolicyHandler.java
```java
package imhungry;

import imhungry.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PolicyHandler{
    @Autowired RequestRepository requestRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverOrdered_UpdateStatus(@Payload Ordered ordered){

        if(!ordered.validate()) return;

        System.out.println("\n\n##### listener wheneverOrdered_UpdateStatus : " + ordered.toJson() + "\n\n");

        Optional<Request> optionalRequest = requestRepository.findById(ordered.getRequestId());
        Request request = optionalRequest.get();
        request.setStatus(ordered.getStatus());
        request.setRequestId(ordered.getRequestId());
        request.setOrderId(ordered.getOrderId());
        request.setMenuId(ordered.getMenuId());
        requestRepository.save(request);
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString){}
}
```
- 구현 후 REST API 테스트
```shell
# 메뉴 추천 요청
http POST http://localhost:8083/requests menuType="A" status="REQUESTED"

# 메뉴 추천 취소
http PATCH http://localhost:8083/requests/1 status="CANCELED"

# 메뉴 추천 현황 확인
http GET http://localhost:8084/myPages
```

## Req/Resp
분석단계의 비기능적 조건 중 `메뉴가 결정되지 않으면 메뉴 추천되지 않는다.`의 요건을 충족하기 위해 Request 서비스에서 Dicision 서비스 간의 호출은 동기식 일관성을 유지하는 트랜잭션으로 처리하였다. 호출 프로토콜은 Rest Repository에 의해 노출되어있는 REST 서비스를 FeignClient를 이용하여 호출하도록 한다.

> Request 서비스의 external.DicisionService.java
```java
package imhungry.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

@FeignClient(name="dicision", url="http://dicision:8080")
public interface DicisionService {

    @RequestMapping(method= RequestMethod.GET, path="/dicisions")
    public void menuSelect(@RequestBody Dicision dicision);

}
```
> Request 서비스의 Req/Resp
```java
    @PostPersist
    public void onPostPersist(){
        if ("REQUESTED".equals(this.getStatus())) {
            imhungry.external.Dicision dicision = new imhungry.external.Dicision();
            dicision.setId(this.getId());
            dicision.setStatus(this.getStatus());
            dicision.setMenuType(this.getMenuType());
            dicision.setRequestId(this.getId());

            RequestApplication.applicationContext.getBean(imhungry.external.DicisionService.class)
                    .menuSelect(dicision);

            Requested requested = new Requested();
            requested.setId(this.getId());
            requested.setStatus(this.getStatus());
            requested.setMenuType(this.getMenuType());
            requested.setRequestId(this.getId());
            requested.publishAfterCommit();
        }
    }
```

> Dicision 서비스의 Request 서비스 FeignClient 호출 대상
```java
@RestController
public class DicisionController {
    @Autowired
    DicisionRepository dicisionRepository;

    @RequestMapping(value = "/dicisions/menuSelect",
            method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    public boolean menuSelect(HttpServletRequest request, HttpServletResponse response) {

        Long requestId = Long.valueOf(request.getParameter("requestId"));
        String menuType = request.getParameter("menuType");
        if (menuType.isEmpty()) return false;

        Dicision dicision = new Dicision();
        dicision.setStatus("SELECTED");
        dicision.setMenuType(request.getParameter("menuType"));
        dicision.setRequestId(requestId);
        dicision.setMenuId(new Random().nextLong());
        dicisionRepository.save(dicision);

        return true;
    }
}
```
![Cap 2021-06-18 11-44-17-405](https://user-images.githubusercontent.com/80938080/122498785-af1c3f80-d02a-11eb-9d26-2172f7b90274.png)

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
- Azure 연결 및 권한 획득
```shell
# Azure 로그인
az login

# Azure 권한 획득 및 aks와 acr 연결
az aks get-credentials --resource-group user10-rsrcgrp --name user10-aks
kubectl config current-context
az aks update -n user10-aks -g user10-rsrcgrp --attach-acr user10
```

- Docker Image Push/Deploy/서비스 생성(yaml 이용)
```shell
cd gateway
az acr build --registry user10 --image user10.azurecr.io/gateway:latest .

cd kubernetes
kubectl apply -f deployment.yml
kubectl apply -f service.yml

cd ../dicision
az acr build --registry user10 --image user10.azurecr.io/dicision:latest .

cd kubernetes
kubectl apply -f deployment.yml
kubectl apply -f service.yml

cd ../order
az acr build --registry user10 --image user10.azurecr.io/order:latest .

cd kubernetes
kubectl apply -f deployment.yml
kubectl apply -f service.yml

cd ../request
az acr build --registry user10 --image user10.azurecr.io/request:latest .

cd kubernetes
kubectl apply -f deployment.yml
kubectl apply -f service.yml

cd ../myInfo
az acr build --registry user10 --image user10.azurecr.io/myInfo:latest .

cd kubernetes
kubectl apply -f deployment.yml
kubectl apply -f service.yml
```
- Deploy 결과 확인
![Cap 2021-06-24 14-16-24-298](https://user-images.githubusercontent.com/80938080/123206530-d2496200-d4f6-11eb-833a-fedf13adb8a8.png)

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
  @FeignClient(name="dicision", url="${api.url.dicision")
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
kubestl create configmap apiurl --from-literal=url=//http://dicision:8080
```
- ConfigMap 결과
![Cap 2021-06-24 14-02-16-163](https://user-images.githubusercontent.com/80938080/123205423-d4122600-d4f4-11eb-8853-043d329647ea.png)

## Circuit Breaker
- Spring FeignClient + Hystrix을 사용하여 서킷 브레이크 구현
- Hystrix 설정 : 메뉴 선택 (Dicision) 서비스의 요청 쓰레드 처리 시간이 610ms가 넘어서기 시작한 후 어느정도 지속되면 서킷 브레이커가 닫히도록 설정
- 메뉴 선택을 요청하는 Request 서비스에서 Hystrix 설정

> Request 서비스의 application.yml : Hystrix 설정
```yaml
feign:
  hystrix:
    enabled: true
hystrix:
  command:
    default:
      execution.isolation.thread.timeoutInMilliseconds: 610
```

> Dicision 서비스의 임의 부하처리 : Dicision.java, 400ms에서 +/- 220ms 지연 발생
```java
    @PrePersist
    public void onPrePersist() {
        try {
            Thread.currentThread().sleep((long) (400 + Math.random() * 220));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
```

- 부하테스터 siege 툴을 사용한 서킷브레이커 동작 확인
    - 동시 사용자 100명
    - 60초 동안 실시
    
```shell
siege -c100 -t60S -r10 -v --content-type "application/json" 'http://52.231.97.198:8080/requests POST {"menuType":"A", "status":"REQUESTED"}'
```
- 테스트 결과
> 부하가 발생하고 서킷브레이커가 발동하여 요청 실패하였고, 밀린 부하가 다시 처리되면서 메뉴 선택 요청(Request)를 받기 시작함
> ![Cap 2021-06-24 16-15-26-459](https://user-images.githubusercontent.com/80938080/123220608-15143580-d509-11eb-89df-a6b08e0faff7.png)
> 운영 중인 시스템은 죽지 않고 지속적으로 서킷브레이커에 의하여 적절히 회로의 열림과 닫힘이 발생하며 자원을 보호하고 있음을 보여줌. 하지만, 47%가 성공하였고, 53%가 실패했다는 것은 사요자의 편의성에 있어 좋지 않기 때문에 Retry 설정과 동적 Scale out (Replica의 자동 추가, HPA)을 통하여 시스템 확장에 대한 보완이 필요.
> ![Cap 2021-06-24 16-15-45-288](https://user-images.githubusercontent.com/80938080/123220618-18a7bc80-d509-11eb-84fa-bbcc7be9e931.png)

## Autoscale (HPA)
Circuit Breaker는 시스템을 안정적으로 운여할 수 있게 해 줬지만 사용자의 요청이 증가하는 경우 100% 받아주지 못하기 때문에 이데 대한 보완책으로 자동화된 확장 기능(HPA)을 적용하고자 한다.

- Request 서비스의 deployment.yml 파일 설정
```yaml
          resources:
            requests:
              memory: "64Mi"
              cpu: "250m"
            limits:
              memory: "500Mi"
              cpu: "500m"
```

- 메뉴 요청 (Request) 서비스에 대한 Replica를 동적으로 늘려주도록 HPA를 설정한다.
- CPU 사용량이 15%를 넘어서면 Replica를 10개까지 늘려준다.
```shell
kubectl autoscale deploy request --min=1 --max=10 --cpu-percent=15
```
> HPA 설정 확인
> ![Cap 2021-06-24 16-41-30-284](https://user-images.githubusercontent.com/80938080/123222630-147c9e80-d50b-11eb-9470-337700bcf0b4.png)

- 서킷브레이커에서 했던 방식으로 1분동안 부하를 걸어준다.
```shell
siege -c100 -t60S -r10 -v --content-type "application/json" 'http://request:8080/requests POST {"menuType":"A", "status":"REQUESTED"}'
```
- 오토스케일 진행 현황을 모니터링 한다.
```shell
kubectl get deploy request -w
```
> 오토스케일 결과 확인
> ![Cap 2021-06-24 17-27-53-872](https://user-images.githubusercontent.com/80938080/123232379-1008b380-d514-11eb-948a-3ee430d6e505.png)

## Zero-downtime deploy (Readiness Probe)

## Self-healing (Liveness Probe)
