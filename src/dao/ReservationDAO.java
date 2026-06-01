package dao;

import dto.Reservation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReservationDAO {

    private final Connection conn;

    public ReservationDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean register(Reservation reservation) {
        String sql =
                "INSERT INTO Reservation " +
                "(reservation_id, member_id, studio_id, photographer_id, background_id, mood_id, reservation_date, reservation_time, state) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, reservation.getReservationId());
            pstmt.setString(2, reservation.getMemberId());
            pstmt.setInt(3, reservation.getStudioId());
            pstmt.setInt(4, reservation.getPhotographerId());
            pstmt.setInt(5, reservation.getBackgroundId());
            pstmt.setInt(6, reservation.getMoodId());
            pstmt.setDate(7, reservation.getReservationDate());
            pstmt.setTime(8, reservation.getReservationTime());
            pstmt.setString(9, reservation.getState());

            return pstmt.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Reservation> findAll() {
        List<Reservation> reservations = new ArrayList<>();

        String sql =
                "SELECT reservation_id, member_id, studio_id, photographer_id, background_id, mood_id, " +
                "reservation_date, reservation_time, state FROM Reservation";

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Reservation reservation = new Reservation(
                        rs.getInt("reservation_id"),
                        rs.getString("member_id"),
                        rs.getInt("studio_id"),
                        rs.getInt("photographer_id"),
                        rs.getInt("background_id"),
                        rs.getInt("mood_id"),
                        rs.getDate("reservation_date"),
                        rs.getTime("reservation_time"),
                        rs.getString("state")
                );

                reservations.add(reservation);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservations;
    }

    public Optional<Reservation> findById(int reservationId) {
        String sql =
                "SELECT reservation_id, member_id, studio_id, photographer_id, background_id, mood_id, " +
                "reservation_date, reservation_time, state FROM Reservation WHERE reservation_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, reservationId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Reservation reservation = new Reservation(
                            rs.getInt("reservation_id"),
                            rs.getString("member_id"),
                            rs.getInt("studio_id"),
                            rs.getInt("photographer_id"),
                            rs.getInt("background_id"),
                            rs.getInt("mood_id"),
                            rs.getDate("reservation_date"),
                            rs.getTime("reservation_time"),
                            rs.getString("state")
                    );

                    return Optional.of(reservation);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public boolean update(Reservation reservation) {
        String sql =
                "UPDATE Reservation SET " +
                "member_id = ?, studio_id = ?, photographer_id = ?, background_id = ?, mood_id = ?, " +
                "reservation_date = ?, reservation_time = ?, state = ? " +
                "WHERE reservation_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, reservation.getMemberId());
            pstmt.setInt(2, reservation.getStudioId());
            pstmt.setInt(3, reservation.getPhotographerId());
            pstmt.setInt(4, reservation.getBackgroundId());
            pstmt.setInt(5, reservation.getMoodId());
            pstmt.setDate(6, reservation.getReservationDate());
            pstmt.setTime(7, reservation.getReservationTime());
            pstmt.setString(8, reservation.getState());
            pstmt.setInt(9, reservation.getReservationId());

            return pstmt.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int reservationId) {
        String sql = "DELETE FROM Reservation WHERE reservation_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, reservationId);
            return pstmt.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean existsById(int reservationId) {
        String sql = "SELECT 1 FROM Reservation WHERE reservation_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, reservationId);

            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
