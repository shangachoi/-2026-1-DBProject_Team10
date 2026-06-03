package main;

import dao.*;
import dto.*;
import util.Util;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Connection conn = Util.getConnection();
                Scanner scanner = new Scanner(System.in)) {

            MemberDAO memberDao = new MemberDAO(conn);
            StudioDAO studioDao = new StudioDAO(conn);
            PhotographerDAO photographerDao = new PhotographerDAO(conn);
            BackgroundDAO backgroundDao = new BackgroundDAO(conn);
            MoodDAO moodDao = new MoodDAO(conn);
            ReservationDAO reservationDao = new ReservationDAO(conn);
            ViewDAO viewDao = new ViewDAO(conn);

            boolean running = true;
            boolean loggedIn = false;
            String currentMemberId = null;

            while (running) {
                if (!loggedIn) {
                    System.out.println("\n=== 사진관 예약 시스템 ===");
                    System.out.println("1. 회원가입");
                    System.out.println("2. 로그인");
                    System.out.println("0. 종료");
                    System.out.print("선택: ");
                    String input = scanner.nextLine().trim();

                    switch (input) {
                        case "1": // register
                            System.out.print("회원 ID: ");
                            String regId = scanner.nextLine().trim();
                            System.out.print("비밀번호: ");
                            String regPw = scanner.nextLine().trim();
                            System.out.print("이름: ");
                            String regName = scanner.nextLine().trim();
                            System.out.print("전화번호: ");
                            String regPhone = scanner.nextLine().trim();

                            Member newMember = new Member(regId, regPw, regName, regPhone);
                            try {
                                if (memberDao.insert(newMember)) {
                                    System.out.println("회원가입 성공.");
                                } else {
                                    System.out.println("회원가입 실패(중복 또는 DB 오류).");
                                }
                            } catch (Exception e) {
                                System.err.println("회원가입 오류: " + e.getMessage());
                            }
                            break;

                        case "2": // login
                            System.out.print("회원 ID: ");
                            String loginId = scanner.nextLine().trim();
                            System.out.print("비밀번호: ");
                            String loginPw = scanner.nextLine().trim();
                            try {
                                Optional<Member> mOpt = memberDao.findById(loginId);
                                if (mOpt.isPresent() && mOpt.get().getPassword().equals(loginPw)) {
                                    loggedIn = true;
                                    currentMemberId = loginId;
                                    System.out.println("로그인 성공. 환영합니다, " + mOpt.get().getName());
                                } else {
                                    System.out.println("로그인 실패(아이디/비밀번호 확인).");
                                }
                            } catch (Exception e) {
                                System.err.println("로그인 오류: " + e.getMessage());
                            }
                            break;

                        case "0":
                            running = false;
                            break;

                        default:
                            System.out.println("잘못된 입력입니다.");
                    }

                } else {
                    // user menu
                    System.out.println("\n[사용자 메뉴]");
                    System.out.println("1. 전체 사진관 조회");
                    System.out.println("2. 사진관 이름으로 검색");
                    System.out.println("3. 사진관 상세 정보 조회");
                    System.out.println("4. 지역별 사진관 조회");
                    System.out.println("5. 전체 사진작가 조회");
                    System.out.println("6. 사진관별 사진작가 조회");
                    System.out.println("7. 전문 분야 별 사진작가 조회");
                    System.out.println("8. 경력 별 사진작가 조회");
                    System.out.println("9. 전체 배경 조회");
                    System.out.println("10. 사진관별 배경 조회");
                    System.out.println("11. 전체 무드 조회");
                    System.out.println("12. 사진관별 무드 조회");
                    System.out.println("13. 예약 생성");
                    System.out.println("14. 내 예약 조회");
                    System.out.println("15. 예약 상태 수정");
                    System.out.println("16. 예약 취소");
                    System.out.println("17. 사진관 검색 뷰 조회");
                    System.out.println("18. 내 예약 뷰 조회");
                    System.out.println("19. 로그아웃");
                    System.out.println("0. 종료");
                    System.out.print("선택: ");

                    String cmd = scanner.nextLine().trim();
                    try {
                        switch (cmd) {
                            case "1": // 전체 사진관 조회
                                List<Studio> all = studioDao.findAll();
                                System.out.println("=== 전체 사진관 ===");
                                all.forEach(s -> System.out.println("사진관 번호: " + s.getStudioId()
                                        + " | 이름: " + s.getStudioName()
                                        + " | 위치: " + s.getLocation()
                                        + " | 전화번호: " + s.getPhone()));
                                break;

                            case "2": // 이름으로 사진관 검색
                                System.out.print("사진관 이름: ");
                                String kw = scanner.nextLine().trim();
                                List<Studio> byName = studioDao.findByName(kw);
                                if (byName.isEmpty()) {
                                    System.out.println("해당 사진관이 없습니다.");
                                } else {
                                    byName.forEach(s -> System.out.println("사진관 번호: " + s.getStudioId()
                                            + " | 이름: " + s.getStudioName()
                                            + " | 위치: " + s.getLocation()));
                                }
                                break;

                            case "3": // 사진관 상세 조회
                                System.out.print("사진관 ID: ");
                                int studioId = Integer.parseInt(scanner.nextLine().trim());

                                Optional<Studio> sOpt = studioDao.findById(studioId);

                                if (sOpt.isPresent()) {
                                    Studio s = sOpt.get();
                                    System.out.println("\n[사진관 상세 정보]");
                                    System.out.println("사진관 번호: " + s.getStudioId());
                                    System.out.println("사진관 이름: " + s.getStudioName());
                                    System.out.println("위치: " + s.getLocation());
                                    System.out.println("전화번호: " + s.getPhone());
                                } else {
                                    System.out.println("해당 사진관이 없습니다.");
                                }
                                break;
                            // 지역별 사진관 검색
                            case "4":
                                System.out.print("검색할 지역을 입력하세요 (예: 강남구): ");
                                String location = scanner.nextLine().trim();

                                // 지역으로 사진관 검색
                                List<Studio> locationResult = studioDao.findByLocation(location);

                                if (locationResult.isEmpty()) {
                                    System.out.println("조건에 맞는 사진관이 없습니다.");
                                } else {
                                    locationResult.forEach(s -> System.out.println(
                                            "사진관 번호: " + s.getStudioId()
                                                    + " | 이름: " + s.getStudioName()
                                                    + " | 위치: " + s.getLocation()));
                                }
                                break;

                            // 5. 전체 사진작가 조회
                            case "5":
                                List<Photographer> photographers = photographerDao.findAll();
                                System.out.println("\n[전체 사진작가 목록]");
                                photographers.forEach(p -> System.out.println(
                                        "작가 번호: " + p.getPhotographerId()
                                                + " | 이름: " + p.getPhotographerName()
                                                + " | 소속 사진관: " + p.getStudioId()
                                                + " | 경력: " + p.getCareer()
                                                + " | 전문 분야: " + p.getSpecialty()));
                                break;

                            // 6. 사진관별 사진작가 조회
                            case "6":
                                System.out.print("사진작가를 확인할 사진관 ID를 입력하세요: ");

                                int pStudioId = Integer.parseInt(scanner.nextLine().trim());
                                List<Photographer> byStudio = photographerDao.findByStudio(pStudioId);

                                if (byStudio.isEmpty()) {
                                    System.out.println("해당 사진관에 등록된 사진작가가 없습니다.");
                                } else {
                                    System.out.println("\n[사진작가 목록]");
                                    byStudio.forEach(p -> System.out.println(
                                            "작가 번호: " + p.getPhotographerId()
                                                    + " | 이름: " + p.getPhotographerName()
                                                    + " | 경력: " + p.getCareer()
                                                    + " | 전문 분야: " + p.getSpecialty()));
                                }
                                break;

                            // 7. 전문 분야별 사진작가 검색
                            case "7":
                                System.out.print("찾고 싶은 촬영 스타일을 입력하세요 (예: 커플 사진): ");
                                String specialty = scanner.nextLine().trim();
                                List<Photographer> specialtyResult = photographerDao.findBySpecialty(specialty);

                                if (specialtyResult.isEmpty()) {
                                    System.out.println("조건에 맞는 사진작가가 없습니다.");
                                } else {
                                    specialtyResult.forEach(p -> System.out.println(
                                            "작가 번호: " + p.getPhotographerId()
                                                    + " | 이름: " + p.getPhotographerName()
                                                    + " | 전문 분야: " + p.getSpecialty()));
                                }
                                break;

                            // 8. 경력별 사진작가 검색
                            case "8":
                                System.out.print("원하는 경력을 입력하세요 (예: 5년): ");

                                String career = scanner.nextLine().trim();
                                List<Photographer> careerResult = photographerDao.findByCareer(career);

                                if (careerResult.isEmpty()) {
                                    System.out.println("조건에 맞는 사진작가가 없습니다.");
                                } else {
                                    careerResult.forEach(p -> System.out.println(
                                            "작가 번호: " + p.getPhotographerId()
                                                    + " | 이름: " + p.getPhotographerName()
                                                    + " | 경력: " + p.getCareer())

                                    );
                                }
                                break;

                            case "9": // all backgrounds
                                List<Background> bAll = backgroundDao.findAll();
                                bAll.forEach(b -> System.out.println(
                                        b.getBackgroundId() + " | " + b.getColor() + " | " + b.getDescription()));
                                break;

                            case "10": // backgrounds by studio
                                System.out.print("사진관 ID: ");
                                int bSid = Integer.parseInt(scanner.nextLine().trim());
                                List<Background> bBy = backgroundDao.findByStudio(bSid);
                                bBy.forEach(b -> System.out.println(b.getBackgroundId() + " | " + b.getColor()));
                                break;

                            case "11": // all moods
                                List<Mood> mAll = moodDao.findAll();
                                mAll.forEach(m -> System.out.println(m.getMoodId() + " | " + m.getMoodName()));
                                break;

                            case "12": // moods by studio
                                System.out.print("사진관 ID: ");
                                int mSid = Integer.parseInt(scanner.nextLine().trim());
                                List<Mood> mBy = moodDao.findByStudio(mSid);
                                mBy.forEach(m -> System.out.println(m.getMoodId() + " | " + m.getMoodName()));
                                break;

                            case "13": // create reservation (transactional)
                                System.out.print("예약할 사진관 ID: ");
                                int rStudioId = Integer.parseInt(scanner.nextLine().trim());
                                System.out.print("사진작가 ID: ");
                                int rPhotographerId = Integer.parseInt(scanner.nextLine().trim());
                                System.out.print("배경 ID: ");
                                int rBackgroundId = Integer.parseInt(scanner.nextLine().trim());
                                System.out.print("무드 ID: ");
                                int rMoodId = Integer.parseInt(scanner.nextLine().trim());
                                System.out.print("예약 날짜 (YYYY-MM-DD): ");
                                String dateStr = scanner.nextLine().trim();
                                System.out.print("예약 시간 (HH:MM:SS): ");
                                String timeStr = scanner.nextLine().trim();

                                LocalDate rDate;
                                try {
                                    rDate = LocalDate.parse(dateStr);
                                } catch (DateTimeParseException ex) {
                                    System.out.println("날짜 형식 오류.");
                                    break;
                                }

                                // transaction begin
                                try {
                                    conn.setAutoCommit(false);

                                    // validate member exists
                                    if (!memberDao.existsById(currentMemberId)) {
                                        System.out.println("회원 정보가 없습니다.");
                                        conn.rollback();
                                        conn.setAutoCommit(true);
                                        break;
                                    }

                                    // validate studio, photographer, background, mood existence and relationships
                                    if (!studioDao.existsById(rStudioId)) {
                                        System.out.println("사진관 없음.");
                                        conn.rollback();
                                        conn.setAutoCommit(true);
                                        break;
                                    }
                                    if (!photographerDao.existsById(rPhotographerId)) {
                                        System.out.println("사진작가 없음.");
                                        conn.rollback();
                                        conn.setAutoCommit(true);
                                        break;
                                    }
                                    // photographer belongs to studio
                                    if (!photographerDao.isInStudio(rPhotographerId, rStudioId)) {
                                        System.out.println("선택한 사진작가가 해당 사진관 소속이 아닙니다.");
                                        conn.rollback();
                                        conn.setAutoCommit(true);
                                        break;
                                    }
                                    if (!backgroundDao.isProvidedByStudio(rBackgroundId, rStudioId)) {
                                        System.out.println("해당 사진관에서 제공하지 않는 배경입니다.");
                                        conn.rollback();
                                        conn.setAutoCommit(true);
                                        break;
                                    }
                                    if (!moodDao.isProvidedByStudio(rMoodId, rStudioId)) {
                                        System.out.println("해당 사진관에서 제공하지 않는 무드입니다.");
                                        conn.rollback();
                                        conn.setAutoCommit(true);
                                        break;
                                    }

                                    // conflict check: photographer at same date (simple check by reservation_date)
                                    if (reservationDao.existsConflict(rPhotographerId, rDate, timeStr)) {
                                        System.out.println("해당 시간대에 사진작가 예약이 이미 존재합니다.");
                                        conn.rollback();
                                        conn.setAutoCommit(true);
                                        break;
                                    }

                                    // insert reservation (let DAO generate reservation id or return success)
                                    Reservation r = new Reservation(0, currentMemberId, rStudioId, rPhotographerId,
                                            rBackgroundId, rMoodId, rDate, timeStr, "예약완료");
                                    int createdId = reservationDao.insert(r); // should return generated id or -1
                                                                                    // on fail
                                    if (createdId > 0) {
                                        conn.commit();
                                        System.out.println("예약 생성 성공. 예약 ID: " + createdId);
                                    } else {
                                        conn.rollback();
                                        System.out.println("예약 생성 실패.");
                                    }
                                } catch (SQLException e) {
                                    try {
                                        conn.rollback();
                                    } catch (SQLException ex) {
                                        /* ignore */ }
                                    System.err.println("예약 중 오류: " + e.getMessage());
                                } finally {
                                    try {
                                        conn.setAutoCommit(true);
                                    } catch (SQLException ignored) {
                                    }
                                }
                                break;

                            case "14": // my reservations
                                List<Reservation> mine = reservationDao.findByMember(currentMemberId);
                                System.out.println("=== 내 예약 ===");
                                mine.forEach(r -> System.out.println(
                                        r.getReservationId() + " | " + r.getReservationDate() + " | " + r.getState()));
                                break;

                            case "15": // update state (member or admin)
                                System.out.print("수정할 예약 ID: ");
                                int upId = Integer.parseInt(scanner.nextLine().trim());
                                System.out.print("새 상태(예약완료/취소/촬영완료): ");
                                String newState = scanner.nextLine().trim();
                                if (!("예약완료".equals(newState) || "취소".equals(newState) || "촬영완료".equals(newState))) {
                                    System.out.println("올바른 상태가 아닙니다.");
                                    break;
                                }
                                if (reservationDao.updateState(upId, newState))
                                    System.out.println("상태 변경 완료.");
                                else
                                    System.out.println("상태 변경 실패.");
                                break;

                            case "16": // cancel reservation (set state to 취소)
                                System.out.print("취소할 예약 ID: ");
                                int cancelId = Integer.parseInt(scanner.nextLine().trim());
                                if (reservationDao.updateState(cancelId, "취소"))
                                    System.out.println("취소 완료.");
                                else
                                    System.out.println("취소 실패.");
                                break;

                            case "17": // view: studio search view
                                List<StudioSearchResult> sv = viewDao.getStudioSearchView(conn);
                                sv.forEach(r -> System.out.println(r.getStudioId() + " | " + r.getStudioName() + " | "
                                        + r.getPhotographerName() + " | " + r.getColor() + " | " + r.getMoodName()));
                                break;

                            case "18": // member reservation view
                                List<MemberReservation> mv = viewDao.getMemberReservationView(conn, currentMemberId);
                                mv.forEach(v -> System.out.println(v.getReservationId() + " | " + v.getStudioName()
                                        + " | " + v.getPhotographerName() + " | " + v.getReservationDate() + " | "
                                        + v.getState()));
                                break;

                            case "19": // logout
                                loggedIn = false;
                                currentMemberId = null;
                                System.out.println("로그아웃 되었습니다.");
                                break;

                            case "0":
                                running = false;
                                break;

                            default:
                                System.out.println("잘못된 선택입니다.");
                        }
                    } catch (NumberFormatException nfe) {
                        System.out.println("숫자 형식 오류: " + nfe.getMessage());
                    } catch (Exception se) {
                        System.err.println("DB 오류: " + se.getMessage());
                    }
                }
            }

            System.out.println("프로그램 종료.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}