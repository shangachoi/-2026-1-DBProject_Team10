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

    // 스튜디오 등록
    public boolean register(Studio studio) {

        String sql =
                "INSERT INTO Studio (studio_id, studio_name, location, studio_phone) "
              + "VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, studio.getStudioId());
            pstmt.setString(2, studio.getStudioName());
            pstmt.setString(3, studio.getLocation());
            pstmt.setString(4, studio.getPhone());

            return pstmt.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public List findByName(String name) {
        List list = new ArrayList<>();
        String sql = "SELECT studio_id, name, address FROM Studio WHERE name LIKE ?"; // adjust columns
        try (PreparedStatement p = conn.prepareStatement(sql)) {
            p.setString(1, "%" + name + "%");
            try (ResultSet r = p.executeQuery()) {
                while (r.next()) {
                    list.add(new Studio(r.getInt("studio_id"), r.getString("name"), r.getString("address")));
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    // 전체 스튜디오 조회
    public List<Studio> findAll() {

        List<Studio> studios = new ArrayList<>();

        String sql =
                "SELECT studio_id, studio_name, location, studio_phone "
              + "FROM Studio";

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {

                Studio studio = new Studio(
                        rs.getInt("studio_id"),
                        rs.getString("studio_name"),
                        rs.getString("location"),
                        rs.getString("studio_phone")
                );

                studios.add(studio);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return studios;
    }

    // ID로 스튜디오 조회
    public Optional<Studio> findById(int studioId) {

        String sql =
                "SELECT studio_id, studio_name, location, studio_phone "
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
                            rs.getString("studio_phone")
                    );

                    return Optional.of(studio);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    // 스튜디오 정보 수정
    public boolean update(Studio studio) {

        String sql =
                "UPDATE Studio "
              + "SET studio_name = ?, "
              + "location = ?, "
              + "studio_phone = ? "
              + "WHERE studio_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, studio.getStudioName());
            pstmt.setString(2, studio.getLocation());
            pstmt.setString(3, studio.getPhone());
            pstmt.setInt(4, studio.getStudioId());

            return pstmt.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 스튜디오 삭제
    public boolean delete(int studioId) {

        String sql = "DELETE FROM Studio WHERE studio_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, studioId);

            return pstmt.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 존재 여부 확인
    public boolean existsById(int studioId) {

        String sql =
                "SELECT 1 FROM Studio WHERE studio_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, studioId);

            try (ResultSet rs = pstmt.executeQuery()) {

                return rs.next();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}