<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.ec.model.Expenses, com.ec.model.User, java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>My Expenses</title>
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; }
        body { font-family: Arial, sans-serif; background: #f0f2f5; }

        /* Navbar */
        .navbar { background: #333; color: white; padding: 15px 30px; display: flex; justify-content: space-between; align-items: center; }
        .navbar h1 { font-size: 20px; }
        .navbar a { color: #ff6b6b; text-decoration: none; font-size: 14px; }

        /* Summary cards */
        .summary { display: flex; gap: 20px; padding: 20px 30px; flex-wrap: wrap; }
        .card { background: white; padding: 20px; border-radius: 10px; box-shadow: 0 2px 8px rgba(0,0,0,0.1); flex: 1; min-width: 150px; text-align: center; }
        .card h3 { font-size: 13px; color: #888; margin-bottom: 8px; }
        .card p  { font-size: 24px; font-weight: bold; color: #333; }
        .total p { color: #4CAF50; }

        /* Add button */
        .actions { padding: 0 30px 15px; }
        .btn-add { background: #4CAF50; color: white; padding: 10px 20px; border-radius: 6px; text-decoration: none; font-size: 14px; }
        .btn-add:hover { background: #45a049; }

        /* Table */
        .table-wrap { padding: 0 30px 30px; overflow-x: auto; }
        table { width: 100%; border-collapse: collapse; background: white; border-radius: 10px; overflow: hidden; box-shadow: 0 2px 8px rgba(0,0,0,0.1); }
        th { background: #333; color: white; padding: 12px 15px; text-align: left; font-size: 13px; }
        td { padding: 12px 15px; font-size: 14px; border-bottom: 1px solid #eee; }
        tr:last-child td { border-bottom: none; }
        tr:hover td { background: #f9f9f9; }

        /* Category badge */
        .badge { padding: 4px 10px; border-radius: 20px; font-size: 12px; font-weight: bold; }
        .Food     { background: #fff3cd; color: #856404; }
        .Travel   { background: #d1ecf1; color: #0c5460; }
        .Shopping { background: #f8d7da; color: #721c24; }
        .Health   { background: #d4edda; color: #155724; }
        .Bills    { background: #e2d9f3; color: #4a235a; }
        .Other    { background: #e2e3e5; color: #383d41; }

        /* Action buttons */
        .btn-edit   { background: #FF9800; color: white; padding: 5px 12px; border-radius: 4px; text-decoration: none; font-size: 12px; }
        .btn-delete { background: #f44336; color: white; padding: 5px 12px; border-radius: 4px; text-decoration: none; font-size: 12px; margin-left: 5px; }
        .btn-edit:hover   { background: #e68900; }
        .btn-delete:hover { background: #d32f2f; }

        .empty { text-align: center; padding: 40px; color: #aaa; font-size: 15px; }
    </style>
</head>
<body>

<%
    User user = (User) session.getAttribute("user");
    List<Expenses> expenses = (List<Expenses>) request.getAttribute("expenses");
    double total = (double) request.getAttribute("total");
%>

<!-- Navbar -->
<div class="navbar">
    <h1>💰 Expense Tracker</h1>
    <span>Welcome, <%= user.getUsername() %> &nbsp;|&nbsp; <a href="logout">Logout</a></span>
</div>

<!-- Summary Cards -->
<div class="summary">
    <div class="card total">
        <h3>Total Expenditure</h3>
        <p>₹ <%= String.format("%.2f", total) %></p>
    </div>
    <div class="card">
        <h3>Total Transactions</h3>
        <p><%= expenses.size() %></p>
    </div>
</div>

<!-- Add Expense Button -->
<div class="actions">
    <a href="expenses/form.jsp" class="btn-add">+ Add Expense</a>
</div>

<!-- Expenses Table -->
<div class="table-wrap">
    <table>
        <thead>
            <tr>
                <th>#</th>
                <th>Description</th>
                <th>Amount</th>
                <th>Category</th>
                <th>Date</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <% if (expenses.isEmpty()) { %>
                <tr><td colspan="6" class="empty">No expenses yet. Add your first one!</td></tr>
            <% } else {
                int i = 1;
                for (Expenses exp : expenses) { %>
                <tr>
                    <td><%= i++ %></td>
                    <td><%= exp.getDescription() %></td>
                    <td>₹ <%= String.format("%.2f", exp.getAmount()) %></td>
                    <td><span class="badge <%= exp.getCategory() %>"><%= exp.getCategory() %></span></td>
                    <td><%= exp.getExpenseDate() %></td>
                    <td>
                        <a href="expenses?action=edit&id=<%= exp.getId() %>" class="btn-edit">Edit</a>
                        <a href="expenses?action=delete&id=<%= exp.getId() %>"
                           class="btn-delete"
                           onclick="return confirm('Delete this expense?')">Delete</a>
                    </td>
                </tr>
            <% } } %>
        </tbody>
    </table>
</div>

</body>
</html>