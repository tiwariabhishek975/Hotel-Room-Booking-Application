package com.hotel.dao;

import com.hotel.config.DBConnection;
import com.hotel.model.Room;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO {

    public boolean save(Room room) throws SQLException {
        String sql = "INSERT INTO rooms(room_number,room_type,price,capacity,status,image_path) VALUES(?,?,?,?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, room.getRoomNumber());
            ps.setString(2, room.getRoomType());
            ps.setBigDecimal(3, room.getPrice());
            ps.setInt(4, room.getCapacity());
            ps.setString(5, room.getStatus());
            ps.setString(6, room.getImagePath());
            return ps.executeUpdate() == 1;
        }
    }

    public boolean update(Room room) throws SQLException {
        String sql = "UPDATE rooms SET room_number=?,room_type=?,price=?,capacity=?,status=?,image_path=? WHERE id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, room.getRoomNumber());
            ps.setString(2, room.getRoomType());
            ps.setBigDecimal(3, room.getPrice());
            ps.setInt(4, room.getCapacity());
            ps.setString(5, room.getStatus());
            ps.setString(6, room.getImagePath());
            ps.setInt(7, room.getId());
            return ps.executeUpdate() == 1;
        }
    }

    public boolean delete(int id) throws SQLException {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("DELETE FROM rooms WHERE id=?")) {
            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        }
    }

    public Room findById(int id) throws SQLException {
        String sql = "SELECT * FROM rooms WHERE id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        }
        return null;
    }

    public List<Room> search(String keyword, String type, int page, int pageSize) throws SQLException {
        int offset = (page - 1) * pageSize;
        StringBuilder sql = new StringBuilder(
                "SELECT * FROM rooms WHERE status='AVAILABLE' AND " +
                "(room_number LIKE ? OR room_type LIKE ?) ");
        List<Object> params = new ArrayList<>();
        String key = "%" + (keyword == null ? "" : keyword) + "%";
        params.add(key); params.add(key);

        if (type != null && !type.isBlank()) {
            sql.append("AND room_type=? ");
            params.add(type);
        }
        sql.append("ORDER BY id DESC LIMIT ? OFFSET ?");
        params.add(pageSize); params.add(offset);

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                Object value = params.get(i);
                if (value instanceof Integer n) ps.setInt(i + 1, n);
                else ps.setString(i + 1, String.valueOf(value));
            }
            List<Room> rooms = new ArrayList<>();
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) rooms.add(map(rs));
            }
            return rooms;
        }
    }

    public int countAvailable(String keyword, String type) throws SQLException {
        StringBuilder sql = new StringBuilder(
                "SELECT COUNT(*) FROM rooms WHERE status='AVAILABLE' AND " +
                "(room_number LIKE ? OR room_type LIKE ?) ");
        List<Object> params = new ArrayList<>();
        String key = "%" + (keyword == null ? "" : keyword) + "%";
        params.add(key); params.add(key);
        if (type != null && !type.isBlank()) {
            sql.append("AND room_type=? ");
            params.add(type);
        }
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {
            for (int i=0;i<params.size();i++) ps.setString(i+1, String.valueOf(params.get(i)));
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1);
            }
        }
    }

    private Room map(ResultSet rs) throws SQLException {
        return new Room(
                rs.getInt("id"), rs.getString("room_number"),
                rs.getString("room_type"), rs.getBigDecimal("price"),
                rs.getInt("capacity"), rs.getString("status"),
                rs.getString("image_path")
        );
    }
}
