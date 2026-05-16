/* =========================================================
   dropdb.sql
   DB2026Team10 데이터베이스의 뷰, 테이블, 데이터를 삭제한다.
   ========================================================= */

USE DB2026Team10;

-- 뷰 먼저 삭제
DROP VIEW IF EXISTS Member_Reservation_View;
DROP VIEW IF EXISTS Studio_Search_View;

-- 외래키를 참조하는 자식 테이블부터 삭제
DROP TABLE IF EXISTS Reservation;
DROP TABLE IF EXISTS Studio_mood;
DROP TABLE IF EXISTS Studio_Background;
DROP TABLE IF EXISTS Photographer;

-- 부모 테이블 삭제
DROP TABLE IF EXISTS Mood;
DROP TABLE IF EXISTS Background;
DROP TABLE IF EXISTS Studio;
DROP TABLE IF EXISTS Member;

-- 데이터베이스 삭제
DROP DATABASE IF EXISTS DB2026Team10;