package com.ec.dao;

import com.ec.model.User;
import com.ec.util.DBConnection;
import java.sql.*;

public class UserDAO {

    // REGISTER - INSERT new user
    public boolean register(User user) {
        String query = "INSERT INTO users (username, password) VALUES (?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPass());
            
            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // LOGIN - SELECT user by username and password
    public User login(String username, String password) {
        String query = "SELECT * FROM economictracker.users WHERE username = ? AND password = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, username);
            ps.setString(2, password);
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                User user = new User(username, password);
                user.setId(rs.getInt("id"));
                return user;  // login success
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;  // login failed
    }

    // CHECK - if username already exists (useful during register)
    public boolean usernameExists(String username) {
        String query = "SELECT id FROM economictracker.users WHERE username = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            return rs.next();  // true if username taken

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}