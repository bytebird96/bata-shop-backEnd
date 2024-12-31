# Bata-Shop: Backend

👉🏻 **Summary**  
이 프로젝트는 **Spring Boot**와 **MariaDB**를 활용하여 쇼핑몰 백엔드를 구현하기 위한 목적으로 제작되었습니다. 상품 관리, 사용자 인증 및 권한 부여, 장바구니, 주문 기능 등 다양한 쇼핑몰 핵심 기능을 제공할 예정입니다. 프론트엔드는 **React** 기반으로 구현되며, 백엔드와 REST API를 통해 통신합니다.

👉🏻 관련 레포지토리

Bata-Shop: Frontend : https://github.com/bytebird96/bata-shop-frontend.git

---
👉🏻 **구현 화면(추가 중)**

**예시 이미지의 경우 GPT로 생성**

<img src="https://github.com/user-attachments/assets/b767e3c8-a904-44d9-b39b-50cd0b4fa51c"/>


---
👉🏻 **사용 기술 스택**
- **Spring Boot**  
  - Java 기반의 웹 애플리케이션 프레임워크로, 빠르고 효율적인 개발 환경을 제공하기 위해 사용하였습니다.
  - RESTful API 구현을 통해 프론트엔드와 데이터 교환을 수행합니다.
  
- **MariaDB**  
  - 관계형 데이터베이스로, 상품 정보, 사용자 정보, 주문 내역 등을 저장하고 관리합니다.
  - Hibernate와 JPA를 통해 데이터베이스와 객체 간 매핑을 간편하게 처리합니다.

- **Spring Data JPA**  
  - ORM(Object Relational Mapping)을 지원하여 데이터베이스 연동을 단순화하고, 유지보수를 쉽게 하기 위해 사용하였습니다.
  
- **Lombok**  
  - Boilerplate 코드를 줄이고, 가독성을 높이기 위해 선택하였습니다.

- **Logback**  
  - 애플리케이션 로그 관리를 위해 사용하며, 디버깅과 모니터링에 활용됩니다.
---

👉🏻 **상세 기능(예정)**
1. **사용자 관리**
   - 회원가입 및 로그인 (JWT 기반 인증)
   - 사용자 정보 수정 및 조회
   - 관리자 권한을 활용한 사용자 관리

2. **상품 관리**
   - 상품 등록, 조회, 수정, 삭제
   - 상품 카테고리 분류 및 필터링
   - 페이징 및 정렬 기능을 통한 상품 목록 제공

3. **주문 및 장바구니**
   - 장바구니에 상품 추가/삭제
   - 주문 생성 및 주문 내역 관리
   - 결제 연동 (추후 기능 추가 예정)

4. **API**
   - RESTful API 설계로 프론트엔드와의 통신 최적화
   - Swagger(OpenAPI)를 활용한 API 문서화 (예정)

5. **관리자 기능**
   - 상품 및 주문 관리
   - 사용자 관리 및 데이터 대시보드 제공 (예정)

👉🏻 **현재 상태**
- 기본 상품 관리 및 페이징 기능 구현
- REST API 기본 설계 완료
- 사용자 관리 및 인증 기능 개발 중
- 프론트엔드와 데이터 통신 테스트 진행 중

👉🏻 **설치 및 실행**
### 1. 클론 및 빌드
```bash
git clone https://github.com/your-repository/bata-shop-backend.git
cd bata-shop-backend
mvn clean install
