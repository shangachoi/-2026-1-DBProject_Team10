/* =========================================================
   dropdb.sql
   DB2026Team10 데이터베이스의 뷰, 테이블, 데이터를 삭제한다.
   ========================================================= */

USE DB2026Team10;

-- 뷰 먼저 삭제
DROP VIEW IF EXISTS member_reservation_view;
DROP VIEW IF EXISTS studio_search_view;

-- 외래키를 참조하는 자식 테이블부터 삭제
DROP TABLE IF EXISTS reservation;
DROP TABLE IF EXISTS studio_mood;
DROP TABLE IF EXISTS studio_background;
DROP TABLE IF EXISTS photographer;

-- 부모 테이블 삭제
DROP TABLE IF EXISTS mood;
DROP TABLE IF EXISTS background;
DROP TABLE IF EXISTS studio;
DROP TABLE IF EXISTS member;

-- 데이터베이스 삭제
DROP DATABASE IF EXISTS DB2026Team10;