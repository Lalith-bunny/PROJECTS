<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
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
            width: 350px;
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
        input[type="text"], input[type="password"] {
            width: 100%;
            padding: 10px;
            margin: 8px 0 16px 0;
            border: 1px solid #ccc;
            border-radius: 6px;
            box-sizing: border-box;
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
    <div class="container">
        <h2>Login</h2>
        <% if (request.getAttribute("error") != null) { %>
            <p class="error"><%= request.getAttribute("error") %></p>
        <% } %>
        <form action="<%= request.getContextPath() %>/login" method="post">
            <label>Username:</label>
            <input type="text" name="username" required/>
            <label>Password:</label>
            <input type="password" name="password" required/>
            <input type="submit" value="Login"/>
        </form>
        <div class="link">
            <a href="<%= request.getContextPath() %>/register">Don't have an account? Register</a>
        </div>
    </div>
</body>
</html>