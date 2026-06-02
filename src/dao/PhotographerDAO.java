package dao;

import dto.Photographer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PhotographerDAO {

    private final Connection conn;

    public PhotographerDAO(Connection conn) {
        this.conn = conn;
    }

    // 전체 사진작가 조회
    public List<Photographer> findAll() {

        List<Photographer> photographers = new ArrayList<>();

        String sql =
                "SELECT photographer_id, photographer_name, studio_id, career, specialty " +
                "FROM Photographer";

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {

                Photographer photographer = new Photographer(
                        rs.getInt("photographer_id"),
                        rs.getString("photographer_name"),
                        rs.getInt("studio_id"),
                        rs.getString("career"),
                        rs.getString("specialty")
                );

                photographers.add(photographer);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return photographers;
    }

    // ID로 사진작가 조회
    public Optional<Photographer> findById(int photographerId) {

        String sql =
                "SELECT photographer_id, photographer_name, studio_id, career, specialty " +
                "FROM Photographer " +
                "WHERE photographer_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, photographerId);

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {

                    Photographer photographer = new Photographer(
                            rs.getInt("photographer_id"),
                            rs.getString("photographer_name"),
                            rs.getInt("studio_id"),
                            rs.getString("career"),
                            rs.getString("specialty")
                    );

                    return Optional.of(photographer);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    // 사진관별 사진작가 조회
    public List<Photographer> findByStudio(int studioId) {

        List<Photographer> photographers = new ArrayList<>();

        String sql =
                "SELECT photographer_id, photographer_name, studio_id, career, specialty " +
                "FROM Photographer " +
                "WHERE studio_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, studioId);

            try (ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    Photographer photographer = new Photographer(
                            rs.getInt("photographer_id"),
                            rs.getString("photographer_name"),
                            rs.getInt("studio_id"),
                            rs.getString("career"),
                            rs.getString("specialty")
                    );

                    photographers.add(photographer);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return photographers;
    }

    // 전문 분야별 사진작가 검색
    public List<Photographer> findBySpecialty(String specialty) {

        List<Photographer> photographers = new ArrayList<>();

        String sql =
                "SELECT photographer_id, photographer_name, studio_id, career, specialty " +
                "FROM Photographer " +
                "WHERE specialty LIKE ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + specialty + "%");

            try (ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    Photographer photographer = new Photographer(
                            rs.getInt("photographer_id"),
                            rs.getString("photographer_name"),
                            rs.getInt("studio_id"),
                            rs.getString("career"),
                            rs.getString("specialty")
                    );

                    photographers.add(photographer);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return photographers;
    }

    // 경력별 사진작가 검색
    public List<Photographer> findByCareer(String career) {

        List<Photographer> photographers = new ArrayList<>();

        String sql =
                "SELECT photographer_id, photographer_name, studio_id, career, specialty " +
                "FROM Photographer " +
                "WHERE career LIKE ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + career + "%");

            try (ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    Photographer photographer = new Photographer(
                            rs.getInt("photographer_id"),
                            rs.getString("photographer_name"),
                            rs.getInt("studio_id"),
                            rs.getString("career"),
                            rs.getString("specialty")
                    );

                    photographers.add(photographer);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return photographers;
    }


    // 사진작가 존재 여부 확인
    public boolean existsById(int photographerId) {

        String sql =
                "SELECT 1 FROM Photographer WHERE photographer_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, photographerId);

            try (ResultSet rs = pstmt.executeQuery()) {

                return rs.next();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // 사진작가가 해당 사진관 소속인지 확인
    public boolean isInStudio(int photographerId, int studioId) {

        String sql =
                "SELECT 1 " +
                "FROM Photographer " +
                "WHERE photographer_id = ? AND studio_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, photographerId);
            pstmt.setInt(2, studioId);

            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}