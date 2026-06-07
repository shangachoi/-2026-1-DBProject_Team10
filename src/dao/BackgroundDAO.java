package dao;

import dto.Background;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BackgroundDAO {
    private final Connection conn;

    public BackgroundDAO(Connection conn) {
        this.conn = conn;
    }

    // 배경 전체 조회
    public List findAll() {
        List list = new ArrayList<>();
        String sql = "SELECT background_id, color, b_description FROM Background";
        try (PreparedStatement p = conn.prepareStatement(sql);
                ResultSet r = p.executeQuery()) {
            while (r.next()) {
                // use 0 for studioId since Background is not directly bound to one studio
                list.add(new Background(
                        r.getInt("background_id"),
                        r.getString("color"),
                        0,
                        r.getString("b_description")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // 사진관별 배경 조회
    public List findByStudio(int studioId) {
        List list = new ArrayList<>();
        String sql = "SELECT background_id, color, b_description " +
                "FROM Background " +
                "WHERE background_id IN (" +
                "SELECT background_id " +
                "FROM Studio_Background " +
                "WHERE studio_id = ?)";
        ;
        try (PreparedStatement p = conn.prepareStatement(sql)) {
            p.setInt(1, studioId);
            try (ResultSet r = p.executeQuery()) {
                while (r.next()) {
                    list.add(new Background(
                            r.getInt("background_id"),
                            r.getString("color"),
                            studioId,
                            r.getString("b_description")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // 해당 사진관의 배경 제공 여부 확인
    public boolean isProvidedByStudio(int backgroundId, int studioId) {
        String sql = "SELECT 1 FROM Studio_Background WHERE background_id = ? AND studio_id = ?";
        try (PreparedStatement p = conn.prepareStatement(sql)) {
            p.setInt(1, backgroundId);
            p.setInt(2, studioId);
            try (ResultSet r = p.executeQuery()) {
                return r.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}