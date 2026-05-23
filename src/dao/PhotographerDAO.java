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

    // 사진작가 등록
    public boolean register(Photographer photographer) {

        String sql =
                "INSERT INTO Photographer " +
                "(photographer_id, photographer_name, studio_id, career, specialty) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, photographer.getPhotographerId());
            pstmt.setString(2, photographer.getPhotographerName());
            pstmt.setInt(3, photographer.getStudioId());
            pstmt.setString(4, photographer.getCareer());
            pstmt.setString(5, photographer.getSpecialty());

            return pstmt.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
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

    // 사진작가 정보 수정
    public boolean update(Photographer photographer) {

        String sql =
                "UPDATE Photographer " +
                "SET photographer_name = ?, " +
                "studio_id = ?, " +
                "career = ?, " +
                "specialty = ? " +
                "WHERE photographer_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, photographer.getPhotographerName());
            pstmt.setInt(2, photographer.getStudioId());
            pstmt.setString(3, photographer.getCareer());
            pstmt.setString(4, photographer.getSpecialty());
            pstmt.setInt(5, photographer.getPhotographerId());

            return pstmt.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 사진작가 삭제
    public boolean delete(int photographerId) {

        String sql =
                "DELETE FROM Photographer WHERE photographer_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, photographerId);

            return pstmt.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 존재 여부 확인
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
}