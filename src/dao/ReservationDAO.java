package dao;

import dto.Reservation;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReservationDAO {

    private final Connection conn;

    public ReservationDAO(Connection conn) {
        this.conn = conn;
    }

    // original register (boolean)
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
            pstmt.setDate(7, Date.valueOf(reservation.getReservationDate()));
            pstmt.setString(8, reservation.getReservationTime());
            pstmt.setString(9, reservation.getState());

            return pstmt.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // New: insert returning generated id (used by Main)
    public int insert(Reservation reservation) {
        String sql =
                "INSERT INTO Reservation " +
                        "(member_id, studio_id, photographer_id, background_id, mood_id, reservation_date, reservation_time, state) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, reservation.getMemberId());
            pstmt.setInt(2, reservation.getStudioId());
            pstmt.setInt(3, reservation.getPhotographerId());
            pstmt.setInt(4, reservation.getBackgroundId());
            pstmt.setInt(5, reservation.getMoodId());
            pstmt.setDate(6, Date.valueOf(reservation.getReservationDate()));
            pstmt.setString(7, reservation.getReservationTime());
            pstmt.setString(8, reservation.getState());

            int affected = pstmt.executeUpdate();
            if (affected == 0) return -1;

            try (ResultSet keys = pstmt.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                } else {
                    return -1;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
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
                LocalDate date = rs.getDate("reservation_date").toLocalDate();
                String time = rs.getString("reservation_time");

                Reservation reservation = new Reservation(
                        rs.getInt("reservation_id"),
                        rs.getString("member_id"),
                        rs.getInt("studio_id"),
                        rs.getInt("photographer_id"),
                        rs.getInt("background_id"),
                        rs.getInt("mood_id"),
                        date,
                        time,
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
                    LocalDate date = rs.getDate("reservation_date").toLocalDate();
                    String time = rs.getString("reservation_time");

                    Reservation reservation = new Reservation(
                            rs.getInt("reservation_id"),
                            rs.getString("member_id"),
                            rs.getInt("studio_id"),
                            rs.getInt("photographer_id"),
                            rs.getInt("background_id"),
                            rs.getInt("mood_id"),
                            date,
                            time,
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
            pstmt.setDate(6, Date.valueOf(reservation.getReservationDate()));
            pstmt.setString(7, reservation.getReservationTime());
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

    // New: find reservations by member id
    public List<Reservation> findByMember(String memberId) {
        List<Reservation> reservations = new ArrayList<>();
        String sql =
                "SELECT reservation_id, member_id, studio_id, photographer_id, background_id, mood_id, " +
                        "reservation_date, reservation_time, state FROM Reservation WHERE member_id = ? ORDER BY reservation_date DESC";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, memberId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    LocalDate date = rs.getDate("reservation_date").toLocalDate();
                    String time = rs.getString("reservation_time");

                    Reservation reservation = new Reservation(
                            rs.getInt("reservation_id"),
                            rs.getString("member_id"),
                            rs.getInt("studio_id"),
                            rs.getInt("photographer_id"),
                            rs.getInt("background_id"),
                            rs.getInt("mood_id"),
                            date,
                            time,
                            rs.getString("state")
                    );

                    reservations.add(reservation);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservations;
    }

    // New: update only state
    public boolean updateState(int reservationId, String newState) {
        String sql = "UPDATE Reservation SET state = ? WHERE reservation_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newState);
            pstmt.setInt(2, reservationId);
            return pstmt.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // New: check conflict for a photographer on a given date (exclude canceled)
    public boolean existsConflict(int photographerId, LocalDate date) {
        String sql = "SELECT 1 FROM Reservation WHERE photographer_id = ? AND reservation_date = ? AND state <> '취소'";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, photographerId);
            pstmt.setDate(2, Date.valueOf(date));

            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}