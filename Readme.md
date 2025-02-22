# 이커머스 쇼핑몰 프로젝트

---
### 프로젝트 소개(설명)
해당 프로젝트는 eCommerce 쇼핑몰을 가정한 프로젝트로서 기능구현에 그치지 않고 고가용성과 확장성에 대해서 고민하였습니다.

멀티모듈 아키텍처 도입, 분산 트랜잭션 하에서의 보상트랜잭션 적용, 중복요청 방지 목적의 멱등성 Key를 이용한 Redis 분산락 적용 등 다양한 기술을 활용해 유지보수성 및 안정성과 확장성을 높였습니다.

또한 Master / Slave DB Replication과 MDC를 이용한 로깅을 활용해 실제 운영환경과 유사한 환경을 구축하기 위해 노력했습니다.

### 프로젝트 개요
- 기간
    - 2024.05 ~ (진행 중)
- 인원
    - 1인
- 목표
    - 고가용성과 확장성을 갖춘 아키텍처 설계 및 구현
    - 멀티모듈 구조로 역할과 책임을 분리하여 유지보수성 강화
    - 외부 결제 API 연동 시 발생할 수 있는 데이터 정합성 문제 대응(보상 트랜잭션 활용)
    - 분산 환경에서 중복 요청 방지와 동시성 제어로 안전한 트랜잭션 처리
    - 운영환경과 유사한 인프라 구성(Master/Slave DB Replication, MDC 기반 로깅)


---
### ERD

![Image](https://github.com/user-attachments/assets/4477bbbd-8217-43d0-a695-1c149846794f)
---
### 아키텍처 구조
![Image](https://github.com/user-attachments/assets/f40abe2a-7359-4f7e-ae7a-d521b0005f0a)

---
### 기술 스택
## 🖥 Backend
- Java 17
* SpringBoot 3.2.2
* JPA
* MySQL
* Redis
* github actions
* AWS EC2, RDS, S3
* Nginx

---

### 💡 메인 기능
- 사용자 관리
    - 회원가입 / 로그인

- 상품 관리
    - 상품 등록, 수정, 삭제
    - 상품 목록 조회 및 검색
- 주문 및 결제
    - 주문 생성
    - 주문 조회
    - 결제 요청
    - 결제 조회
- 실시간 채팅
    - 1:1 판매자 구매자 채팅
    - 이전 채팅 내역 조회

---
### 주요 이슈사항 및 트러블 슈팅

#### 1. 멀티모듈 도입
단일모듈에서 멀티모듈로 역할과 책임에 따른 모듈분리
각 모듈 간 역할에 따른 최소한의 의존성을 가질 수 있도록 구성

![Image](https://github.com/user-attachments/assets/d2f27dbe-dea1-449f-a49f-a898a32bfd47)
- app 모듈
    - web 모듈
        - Entry-point로서 Api의 입구에 해당하는 모듈
        - 외부에서 사용해야하는 애플리케이션 정의(사용자 Api)
- domain 모듈
    - domain-service 모듈
        - 도메인 로직을 조합하여 비즈니스 로직을 구성
    - domain-redis 모듈
        - redis에 종속적인 domain 모듈
    - domain-rdb 모듈
        - rdb에 종속적인 domain 모듈
- common 모듈
    - 공통 모듈 계층
    - 공통 예외 등 정의
- infra 모듈
    - 외부 API 통신 등을 위한 모듈
- 블로그 포스팅
    - [단일 모듈에서 멀티모듈로의 전환을 어떻게 할 수 있을까?](https://velog.io/@eodnjs568/%EB%A9%80%ED%8B%B0%EB%AA%A8%EB%93%88-%EB%8F%84%EC%9E%85%EA%B8%B0-1%ED%8E%B8)
    - [멀티모듈 구조를 개선할 수는 없을까?](https://velog.io/@eodnjs568/%EB%A9%80%ED%8B%B0%EB%AA%A8%EB%93%88-%EB%8F%84%EC%9E%85%EA%B8%B0-2%ED%8E%B8)
    - [각 모듈의 역할과 책임과 의존성의 범위는 어떻게 나눠야할까? 또 Domain객체와 Entity는 어떻게 나눌 수 있을까?](https://velog.io/@eodnjs568/%EC%87%BC%ED%95%91%EB%AA%B0-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EB%A9%80%ED%8B%B0%EB%AA%A8%EB%93%88-%EB%8F%84%EC%9E%85%EA%B8%B0-3%ED%8E%B8)

#### 2. 분산 트랜잭션에서 보상트랜잭션 적용
외부 API(토스 페이먼츠)를 연동하여 결제기능을 구현하였으나, WAS에서 서버 중단 혹은 에러 발생과 같은 예외 시 외부API서버와 데이터 정합성 맞추기 위해 보상트랜잭션(Compensating Transaction) 적용
- 블로그 포스팅
    - [외부 API 호출에서 보상트랜잭션 적용과정](https://velog.io/@eodnjs568/%EC%87%BC%ED%95%91%EB%AA%B0-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EC%99%B8%EB%B6%80-API-%ED%98%B8%EC%B6%9C%EC%97%90%EC%84%9C-%EB%B3%B4%EC%83%81%ED%8A%B8%EB%9E%9C%EC%9E%AD%EC%85%98-%EC%A0%81%EC%9A%A9)

#### 3. 동일 주문 및 결제 요청에 대하여 Redis 분산락을 적용하여 동시성 문제 해결
주문과 결제와 같이 중복 요청에 대하여 같은 결과를 응답해야하는, 즉 멱등성을 보장해야하는 작업에 대하여 멱등성 Key 및 Redis 분산락을 통해 동시성 문제 해소
- 블로그 포스팅
    - [분산환경에서의 동시성 문제](https://velog.io/@eodnjs568/%EC%87%BC%ED%95%91%EB%AA%B0-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EB%8F%99%EC%8B%9C%EC%84%B1-%EB%AC%B8%EC%A0%9C)