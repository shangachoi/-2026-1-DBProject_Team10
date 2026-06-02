package dao;

import dto.Mood;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MoodDAO {
    private final Connection conn;

    public MoodDAO(Connection conn) {
        this.conn = conn;
    }

    public List findAll() {
        List list = new ArrayList<>();
        String sql = "SELECT mood_id, mood_name, m_description FROM Mood";
        try (PreparedStatement p = conn.prepareStatement(sql);
             ResultSet r = p.executeQuery()) {
            while (r.next()) {
                list.add(new Mood(
                        r.getInt("mood_id"),
                        r.getString("mood_name"),
                        0,
                        r.getString("m_description")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List findByStudio(int studioId) {
        List list = new ArrayList<>();
        String sql = "SELECT m.mood_id, m.mood_name, m.m_description " +
                "FROM Mood m JOIN Studio_Mood sm ON m.mood_id = sm.mood_id " +
                "WHERE sm.studio_id = ?";
        try (PreparedStatement p = conn.prepareStatement(sql)) {
            p.setInt(1, studioId);
            try (ResultSet r = p.executeQuery()) {
                while (r.next()) {
                    list.add(new Mood(
                            r.getInt("mood_id"),
                            r.getString("mood_name"),
                            studioId,
                            r.getString("m_description")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean isProvidedByStudio(int moodId, int studioId) {
        String sql = "SELECT 1 FROM Studio_Mood WHERE mood_id = ? AND studio_id = ?";
        try (PreparedStatement p = conn.prepareStatement(sql)) {
            p.setInt(1, moodId);
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