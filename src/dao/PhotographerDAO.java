package dao;

import dto.Photographer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PhotographerDAO {
    private final Connection conn;

    public PhotographerDAO(Connection conn) {
        this.conn = conn;
    }

    public List<Photographer> findAll() {
        List<Photographer> list = new ArrayList<>();
        String sql = "SELECT photographer_id, photographer_name, studio_id, career, specialty FROM Photographer";
        try (PreparedStatement p = conn.prepareStatement(sql);
             ResultSet r = p.executeQuery()) {
            while (r.next()) {
                int id = r.getInt("photographer_id");
                String name = r.getString("photographer_name");
                int studioId = r.getInt("studio_id");
                String career = null;
                String desc = null;
                try {
                    career = r.getString("career");
                } catch (SQLException ignored) {
                }
                try {
                    desc = r.getString("specialty");
                } catch (SQLException ignored) {
                }
                list.add(new Photographer(id, name, studioId, career, desc));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Photographer> findByStudio(int studioId) {
        List<Photographer> list = new ArrayList<>();
        String sql = "SELECT photographer_id, photographer_name, career, specialty FROM Photographer WHERE studio_id = ?";
        try (PreparedStatement p = conn.prepareStatement(sql)) {
            p.setInt(1, studioId);
            try (ResultSet r = p.executeQuery()) {
                while (r.next()) {
                    list.add(new Photographer(
                            r.getInt("photographer_id"),
                            r.getString("photographer_name"),
                            studioId,
                            r.getString("career"),
                            r.getString("specialty")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean isInStudio(int photographerId, int studioId) {
        String sql = "SELECT 1 FROM Photographer WHERE photographer_id = ? AND studio_id = ?";
        try (PreparedStatement p = conn.prepareStatement(sql)) {
            p.setInt(1, photographerId);
            p.setInt(2, studioId);
            try (ResultSet r = p.executeQuery()) {
                return r.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean existsById(int photographerId) {
        String sql = "SELECT 1 FROM Photographer WHERE photographer_id = ?";
        try (PreparedStatement p = conn.prepareStatement(sql)) {
            p.setInt(1, photographerId);
            try (ResultSet r = p.executeQuery()) {
                return r.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}