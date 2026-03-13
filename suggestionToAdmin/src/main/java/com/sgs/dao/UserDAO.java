package com.sgs.dao;


import java.sql.*;
import com.sgs.model.User;
import com.sgs.util.DBConnection;


public class UserDAO{
	public User findByUsernameAndPassword(String username, String password) throws SQLException {
	    String sql = "SELECT * FROM users WHERE username=? AND password=?";
	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {
	        ps.setString(1, username); ps.setString(2, password);
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            User u = new User();
	            u.setId(rs.getInt("id"));
	            u.setUserName(rs.getString("username"));
	            u.setRole(rs.getString("role")); // if column exists
	            return u;
	        }
	    }
	    return null; // no match
	}
	public void registerUser(User u) throws SQLException {
	    String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {
	        ps.setString(1, u.getUserName());
	        ps.setString(2, u.getPassword());
	        ps.setString(3, u.getRole());
	        ps.executeUpdate();
	    }
	}

}