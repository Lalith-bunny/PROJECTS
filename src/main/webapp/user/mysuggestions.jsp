<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.sgs.model.Suggestions"%>
<%
    if (session == null || !"USER".equals(session.getAttribute("role"))) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
    List<Suggestions> suggestions = (List<Suggestions>) request.getAttribute("suggestions");
%>
<!DOCTYPE html>
<html>
<head>
    <title>My Suggestions</title>
    <style>
     .navbar {
        background-color: #333;
        overflow: hidden;
        padding: 12px 20px;
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin: -30px -30px 30px -30px;
    }
    .navbar span {
        color: white;
        font-size: 16px;
    }
    .navbar a {
        color: #f44336;
        text-decoration: none;
        font-size: 15px;
        font-weight: bold;
        padding: 6px 14px;
        border: 1px solid #f44336;
        border-radius: 4px;
    }
    .navbar a:hover {
        background-color: #f44336;
        color: white;
    }
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f2f5;
            margin: 0;
            padding: 30px;
        }
        h2 {
            color: #333;
            text-align: center;
            margin-bottom: 24px;
        }
        .error {
            color: red;
            text-align: center;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            background-color: white;
            border-radius: 10px;
            overflow: hidden;
            box-shadow: 0 4px 12px rgba(0,0,0,0.1);
        }
        th {
            background-color: #4CAF50;
            color: white;
            padding: 12px;
            text-align: left;
        }
        td {
            padding: 12px;
            border-bottom: 1px solid #eee;
            color: #555;
        }
        tr:last-child td {
            border-bottom: none;
        }
        tr:hover td {
            background-color: #f9f9f9;
        }
        .no-reply {
            color: #aaa;
            font-style: italic;
        }
        .link {
            text-align: center;
            margin-top: 24px;
        }
        .link a {
            color: #4CAF50;
            text-decoration: none;
            font-size: 16px;
        }
        .link a:hover {
            text-decoration: underline;
        }
        .empty {
            text-align: center;
            color: #aaa;
            font-size: 18px;
            margin-top: 40px;
        }
    </style>
</head>
<body>
<div class="navbar">
        <span>Welcome, <%= session.getAttribute("username") %></span>
        <a href="<%= request.getContextPath() %>/logout">Logout</a>
    </div>
    <h2>My Suggestions</h2>
    <% if (request.getAttribute("error") != null) { %>
        <p class="error"><%= request.getAttribute("error") %></p>
    <% } %>
    <% if (suggestions == null || suggestions.isEmpty()) { %>
        <p class="empty">No suggestions submitted yet.</p>
    <% } else { %>
        <table>
            <tr>
                <th>Title</th>
                <th>Message</th>
                <th>Admin Reply</th>
                <th>Submitted At</th>
            </tr>
            <% for (Suggestions s : suggestions) { %>
            <tr>
                <td><%= s.getTitle() %></td>
                <td><%= s.getMessage() %></td>
                <td>
                    <% if (s.getAdminReply() != null) { %>
                        <%= s.getAdminReply() %>
                    <% } else { %>
                        <span class="no-reply">No reply yet</span>
                    <% } %>
                </td>
                <td><%= s.getSubmittedAt() %></td>
            </tr>
            <% } %>
        </table>
    <% } %>
    <div class="link">
        <a href="<%= request.getContextPath() %>/user/suggest.jsp">Submit New Suggestion</a>
    </div>
</body>
</html>