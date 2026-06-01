package dao;

import dto.Mood;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MoodDAO {

    private final Connection conn;

    public MoodDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean register(Mood mood) {
        String sql = "INSERT INTO Mood (mood_id, mood_name, m_description) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, mood.getMoodId());
            pstmt.setString(2, mood.getMoodName());
            pstmt.setString(3, mood.getDescription());

            return pstmt.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Mood> findAll() {
        List<Mood> moods = new ArrayList<>();

        String sql = "SELECT mood_id, mood_name, m_description FROM Mood";

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Mood mood = new Mood(
                        rs.getInt("mood_id"),
                        rs.getString("mood_name"),
                        rs.getString("m_description")
                );
                moods.add(mood);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return moods;
    }

    public Optional<Mood> findById(int moodId) {
        String sql = "SELECT mood_id, mood_name, m_description FROM Mood WHERE mood_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, moodId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Mood mood = new Mood(
                            rs.getInt("mood_id"),
                            rs.getString("mood_name"),
                            rs.getString("m_description")
                    );
                    return Optional.of(mood);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public boolean update(Mood mood) {
        String sql = "UPDATE Mood SET mood_name = ?, m_description = ? WHERE mood_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, mood.getMoodName());
            pstmt.setString(2, mood.getDescription());
            pstmt.setInt(3, mood.getMoodId());

            return pstmt.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int moodId) {
        String sql = "DELETE FROM Mood WHERE mood_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, moodId);
            return pstmt.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean existsById(int moodId) {
        String sql = "SELECT 1 FROM Mood WHERE mood_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, moodId);

            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
