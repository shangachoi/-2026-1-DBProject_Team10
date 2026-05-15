/* ==========================================================
   현재 작성 테이블
	Member
	Studio
	Background
	Mood
	Photographer
	Reservation
   =========================================================== */



/* =========================================================
   1. 데이터베이스 선택
   ========================================================= */

DROP DATABASE IF EXISTS DB2026Team10; -- 테스트용으로 추가
CREATE DATABASE DB2026Team10; -- 테스트용 "
USE DB2026Team10;


/* =========================================================
   2. 기존 기본 테이블 삭제
   재실행 시 오류를 방지하기 위해 자식 테이블부터 삭제한다.

   주의:
   연결 테이블 Studio_Background, Studio_Mood는
   삭제 테이블은 안넣어놔서 밑에다 추가하시면 됩니다!. 
   -> 연결 테이블 및 뷰 추가했습니다. 연결 테이블은 Reservation 앞에 넣었습니다!
   ========================================================= */

DROP VIEW IF EXISTS Member_Reservation_View;
DROP VIEW IF EXISTS Studio_Search_View;

DROP TABLE IF EXISTS Reservation;
DROP TABLE IF EXISTS Studio_Mood;
DROP TABLE IF EXISTS Studio_Background;
DROP TABLE IF EXISTS Photographer;
DROP TABLE IF EXISTS Mood;
DROP TABLE IF EXISTS Background;
DROP TABLE IF EXISTS Studio;
DROP TABLE IF EXISTS Member;


/* =========================================================
   3. Member 테이블 생성
   회원 정보를 저장하는 기본 테이블

   member_id : 회원 ID, 기본키
   password  : 비밀번호
   name      : 회원 이름
   phone     : 전화번호
   ========================================================= */

CREATE TABLE Member (
    member_id VARCHAR(20) NOT NULL,
    password VARCHAR(100) NOT NULL,
    name VARCHAR(30) NOT NULL,
    phone VARCHAR(20) NOT NULL,

    CONSTRAINT pk_member PRIMARY KEY (member_id)	/*member_id를 기본키로 지정*/
);


/* =========================================================
   4. Studio 테이블 생성
   사진관 정보를 저장하는 기본 테이블

   studio_id    : 사진관 ID, 기본키
   studio_name  : 사진관 이름
   location     : 사진관 위치
   studio_phone : 사진관 전화번호
   ========================================================= */

CREATE TABLE Studio (
    studio_id INT NOT NULL,
    studio_name VARCHAR(50) NOT NULL,
    location VARCHAR(100) NOT NULL,
    studio_phone VARCHAR(20) NOT NULL,

    CONSTRAINT pk_studio PRIMARY KEY (studio_id)	/*studio_id를 기본키로 지정*/
);


/* =========================================================
   5. Background 테이블 생성
   사진배경 정보를 저장하는 기본 테이블

   background_id : 사진배경 ID, 기본키
   color         : 사진컬러
   b_description : 배경설명
   ========================================================= */

CREATE TABLE Background (
    background_id INT NOT NULL,
    color VARCHAR(30) NOT NULL,
    b_description VARCHAR(200) NOT NULL,

    CONSTRAINT pk_background PRIMARY KEY (background_id)	/*background_id를 기본키로 지정*/
);


/* =========================================================
   6. Mood 테이블 생성
   사진무드 정보를 저장하는 기본 테이블

   mood_id       : 사진무드 ID, 기본키
   mood_name     : 사진무드명
   m_description : 무드설명
   ========================================================= */

CREATE TABLE Mood (
    mood_id INT NOT NULL,
    mood_name VARCHAR(30) NOT NULL,
    m_description VARCHAR(200) NOT NULL,

    CONSTRAINT pk_mood PRIMARY KEY (mood_id)	/*mood_id를 기본키로 지정*/
);


/* =========================================================
   7. Photographer 테이블 생성
   사진작가 정보를 저장하는 기본 테이블

   photographer_id   : 사진작가 ID, 기본키
   photographer_name : 사진작가 이름
   studio_id         : 소속 사진관 ID, Studio 테이블 참조
   career            : 경력
   specialty         : 전문분야

   관계:
   Studio 1 : N Photographer
   하나의 사진관에는 여러 명의 사진작가가 소속될 수 있고,
   한 명의 사진작가는 하나의 사진관에 소속된다.
   ========================================================= */

CREATE TABLE Photographer (
    photographer_id INT NOT NULL,
    photographer_name VARCHAR(30) NOT NULL,
    studio_id INT NOT NULL,
    career VARCHAR(50) NOT NULL,
    specialty VARCHAR(50) NOT NULL,

    CONSTRAINT pk_photographer PRIMARY KEY (photographer_id),	/*photographer_id를 기본키로 지정*/

    /* Reservation에서 studio_id와 photographer_id 조합 검사를 할 수 있게 하기 위한 UNIQUE 제약 (Reservation에서 복합 외래키를 걸기 위해 추가) */
    CONSTRAINT uq_photographer_studio UNIQUE (studio_id, photographer_id),

    CONSTRAINT fk_photographer_studio
        FOREIGN KEY (studio_id)
        REFERENCES Studio(studio_id)
        ON DELETE RESTRICT	/*참조 중엔 사진관 삭제 금지*/
        ON UPDATE CASCADE	/*부모 테이블 studio_id 변경시 참조 테이블 studio_id도 함께 변경*/
);


/* =========================================================
   8. Studio_Background 연결 테이블 생성
   사진관과 사진배경의 N:M 관계를 관리하는 테이블

   studio_id     : 사진관 ID, Studio 테이블 참조
   background_id : 사진배경 ID, Background 테이블 참조

   관계:
   하나의 사진관은 여러 개의 사진배경을 제공할 수 있고,
   하나의 사진배경은 여러 사진관에서 제공될 수 있다.
   ========================================================= */

CREATE TABLE Studio_Background (
    studio_id INT NOT NULL,
    background_id INT NOT NULL,

    CONSTRAINT pk_studio_background PRIMARY KEY (studio_id, background_id),

    CONSTRAINT fk_studio_background_studio
        FOREIGN KEY (studio_id)
        REFERENCES Studio(studio_id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,

    CONSTRAINT fk_studio_background_background
        FOREIGN KEY (background_id)
        REFERENCES Background(background_id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);


/* =========================================================
   9. Studio_Mood 연결 테이블 생성
   사진관과 사진무드의 N:M 관계를 관리하는 테이블

   studio_id : 사진관 ID, Studio 테이블 참조
   mood_id   : 사진무드 ID, Mood 테이블 참조

   관계:
   하나의 사진관은 여러 개의 사진무드를 제공할 수 있고,
   하나의 사진무드는 여러 사진관에서 제공될 수 있다.
   ========================================================= */

CREATE TABLE Studio_Mood (
    studio_id INT NOT NULL,
    mood_id INT NOT NULL,

    CONSTRAINT pk_studio_mood PRIMARY KEY (studio_id, mood_id),

    CONSTRAINT fk_studio_mood_studio
        FOREIGN KEY (studio_id)
        REFERENCES Studio(studio_id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,

    CONSTRAINT fk_studio_mood_mood
        FOREIGN KEY (mood_id)
        REFERENCES Mood(mood_id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);


/* =========================================================
   10. Reservation 테이블 생성
   예약 정보를 저장하는 기본 테이블

   reservation_id   : 예약 ID, 기본키
   member_id        : 예약한 회원 ID
   studio_id        : 예약한 사진관 ID
   photographer_id  : 선택한 사진작가 ID
   background_id    : 선택한 사진배경 ID
   mood_id          : 선택한 사진무드 ID
   reservation_date : 예약 날짜
   reservation_time : 예약 시간
   state            : 예약 상태

   예약 상태는 '예약완료', '취소', '촬영완료' 중 하나만 가능하다.
   ========================================================= */

CREATE TABLE Reservation (
    reservation_id INT NOT NULL,
    member_id VARCHAR(20) NOT NULL,
    studio_id INT NOT NULL,
    photographer_id INT NOT NULL,
    background_id INT NOT NULL,
    mood_id INT NOT NULL,
    reservation_date DATE NOT NULL,
    reservation_time TIME NOT NULL,
    state VARCHAR(20) NOT NULL,

    CONSTRAINT pk_reservation PRIMARY KEY (reservation_id),	/*reservation_id를 기본키로 지정*/

    /* 예약은 반드시 한 명의 회원에 의해 생성된다. */
    CONSTRAINT fk_reservation_member
        FOREIGN KEY (member_id)
        REFERENCES Member(member_id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,

    /* 예약은 반드시 하나의 사진관을 선택한다. */
    CONSTRAINT fk_reservation_studio
        FOREIGN KEY (studio_id)
        REFERENCES Studio(studio_id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,

    /* 예약은 반드시 하나의 사진작가를 선택한다. */
    CONSTRAINT fk_reservation_photographer
        FOREIGN KEY (photographer_id)
        REFERENCES Photographer(photographer_id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,

    /* 예약은 반드시 하나의 사진배경을 선택한다. */
    CONSTRAINT fk_reservation_background
        FOREIGN KEY (background_id)
        REFERENCES Background(background_id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,

    /* 예약은 반드시 하나의 사진무드를 선택한다. */
    CONSTRAINT fk_reservation_mood
        FOREIGN KEY (mood_id)
        REFERENCES Mood(mood_id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,

    /* 예약한 사진작가가 해당 사진관 소속인지 확인한다. */
    CONSTRAINT fk_reservation_studio_photographer
        FOREIGN KEY (studio_id, photographer_id)
        REFERENCES Photographer(studio_id, photographer_id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,

    /* 예약 상태 값 제한 */
    CONSTRAINT chk_reservation_state
        CHECK (state IN ('예약완료', '취소', '촬영완료'))
);


/* =========================================================
   인덱스 생성
   검색 성능 향상을 위해 자주 검색되는 컬럼에 인덱스를 생성한다.
   ========================================================= */

CREATE INDEX idx_studio_name
ON Studio(studio_name);

CREATE INDEX idx_background_color
ON Background(color);

CREATE INDEX idx_mood_name
ON Mood(mood_name);

CREATE INDEX idx_reservation_member
ON Reservation(member_id);

CREATE INDEX idx_reservation_date_time
ON Reservation(reservation_date, reservation_time);


/* =========================================================
   View 1. Studio_Search_View
   사진관 검색 결과를 보여주기 위한 뷰

   사용자가 사진관을 검색할 때 사진관 정보와 함께
   사진작가, 사진배경, 사진무드 정보를 한 번에 조회할 수 있다.
   ========================================================= */

CREATE VIEW Studio_Search_View AS
SELECT
    s.studio_id,
    s.studio_name,
    s.location,
    s.studio_phone,
    p.photographer_name,
    b.color,
    m.mood_name
FROM Studio s
JOIN Photographer p
    ON s.studio_id = p.studio_id
JOIN Studio_Background sb
    ON s.studio_id = sb.studio_id
JOIN Background b
    ON sb.background_id = b.background_id
JOIN Studio_Mood sm
    ON s.studio_id = sm.studio_id
JOIN Mood m
    ON sm.mood_id = m.mood_id;


/* =========================================================
   View 2. Member_Reservation_View
   회원 예약 조회 결과를 보여주기 위한 뷰

   회원이 자신의 예약 정보를 조회할 때
   회원 이름, 사진관 이름, 사진배경, 사진무드,
   사진작가 이름, 예약 날짜, 예약 시간, 예약 상태를
   한 번에 확인할 수 있다.
   ========================================================= */

CREATE VIEW Member_Reservation_View AS
SELECT
    mem.member_id,
    mem.name,
    r.reservation_id,
    s.studio_name,
    b.color,
    m.mood_name,
    p.photographer_name,
    r.reservation_date,
    r.reservation_time,
    r.state
FROM Reservation r
JOIN Member mem
    ON r.member_id = mem.member_id
JOIN Studio s
    ON r.studio_id = s.studio_id
JOIN Background b
    ON r.background_id = b.background_id
JOIN Mood m
    ON r.mood_id = m.mood_id
JOIN Photographer p
    ON r.photographer_id = p.photographer_id;
    
    
/* =========================================================
   초기 데이터 삽입
   전체 테이블을 합쳐 30개 이상의 레코드를 삽입한다.
   ========================================================= */

/* Member 초기 데이터 */
INSERT INTO Member (member_id, password, name, phone) VALUES
('M001', 'pw001', '김하은', '010-1111-1111'),
('M002', 'pw002', '이서연', '010-2222-2222'),
('M003', 'pw003', '박지민', '010-3333-3333'),
('M004', 'pw004', '최유진', '010-4444-4444'),
('M005', 'pw005', '정민수', '010-5555-5555');


/* Studio 초기 데이터 */
INSERT INTO Studio (studio_id, studio_name, location, studio_phone) VALUES
(1, '온유사진관', '서울시 마포구', '02-111-1111'),
(2, '무드스튜디오', '서울시 강남구', '02-222-2222'),
(3, '하루필름스튜디오', '서울시 종로구', '02-333-3333'),
(4, '블룸사진관', '서울시 성동구', '02-444-4444');


/* Background 초기 데이터 */
INSERT INTO Background (background_id, color, b_description) VALUES
(1, '화이트', '깔끔하고 밝은 분위기의 기본 배경'),
(2, '블랙', '차분하고 시크한 분위기의 어두운 배경'),
(3, '플라워', '꽃 장식이 있는 화사한 배경'),
(4, '베이지', '따뜻하고 부드러운 느낌의 배경'),
(5, '블루', '청량하고 쿨한 분위기의 배경'),
(6, '빈티지', '레트로하고 감성적인 분위기의 배경');


/* Mood 초기 데이터 */
INSERT INTO Mood (mood_id, mood_name, m_description) VALUES
(1, '따뜻한', '부드럽고 편안한 분위기의 촬영 무드'),
(2, '세련된', '깔끔하고 고급스러운 분위기의 촬영 무드'),
(3, '시크한', '도시적이고 차분한 분위기의 촬영 무드'),
(4, '쿨한', '청량하고 개성 있는 분위기의 촬영 무드'),
(5, '청순한', '맑고 자연스러운 분위기의 촬영 무드'),
(6, '빈티지한', '레트로하고 감성적인 분위기의 촬영 무드');


/* Photographer 초기 데이터 */
INSERT INTO Photographer (photographer_id, photographer_name, studio_id, career, specialty) VALUES
(1, '한지우', 1, '3년', '프로필 사진'),
(2, '강민재', 1, '5년', '우정 사진'),
(3, '서도윤', 2, '4년', '컨셉 사진'),
(4, '윤서아', 2, '6년', '증명 사진'),
(5, '문하린', 3, '2년', '감성 사진'),
(6, '오지훈', 4, '7년', '커플 사진');


/* Studio_Background 초기 데이터 */
INSERT INTO Studio_Background (studio_id, background_id) VALUES
(1, 1),
(1, 3),
(1, 4),
(2, 1),
(2, 2),
(2, 5),
(3, 3),
(3, 4),
(3, 6),
(4, 1),
(4, 5),
(4, 6);


/* Studio_Mood 초기 데이터 */
INSERT INTO Studio_Mood (studio_id, mood_id) VALUES
(1, 1),
(1, 2),
(1, 5),
(2, 2),
(2, 3),
(2, 4),
(3, 1),
(3, 5),
(3, 6),
(4, 3),
(4, 4),
(4, 6);


/* Reservation 초기 데이터 */
INSERT INTO Reservation (
    reservation_id,
    member_id,
    studio_id,
    photographer_id,
    background_id,
    mood_id,
    reservation_date,
    reservation_time,
    state
) VALUES
(1, 'M001', 1, 1, 1, 1, '2026-05-20', '10:00:00', '예약완료'),
(2, 'M002', 1, 2, 3, 5, '2026-05-20', '13:00:00', '예약완료'),
(3, 'M003', 2, 3, 2, 3, '2026-05-21', '11:00:00', '촬영완료'),
(4, 'M004', 3, 5, 6, 6, '2026-05-22', '15:00:00', '예약완료'),
(5, 'M005', 4, 6, 5, 4, '2026-05-23', '14:00:00', '취소'),
(6, 'M001', 2, 4, 1, 2, '2026-05-24', '16:00:00', '예약완료');