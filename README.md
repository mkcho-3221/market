# market

사용자간 거래가 가능한 market 서비스  

## 목차
[1. 프로그램 주요 기능 ](#1-프로그램-주요-기능)  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; [1) 회원가입](#1-회원가입)  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; [2) 로그인](#2-로그인)  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; [3) 제품 관리](#3-제품-관리)  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; [4) 거래](#4-거래)  
[2. 사용한 기술 스택 및 라이브러리 ](#2-사용한-기술-스택-및-라이브러리)  
[3. ERD 데이터 모델링 ](#3-erd-데이터-모델링)  

---

## 1. 프로그램 주요 기능  
### 1) 회원가입  
- [x] 닉네임, 이메일, 비밀번호를 입력해 회원가입 할 수 있음.
- [x] 이메일은 중복 불가함.
### 2) 로그인  
- [x] 스프링 시큐리티를 활용한 로그인 기능 구현.
- [x] 제품 등록과 구매는 회원만 가능함.  
- [x] 비회원은 등록된 제품의 목록조회와 상세조회만 가능함.  
### 3) 제품 관리  
- [x] 등록된 제품에는 "제품명", "가격", "예약상태"가 포함되어 있어야하고, 목록 상세 조회시에 예약 상태를 포함해야 함.  
- [x] 제품의 상태는 "판매중", "예약중", "완료" 세 가지가 존재함.  
### 4) 거래  
- [x] 구매자가 제품의 상세 페이지에서 구매하기 버튼을 누르면 거래가 시작됨.
- [ ] 판매자와 구매자는 제품의 상세 정보를 조회하면, 당사자간의 거래 내역을 확인할 수 있음.  
- [ ] 모든 사용자는 내가 "구매한 용품(내가 구매자)"과 "예약 중인 용품(내가 구매자/판매자 모두)"의 목록을 확인할 수 있음.
- [ ] 판매자는 거래 진행 중인 구매자에 대해 "판매 승인"을 하는 경우 거래가 완료됨.  
  
## 2. 사용한 기술 스택 및 라이브러리  
<div align=center> 
  <img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white"> 
  <img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"> 
  <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"> 
  <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">
  <img src="https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=Postman&logoColor=white"/>
</div>

  - 라이브러리 : Spring Web, Spring Data JPA, Spring Security, Lombok, JWT
    
## 3. ERD 데이터 모델링  
  <img width="754" alt="image" src="https://github.com/user-attachments/assets/f9334bbe-874c-4651-925c-8d932dc4d0cb">  
