# MSA 기반 여행 패키지 예약 플랫폼

## 프로젝트 소개
MSA 아키텍처를 기반으로 여행 패키지 예약 서비스를 구축한 프로젝트입니다.
사용자와 관리자가 모두 직관적이고 편리하게 사용할 수 있는 여행 플랫폼을 목표로 개발했습니다

## 개발 기간
- 3.31 ~ 4.11

## 기술 스택
### IDE
- Intellij Ultimate
### Frontend
- Vue.js (Composition API)
- Bootstrap 5
- Axios
- CSS
- JavaScript
### Backend
- Spring Boot 3.1.5 (JDK 17)
- MyBatis
- H2 Database
- Spring Cloud Gateway
- Eureka

## 프로젝트 구조
```
travelpackage-main/
├── admin-service (포트: 8081)
├── board-service (포트: 8007)
├── detail-service (포트: 8004)
├── gateway-service (포트: 8000)
├── eureka-server (포트: 8761)
└── travel-product-service (포트: 8003)
```

## 서비스 설명
### 1. Admin Service (8081)
- 회원 목록 조회, 수정, 삭제 기능 제공
- 여행지(패키지) 등록, 수정, 삭제 기능 담당
- 관리자 전용 API 제공으로 데이터 유지보수 지원

### 2. Board Service (8007)
- 공지사항 게시판 CRUD (생성, 조회, 수정, 삭제) 기능 제공
- 사용자에게 주요 정보를 효과적으로 전달

### 3. Detail Service (8004)
- 여행 상품 상세 정보 제공
- 예약 및 결제 기능 담당
- 인원 수 증감 및 실시간 총 결제 금액 계산 기능 구현

### 4. Gateway Service (8000)
- API Gateway 역할 수행
- 모든 외부 요청을 서비스별로 라우팅 처리
- JWT 인증 및 인가 처리 (Interceptor 기반)
- 프론트엔드 정적 리소스 제공

### 5. Eureka Server (8761)
- 각 마이크로서비스를 등록하고 관리하는 서비스 디스커버리 서버
- 서비스 간 통신을 위한 위치 정보 제공
- MSA 구조에서 동적 스케일링 및 장애 대응 지원

### 6. Travel Product Service (8003)
- 여행 상품 목록 조회, 검색, 정렬 기능 제공
- 상품의 핵심 정보(이름, 지역, 가격 등) 리스트화하여 제공
- RESTful 방식 API로 다른 서비스와 연동 용이

## 실행 방법
1. 프로젝트를 클론
2. 각 서비스에 대한 폴더로 들어가 @SpringBootApplication이 붙은 메인 클래스 (예: AdminServiceApplication.java)를 찾아 Run을 시킴
3. frontend 폴더에 들어가 ``` npm install ``` 명령어를 실행한 뒤 ``` npm run dev ``` 명령어 입력

## 로그인 서비스
### 테스트 계정
- 관리자 계정
  - 사용자명: admin
  - 비밀번호: admin123
- 일반 사용자 계정
  - 사용자명: user1
  - 비밀번호: user123
  - 사용자명: user2
  - 비밀번호: user123
 
 ## 시연동영상
https://youtu.be/kbE0Xgdu3rc
