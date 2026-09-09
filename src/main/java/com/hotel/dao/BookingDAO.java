package com.hotel.dao;

import com.hotel.config.DBConnection;
import com.hotel.model.Booking;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

    public boolean isRoomAvailable(int roomId, Date checkIn, Date checkOut) throws SQLException {
        String sql = """
            SELECT COUNT(*) FROM bookings
            WHERE room_id=? AND status IN ('CONFIRMED','CHECKED_IN')
              AND check_in_date < ? AND check_out_date > ?
            """;
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, roomId);
            ps.setDate(2, checkOut);
            ps.setDate(3, checkIn);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1) == 0;
            }
        }
    }

    public boolean create(Booking booking) throws SQLException {
        String sql = """
            INSERT INTO bookings(user_id,room_id,check_in_date,check_out_date,status,total_amount)
            VALUES(?,?,?,?,?,?)
            """;
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, booking.getUserId());
            ps.setInt(2, booking.getRoomId());
            ps.setDate(3, Date.valueOf(booking.getCheckInDate()));
            ps.setDate(4, Date.valueOf(booking.getCheckOutDate()));
            ps.setString(5, "CONFIRMED");
            ps.setBigDecimal(6, booking.getTotalAmount());
            return ps.executeUpdate() == 1;
        }
    }

    public List<Booking> findByUser(int userId) throws SQLException {
        String sql = "SELECT * FROM bookings WHERE user_id=? ORDER BY id DESC";
        List<Booking> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(map(rs));
            }
        }
        return list;
    }

    public boolean updateStatus(int id, String status) throws SQLException {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("UPDATE bookings SET status=? WHERE id=?")) {
            ps.setString(1, status);
            ps.setInt(2, id);
            return ps.executeUpdate() == 1;
        }
    }

    public List<Booking> findAll() throws SQLException {
        List<Booking> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM bookings ORDER BY id DESC");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    private Booking map(ResultSet rs) throws SQLException {
        Booking b = new Booking();
        b.setId(rs.getInt("id"));
        b.setUserId(rs.getInt("user_id"));
        b.setRoomId(rs.getInt("room_id"));
        b.setCheckInDate(rs.getDate("check_in_date").toLocalDate());
        b.setCheckOutDate(rs.getDate("check_out_date").toLocalDate());
        b.setStatus(rs.getString("status"));
        b.setTotalAmount(rs.getBigDecimal("total_amount"));
        return b;
    }
}
