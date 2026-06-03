package dao;

import dto.Studio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudioDAO {

    private final Connection conn;

    public StudioDAO(Connection conn) {
        this.conn = conn;
    }

    // 전체 사진관 조회
    public List<Studio> findAll() {

        List<Studio> studios = new ArrayList<>();

        String sql = "SELECT studio_id, studio_name, location, studio_phone "
                + "FROM Studio";

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {

                Studio studio = new Studio(
                        rs.getInt("studio_id"),
                        rs.getString("studio_name"),
                        rs.getString("location"),
                        rs.getString("studio_phone"));

                studios.add(studio);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return studios;
    }

    // ID로 사진관 조회
    public Optional<Studio> findById(int studioId) {

        String sql = "SELECT studio_id, studio_name, location, studio_phone "
                + "FROM Studio "
                + "WHERE studio_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, studioId);

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {

                    Studio studio = new Studio(
                            rs.getInt("studio_id"),
                            rs.getString("studio_name"),
                            rs.getString("location"),
                            rs.getString("studio_phone"));

                    return Optional.of(studio);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    // 사진관 존재 여부 확인
    public boolean existsById(int studioId) {

        String sql = "SELECT 1 FROM Studio WHERE studio_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, studioId);

            try (ResultSet rs = pstmt.executeQuery()) {

                return rs.next();
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return false;
    }

    // 이름으로 사진관 검색
    public List<Studio> findByName(String keyword) {

        List<Studio> studios = new ArrayList<>();

        String sql = "SELECT studio_id, studio_name, " +
                "location, studio_phone " +
                "FROM Studio " +
                "WHERE studio_name LIKE ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + keyword + "%");

            try (ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {

                    Studio studio = new Studio(
                            rs.getInt("studio_id"),
                            rs.getString("studio_name"),
                            rs.getString("location"),
                            rs.getString("studio_phone"));

                    studios.add(studio);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return studios;
    }

    // 지역으로 사진관 검색
    public List<Studio> findByLocation(String location) {

        List<Studio> studios = new ArrayList<>();

        String sql = "SELECT studio_id, studio_name, " +
                "location, studio_phone " +
                "FROM Studio " +
                "WHERE location LIKE ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + location + "%");

            try (ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {

                    Studio studio = new Studio(
                            rs.getInt("studio_id"),
                            rs.getString("studio_name"),
                            rs.getString("location"),
                            rs.getString("studio_phone"));

                    studios.add(studio);
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return studios;
    }

}
