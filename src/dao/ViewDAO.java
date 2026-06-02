package dao;
import dto.StudioSearchResult;
import dto.MemberReservation;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class ViewDAO {

    private final Connection conn;
    public ViewDAO(Connection conn) {
        this.conn = conn;
    }
    
    public List<StudioSearchResult> getStudioSearchView(Connection conn) {
        List<StudioSearchResult> list = new ArrayList<>();
        String sql = "SELECT studio_id, studio_name, location, studio_phone, photographer_name, color, mood_name FROM Studio_Search_View";
        try (PreparedStatement p = conn.prepareStatement(sql);
             ResultSet r = p.executeQuery()) {
            while (r.next()) {
                list.add(new StudioSearchResult(
                        r.getInt("studio_id"),
                        r.getString("studio_name"),
                        r.getString("location"),
                        r.getString("studio_phone"),
                        r.getString("photographer_name"),
                        r.getString("color"),
                        r.getString("mood_name")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<MemberReservation> getMemberReservationView(Connection conn, String memberId) {
        List<MemberReservation> list = new ArrayList<>();
        String sql = "SELECT member_id, name, reservation_id, studio_name, color, mood_name, photographer_name, reservation_date, reservation_time, state FROM Member_Reservation_View WHERE member_id = ?";
        try (PreparedStatement p = conn.prepareStatement(sql)) {
            p.setString(1, memberId);
            try (ResultSet r = p.executeQuery()) {
                while (r.next()) {
                    Date d = r.getDate("reservation_date");
                    LocalDate date = (d == null) ? null : d.toLocalDate();
                    list.add(new MemberReservation(
                            r.getString("member_id"),
                            r.getString("name"),
                            r.getInt("reservation_id"),
                            r.getString("studio_name"),
                            r.getString("color"),
                            r.getString("mood_name"),
                            r.getString("photographer_name"),
                            date,
                            r.getString("reservation_time"),
                            r.getString("state")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

}