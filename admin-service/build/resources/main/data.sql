-- src/main/resources/data.sql
INSERT INTO MEMBER (NAME, EMAIL, ROLE, CREATED_AT) VALUES ('홍길동', 'hong@test.com', 'ADMIN', CURRENT_TIMESTAMP);
INSERT INTO MEMBER (NAME, EMAIL, ROLE, CREATED_AT) VALUES ('김철수', 'kim@test.com', 'USER', CURRENT_TIMESTAMP);

INSERT INTO DESTINATION (DESCRIPTION, LOCATION, NAME, CREATED_AT, PRICE, DURATION, INCLUDED, IMAGE_URL)
VALUES (
           '제주도에서 부모님과 함께 여유로운 시간을 보내는 효도 여행',
           '제주도',
           '제주도 효도 여행 패키지',
           CURRENT_TIMESTAMP,
           359000,
           '2박 3일',
           '풀빌라 숙박 포함',
           'https://www.agoda.com/wp-content/uploads/2024/07/Jeju-Island-1244x700.jpg'
       );