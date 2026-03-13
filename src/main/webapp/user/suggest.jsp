<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    if (session == null || !"USER".equals(session.getAttribute("role"))) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Submit Suggestion</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f2f5;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        .container {
            background-color: white;
            padding: 40px;
            border-radius: 10px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.1);
            width: 400px;
        }
        h2 {
            text-align: center;
            color: #333;
            margin-bottom: 24px;
        }
        label {
            font-weight: bold;
            color: #555;
        }
        input[type="text"], textarea {
            width: 100%;
            padding: 10px;
            margin: 8px 0 16px 0;
            border: 1px solid #ccc;
            border-radius: 6px;
            box-sizing: border-box;
        }
        textarea {
            resize: vertical;
        }
        input[type="submit"] {
            width: 100%;
            padding: 10px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            font-size: 16px;
        }
        input[type="submit"]:hover {
            background-color: #45a049;
        }
        .error {
            color: red;
            text-align: center;
            margin-bottom: 12px;
        }
        .link {
            text-align: center;
            margin-top: 16px;
        }
        .link a {
            color: #4CAF50;
            text-decoration: none;
        }
        .link a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<div class="navbar">
        <span>Welcome, <%= session.getAttribute("username") %></span>
        <a href="<%= request.getContextPath() %>/logout">Logout</a>
    </div>
    <div class="container">
        <h2>Submit a Suggestion</h2>
        <% if (request.getAttribute("error") != null) { %>
            <p class="error"><%= request.getAttribute("error") %></p>
        <% } %>
        <form action="<%= request.getContextPath() %>/suggestion" method="post">
            <label>Title:</label>
            <input type="text" name="title" required/>
            <label>Message:</label>
            <textarea name="message" rows="5" required></textarea>
            <input type="submit" value="Submit Suggestion"/>
        </form>
        <div class="link">
            <a href="<%= request.getContextPath() %>/suggestion">View My Suggestions</a>
        </div>
    </div>
</body>
</html>