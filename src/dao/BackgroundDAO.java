package dao;

import dto.Background;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BackgroundDAO {

    private final Connection conn;

    public BackgroundDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean register(Background background) {
        String sql = "INSERT INTO Background (background_id, color, b_description) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, background.getBackgroundId());
            pstmt.setString(2, background.getColor());
            pstmt.setString(3, background.getDescription());

            return pstmt.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Background> findAll() {
        List<Background> backgrounds = new ArrayList<>();

        String sql = "SELECT background_id, color, b_description FROM Background";

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Background background = new Background(
                        rs.getInt("background_id"),
                        rs.getString("color"),
                        rs.getString("b_description")
                );
                backgrounds.add(background);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return backgrounds;
    }

    public Optional<Background> findById(int backgroundId) {
        String sql = "SELECT background_id, color, b_description FROM Background WHERE background_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, backgroundId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Background background = new Background(
                            rs.getInt("background_id"),
                            rs.getString("color"),
                            rs.getString("b_description")
                    );
                    return Optional.of(background);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public boolean update(Background background) {
        String sql = "UPDATE Background SET color = ?, b_description = ? WHERE background_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, background.getColor());
            pstmt.setString(2, background.getDescription());
            pstmt.setInt(3, background.getBackgroundId());

            return pstmt.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int backgroundId) {
        String sql = "DELETE FROM Background WHERE background_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, backgroundId);
            return pstmt.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean existsById(int backgroundId) {
        String sql = "SELECT 1 FROM Background WHERE background_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, backgroundId);

            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
