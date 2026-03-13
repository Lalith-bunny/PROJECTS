<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.sgs.model.Suggestions"%>
<%
    if (session == null || !"ADMIN".equals(session.getAttribute("role"))) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
    List<Suggestions> suggestions = (List<Suggestions>) request.getAttribute("suggestions");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Admin - All Suggestions</title>
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
            background-color: #2196F3;
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
        .empty {
            text-align: center;
            color: #aaa;
            font-size: 18px;
            margin-top: 40px;
        }
        input[type="text"] {
            padding: 6px;
            border: 1px solid #ccc;
            border-radius: 4px;
            width: 150px;
        }
        .reply-btn {
            padding: 6px 12px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .reply-btn:hover {
            background-color: #45a049;
        }
        .delete-btn {
            padding: 6px 12px;
            background-color: #f44336;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .delete-btn:hover {
            background-color: #d32f2f;
        }
    </style>
</head>
<body>
<div class="navbar">
        <span>Welcome, <%= session.getAttribute("username") %></span>
        <a href="<%= request.getContextPath() %>/logout">Logout</a>
    </div>
    <h2>All Suggestions</h2>
    <% if (request.getAttribute("error") != null) { %>
        <p class="error"><%= request.getAttribute("error") %></p>
    <% } %>
    <% if (suggestions == null || suggestions.isEmpty()) { %>
        <p class="empty">No suggestions submitted yet.</p>
    <% } else { %>
        <table>
            <tr>
                <th>ID</th>
                <th>User ID</th>
                <th>Title</th>
                <th>Message</th>
                <th>Admin Reply</th>
                <th>Submitted At</th>
                <th>Reply</th>
                <th>Delete</th>
            </tr>
            <% for (Suggestions s : suggestions) { %>
            <tr>
                <td><%= s.getId() %></td>
                <td><%= s.getUserId() %></td>
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
                <td>
                    <form action="<%= request.getContextPath() %>/admin" method="post">
                        <input type="hidden" name="action" value="reply"/>
                        <input type="hidden" name="id" value="<%= s.getId() %>"/>
                        <input type="text" name="replyText" required/>
                        <input type="submit" value="Reply" class="reply-btn"/>
                    </form>
                </td>
                <td>
                    <form action="<%= request.getContextPath() %>/admin" method="post">
                        <input type="hidden" name="action" value="delete"/>
                        <input type="hidden" name="id" value="<%= s.getId() %>"/>
                        <input type="submit" value="Delete" class="delete-btn"
                               onclick="return confirm('Are you sure?')"/>
                    </form>
                </td>
            </tr>
            <% } %>
        </table>
    <% } %>
</body>
</html>