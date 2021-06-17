# 환경설정 및 테스트

## 환경설정

### 네트워크 확인

``` Script
netstat -ano | findstr "PID: 808"

taskkil /pid 99999 /f
```

### 카프카

- 카프카 설치 경로
```
cd C:\kafka_2.13-2.8.0\bin\windows
```

- 주키퍼 실행
```
./zookeeper-server-start.bat ../../config/zookeeper.properties
```

- 카프카 실행
```
./kafka-server-start.bat ../../config/server.properties
```

- 카프카 토픽 생성 : imhungry
```
./kafka-topic.bat --zookeeper localhost:2181 --topic imhungry --create --partitions 1 --replication-factor 1
```

- 카프카 토픽 목록 확인
```
./kafka-topics.bat --list --zookeeper localhost:2181
```

- 카프카 토픽 메시지 전송
```
./kafka-console-producer.bat --broker-list localhost:9092 --topic imhungry
```

- 카프카 토픽 콘솔
```
./kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic imhungry --from-beginning
```

## 테스트용 Script

- 메뉴 요청
```
http POST http://localhost:8083/requests menuType="A" status="REQUESTED"
```

- 메뉴 취소
```
http POST http://localhost:8083/requests requestId=1 status="CANCELED"
http patch http://localhost:8083/requests/1 status="CANCELED"
```

http GET http://localhost:8081/dicisions

http GET http://localhost:8082/orders

http GET http://localhost:8083/requests

http GET http://localhost:8084/myPages