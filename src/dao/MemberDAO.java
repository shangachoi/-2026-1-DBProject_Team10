package dao;

import dto.Member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class MemberDAO {
    private final Connection conn;

    public MemberDAO(Connection conn) {
        this.conn = conn;
    }

    // 회원가입
    public boolean register(Member member) {
        String sql = "INSERT INTO Member (member_id, password, name, phone) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, member.getMemberId());
            pstmt.setString(2, member.getPassword());
            pstmt.setString(3, member.getName());
            pstmt.setString(4, member.getPhone());
            return pstmt.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 로그인 (memberId + password)
    public boolean login(String memberId, String password) {
        String sql = "SELECT 1 FROM Member WHERE member_id = ? AND password = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, memberId);
            pstmt.setString(2, password);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // memberId로 회원 정보 조회
    public Optional<Member> findById(String memberId) {
        String sql = "SELECT member_id, password, name, phone FROM Member WHERE member_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, memberId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Member m = new Member(
                            rs.getString("member_id"),
                            rs.getString("password"),
                            rs.getString("name"),
                            rs.getString("phone")
                    );
                    return Optional.of(m);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    // 로그아웃 (DB 처리 없음)
    public void logout() {
        System.out.println("로그아웃 처리(서버 세션 없음) — 클라이언트 측에서 토큰/상태 제거 필요");
    }

    public boolean insert(Member member) {
        String sql = "INSERT INTO Member (member_id, name, password, phone) VALUES (?, ?, ?, ?)";
        try (PreparedStatement p = conn.prepareStatement(sql)) {
            p.setString(1, member.getMemberId());
            p.setString(2, member.getName());
            p.setString(3, member.getPassword());
            p.setString(4, member.getPhone());
            return p.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 회원탈퇴 by memberId
    public boolean deleteUser(String memberId) {
        String sql = "DELETE FROM Member WHERE member_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, memberId);
            return pstmt.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 존재 여부 확인
    public boolean existsById(String memberId) {
        String sql = "SELECT 1 FROM Member WHERE member_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, memberId);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }
}