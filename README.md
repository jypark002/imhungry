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
1. 트랜잭션
    1. 메뉴가 결정되지 않으면 메뉴 추천되지 않는다. `Sync 호출` 
1. 장애격리
    1. 주문 서비스 기능이 수행되지 않더라도 메뉴 추천은 365일 24시간 가능해야 한다. `Async (event-driven)`, `Eventual Consistency`
    1. 결정 서비스의 부하가 과중되면 추천 요청을 잠시동안 받지 않고 잠시후에 요청하도록 유도한다. `Circuit breaker`, `fallback`
1. 성능
    1. 고객은 메뉴 추천 현황을 언제든지 확인할 수 있어야 한다. `CQRS`
    1. 주문이 생성/취소되면 알림을 줄 수 있어야 한다. `Event driven`

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

### 부적격 이벤트 탈락

### 액터, 커맨드 부착하여 읽기 좋게

### 어그리게잇으로 묶기

### 바운디드 컨텍스트로 묶기

### 기능적 요구사항 검증

### 비기능 요구사항 검증

### 완성된 모델

## 헥사고날 아키텍처 다이어그램 도출
- 외부에서 들어오는 요청을 인바운드 포트를 호출해서 처리하는 인바운드 어댑터와 비즈니스 로직에서 들어온 요청을 회부 서비스를 호출해서 처리하는 아웃바운드 어댑터로 분리
- 호출관계에서 Pub/Sub 과 Req/Resp 를 구분함
- 서브 도메인과 바운디드 컨텍스트의 분리: 각 팀의 KPI 별로 아래와 같이 관심 구현 스토리를 나눠가짐

# 구현
분석/설계 단계에서 도출된 헥사고날 아키텍처에 따라, 각 Bounded Context별로 마이크로서비스들을 스프링부트로 구현하였다. 구현한 각 서비스를 로컬에서 실행하는 방법은 아래와 같다. (각 서비스의 포트넘버는 8081 ~ 8084, 8088 이다)
```
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


# 운영
