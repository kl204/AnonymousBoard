|이름|이메일|
|------|---|
|김경미|kmi_kim@naver.com|
|김선규|kl204@naver.com|
|박형석|dnflekf2748@gmail.com|
|안은비|qldms133@naver.com|
|이지민|jmlbcad@gmail.com|

# 📌 구현할 기능

- 공지사항
- FAQ
- 자유(익명)게시판(CRUD)
- 댓글(CRUD)
- 대댓글(CRUD)
- 게시글 페이징
- 댓글 페이징
- 게시글 좋아요, 싫어요
- 댓글 좋아요, 싫어요
- 게시글 조회수

# 📌 데이터베이스

<img width="840" alt="스크린샷 2023-07-10 오후 7 55 39" src="https://github.com/HengSsg/Anonymous_Board/assets/97237728/b16b8c73-ec8b-417a-8160-220f7b89256f">


### 특이사항

- 게시글의 PK를 댓글의 외래키로 받아서 1:다 형식으로 사용
- 댓글과 대댓글은 한 테이블을 사용하며 댓글의 PK을 대댓글의 외래키로 1:다 형식으로 사용

## 📌 ETC..

- 공지사항은 댓글을 일반형으로 구현
- 나머지는 댓글을 계층형으로 구현
- 익명 게시판으로서 글을 생성, 수정, 삭제 하려면 ID, PW가 있어야 한다.