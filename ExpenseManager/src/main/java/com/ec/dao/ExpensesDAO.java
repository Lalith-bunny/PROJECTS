package com.ec.dao;

import com.ec.model.Expenses;
import com.ec.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExpensesDAO {

    // CREATE - INSERT new expense
    public boolean add(Expenses expense) {
        String query = "INSERT INTO economictracker.expenses (user_id, description, amount, category, expense_date) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, expense.getUserId());
            ps.setString(2, expense.getDescription());
            ps.setDouble(3, expense.getAmount());
            ps.setString(4, expense.getCategory());
            ps.setDate(5, expense.getExpenseDate());

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // READ - GET all expenses by userId
    public List<Expenses> getAll(int userId) {
        List<Expenses> list = new ArrayList<>();
        String query = "SELECT * FROM economictracker.expenses WHERE user_id = ? ORDER BY expense_date DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Expenses expense = new Expenses();
                expense.setId(rs.getInt("id"));
                expense.setUserId(rs.getInt("user_id"));
                expense.setDescription(rs.getString("description"));
                expense.setAmount(rs.getDouble("amount"));
                expense.setCategory(rs.getString("category"));
                expense.setExpenseDate(rs.getDate("expense_date"));
                list.add(expense);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // READ - GET single expense by id (useful for edit form)
    public Expenses getById(int id) {
        String query = "SELECT * FROM economictracker.expenses WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Expenses expense = new Expenses();
                expense.setId(rs.getInt("id"));
                expense.setUserId(rs.getInt("user_id"));
                expense.setDescription(rs.getString("description"));
                expense.setAmount(rs.getDouble("amount"));
                expense.setCategory(rs.getString("category"));
                expense.setExpenseDate(rs.getDate("expense_date"));
                return expense;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // READ - GET total expenditure by userId
    public double getTotal(int userId) {
        String query = "SELECT SUM(amount) AS total FROM economictracker.expenses WHERE user_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getDouble("total");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    // READ - GET total by category (for dashboard summary)
    public List<Object[]> getTotalByCategory(int userId) {
        List<Object[]> list = new ArrayList<>();
        String query = "SELECT category, SUM(amount) AS total FROM economictracker.expenses WHERE user_id = ? GROUP BY category ORDER BY total DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Object[] row = new Object[2];
                row[0] = rs.getString("category");
                row[1] = rs.getDouble("total");
                list.add(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // UPDATE - edit existing expense
    public boolean update(Expenses expense) {
        String query = "UPDATE economictracker.expenses SET description = ?, amount = ?, category = ?, expense_date = ? WHERE id = ? AND user_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, expense.getDescription());
            ps.setDouble(2, expense.getAmount());
            ps.setString(3, expense.getCategory());
            ps.setDate(4, expense.getExpenseDate());
            ps.setInt(5, expense.getId());
            ps.setInt(6, expense.getUserId());

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // DELETE - remove expense by id
    public boolean delete(int id) {
        String query = "DELETE FROM economictracker.expenses WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}