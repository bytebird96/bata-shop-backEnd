# JDK 17 기반의 가벼운 Docker 이미지 사용
FROM openjdk:17-jdk-slim

#컨테이너 내 작업 디렉토리 설정
WORKDIR /app

#JAR 파일을 컨테이너로 복사
COPY target/*.jar app.jar

#컨테이너가 실행될 때 실행할 명령어
ENTRYPOINT ["java", "-jar", "app.jar"]