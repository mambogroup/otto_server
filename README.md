# Getting Started

###

- application-dev.yml / jpa-ddl-auto 옵션

```yml
#create: 기존테이블 삭제 후 다시 생성 (DROP + CREATE)
#create-drop: create와 같으나 종료시점에 테이블 DROP
#update: 변경분만 반영(운영DB에서는 사용하면 안됨)
#validate: 엔티티와 테이블이 정상 매핑되었는지만 확인
#none: 사용하지 않음(사실상 없는 값이지만 관례상 none이라고 한다.)
```
