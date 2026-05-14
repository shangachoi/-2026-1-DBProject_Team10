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

USE DB2026Team10;


/* =========================================================
   2. 기존 기본 테이블 삭제
   재실행 시 오류를 방지하기 위해 자식 테이블부터 삭제한다.

   주의:
   연결 테이블 Studio_Background, Studio_Mood는
   삭제 테이블은 안넣어놔서 밑에다 추가하시면 됩니다!.
   ========================================================= */

DROP TABLE IF EXISTS Reservation;
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
   8. Reservation 테이블 생성
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