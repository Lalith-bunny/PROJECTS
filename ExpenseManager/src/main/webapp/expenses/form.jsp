<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.ec.model.Expenses" %>
<!DOCTYPE html>
<html>
<head>
    <title>Expense Form</title>
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; }
        body { font-family: Arial, sans-serif; background: #f0f2f5; display: flex; justify-content: center; align-items: center; height: 100vh; }
        .card { background: white; padding: 40px; border-radius: 10px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); width: 400px; }
        h2 { text-align: center; margin-bottom: 25px; color: #333; }
        label { font-size: 13px; color: #555; }
        input, select { width: 100%; padding: 10px; margin: 6px 0 16px; border: 1px solid #ccc; border-radius: 6px; font-size: 14px; }
        button { width: 100%; padding: 10px; background: #FF9800; color: white; border: none; border-radius: 6px; font-size: 16px; cursor: pointer; }
        button:hover { background: #e68900; }
        .back { text-align: center; margin-top: 15px; font-size: 13px; }
        a { color: #FF9800; text-decoration: none; }
    </style>
</head>
<body>

<%
    // Check if editing or adding
    Expenses expense = (Expenses) request.getAttribute("expense");
    boolean isEdit = (expense != null);
%>

<div class="card">
    <h2><%= isEdit ? "Edit Expense" : "Add Expense" %></h2>

    <form action="${pageContext.request.contextPath}/expenses" method="post">

        <!-- Hidden fields for update -->
        <% if (isEdit) { %>
            <input type="hidden" name="action" value="update" />
            <input type="hidden" name="id" value="<%= expense.getId() %>" />
        <% } %>

        <label>Description</label>
        <input type="text" name="description" required
               value="<%= isEdit ? expense.getDescription() : "" %>"
               placeholder="e.g. Lunch, Bus ticket" />

        <label>Amount (₹)</label>
        <input type="number" name="amount" step="0.01" required
               value="<%= isEdit ? expense.getAmount() : "" %>"
               placeholder="e.g. 250.00" />

        <label>Category</label>
        <select name="category">
            <option value="Food"     <%= isEdit && "Food".equals(expense.getCategory())     ? "selected" : "" %>>Food</option>
            <option value="Travel"   <%= isEdit && "Travel".equals(expense.getCategory())   ? "selected" : "" %>>Travel</option>
            <option value="Shopping" <%= isEdit && "Shopping".equals(expense.getCategory()) ? "selected" : "" %>>Shopping</option>
            <option value="Health"   <%= isEdit && "Health".equals(expense.getCategory())   ? "selected" : "" %>>Health</option>
            <option value="Bills"    <%= isEdit && "Bills".equals(expense.getCategory())    ? "selected" : "" %>>Bills</option>
            <option value="Other"    <%= isEdit && "Other".equals(expense.getCategory())    ? "selected" : "" %>>Other</option>
        </select>

        <label>Date</label>
        <input type="date" name="expenseDate" required
               value="<%= isEdit ? expense.getExpenseDate() : "" %>" />

        <button type="submit"><%= isEdit ? "Update Expense" : "Add Expense" %></button>
    </form>

    <div class="back">
        <a href="expenses">← Back to list</a>
    </div>
</div>
</body>
</html>