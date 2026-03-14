<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; }
        body { font-family: Arial, sans-serif; background: #f0f2f5; display: flex; justify-content: center; align-items: center; height: 100vh; }
        .card { background: white; padding: 40px; border-radius: 10px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); width: 350px; }
        h2 { text-align: center; margin-bottom: 20px; color: #333; }
        input { width: 100%; padding: 10px; margin: 8px 0 16px; border: 1px solid #ccc; border-radius: 6px; font-size: 14px; }
        button { width: 100%; padding: 10px; background: #4CAF50; color: white; border: none; border-radius: 6px; font-size: 16px; cursor: pointer; }
        button:hover { background: #45a049; }
        .error { color: red; font-size: 13px; margin-bottom: 10px; text-align: center; }
        .link { text-align: center; margin-top: 15px; font-size: 13px; }
        a { color: #4CAF50; text-decoration: none; }
    </style>
</head>
<body>
    <div class="card">
        <h2>Expense Tracker</h2>
        <h3 style="text-align:center; margin-bottom:20px; color:#555;">Login</h3>

        <!-- Show error if any -->
        <% if (request.getAttribute("error") != null) { %>
            <p class="error"><%= request.getAttribute("error") %></p>
        <% } %>

        <form action="login" method="post">
            <label>Username</label>
            <input type="text" name="username" required placeholder="Enter username" />

            <label>Password</label>
            <input type="password" name="password" required placeholder="Enter password" />

            <button type="submit">Login</button>
        </form>

        <div class="link">
            Don't have an account? <a href="register">Register</a>
        </div>
    </div>
</body>
</html>