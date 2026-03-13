package com.sgs.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.sgs.model.Suggestions;
import com.sgs.util.DBConnection;

public class SuggestionDAO {

    // CREATE - user submits a suggestion
    public void addSuggestion(Suggestions s) throws SQLException {
        String sql = "INSERT INTO suggestions (user_id, title, message) VALUES (?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, s.getUserId());
            ps.setString(2, s.getTitle());
            ps.setString(3, s.getMessage());
            ps.executeUpdate();
        }
    }

    // READ - user sees their own suggestions + admin replies
    public List<Suggestions> getSuggestionsByUser(int userId) throws SQLException {
        List<Suggestions> list = new ArrayList<>();
        String sql = "SELECT * FROM suggestions WHERE user_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Suggestions s = new Suggestions();
                    s.setId(rs.getInt("id"));
                    s.setUserId(rs.getInt("user_id"));
                    s.setTitle(rs.getString("title"));
                    s.setMessage(rs.getString("message"));
                    s.setAdminReply(rs.getString("admin_reply"));
                    s.setSubmittedAt(rs.getTimestamp("submitted_at"));
                    list.add(s);
                }
            }
        }
        return list;
    }

    // READ - admin sees all suggestions from all users
    public List<Suggestions> getAllSuggestions() throws SQLException {
        List<Suggestions> list = new ArrayList<>();
        String sql = "SELECT * FROM suggestions";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Suggestions s = new Suggestions();
                    s.setId(rs.getInt("id"));
                    s.setUserId(rs.getInt("user_id"));
                    s.setTitle(rs.getString("title"));
                    s.setMessage(rs.getString("message"));
                    s.setAdminReply(rs.getString("admin_reply"));
                    s.setSubmittedAt(rs.getTimestamp("submitted_at"));
                    list.add(s);
                }
            }
        }
        return list;
    }

    // UPDATE - admin types a reply to a suggestion
    public void addReply(int id, String replyText) throws SQLException {
        String sql = "UPDATE suggestions SET admin_reply = ? WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, replyText);
            ps.setInt(2, id);
            ps.executeUpdate();
        }
    }

    // DELETE - admin deletes a suggestion
    public void deleteSuggestion(int id) throws SQLException {
        String sql = "DELETE FROM suggestions WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}