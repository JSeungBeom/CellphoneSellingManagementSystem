## 📝 프로젝트 개요
LG U+ 유레카 과정 <br><br>
JDBC와 Swing을 이용한 2 Tier Architecture의 휴대폰 판매관리 시스템 구현 미니 프로젝트
<br />

## ✅ 기능 명세
- **관리자**
  - 관리자는 관리자 계정으로 접속할 수 있어야 한다.
  - 관리자는 휴대폰의 현재 재고 현황을 확인할 수 있어야 한다.
  - 관리자는 휴대폰을 등록 / 수정 / 삭제할 수 있어야 한다.
  - 관리자는 전체 주문 현황을 확인할 수 있어야 한다.

- **사용자**
  - 사용자는 사용자 계정으로 접속할 수 있어야 한다.
  - 사용자는 회원가입을 할 수 있어야 한다.
    - 사용자는 중복된 계정명을 가질 수 없다.
  - 사용자는 휴대폰의 현재 재고 현황을 확인할 수 있어야 한다.
  - 사용자는 휴대폰을 기종 명으로 검색할 수 있어야 한다.
  - 사용자는 휴대폰을 구매할 수 있어야 한다.
    - 단, 재고량보다 초과하여 휴대폰을 구매할 수 없다.
  - 사용자는 본인이 주문한 주문 현황을 확인할 수 있어야 한다.
    - 사용자는 주문을 취소할 수 있어야 한다.  

## ⚙ 기술 스택
### Back-end
<div>
<img src="https://github.com/yewon-Noh/readme-template/blob/main/skills/Java.png?raw=true" width="80">
<img src="https://github.com/yewon-Noh/readme-template/blob/main/skills/Mysql.png?raw=true" width="80">
</div>

### Tools
<div>
<img src="https://github.com/yewon-Noh/readme-template/blob/main/skills/Github.png?raw=true" width="80">
</div>

<br />

## 🛠️ ERD
![Image](https://github.com/user-attachments/assets/9925f265-c133-4c8e-ba89-35c34247d71c)

<br />

## 기술적 이슈와 해결 과정
- 비밀번호 암호화 <br>
<a href="https://it-developments.tistory.com/22"> Hashing, Salt </a>
