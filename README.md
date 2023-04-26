# Getting Started

### 작업자 환경 세팅

- (mac 기준) CMD + , 설정 창 열기
- console 검색, 좌측 JAVADEBUGGER 카테고리 내, InternalConsole 로 변경

### application-dev.yml

```yml
server:
  port: 8000
  servlet:
    context-path: /

spring:
  datasource:
    driver-class-name: org.mariadb.jdbc.Driver
    url: jdbc:mariadb://localhost:3306/ottodb
    username: root
    password: 1234
  jpa:
    #open-in-view: false
    hibernate:
      ddl-auto: create #false 로 되어있으면 테이블 생성이 안이루어짐, 한 번 테이블 생성되고 나면 false로 바꿔주어야 DB 초기화 안됨,
    properties:
      "[hibernate.format_sql]": true
  output:
    ansi:
      enabled: always

logging:
  level:
    "[org.hibernate.SQL]": DEBUG
    "[site.metacoding.white]": DEBUG
    root: DEBUG
    "[org.springframework.boot.autoconfigure]": ERROR
```

### Hibernate testing 완료

```java
package com.mambo.otto.ottoserver.domain.board;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Board {

    @Id
    @GeneratedValue
    private Long id;
    private String boardTitle;
    @Column(length = 1000)
    private String content;
}
```

- Run 실행 시, 아래와 같이 콘솔창에서 확인할 수 있음
  <img src="/Users/mambogroup/Desktop/screenshot.png"  width="781" height="234">
