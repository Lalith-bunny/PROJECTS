package com.sgs.controller;

import com.sgs.dao.UserDAO;
import java.io.*;
import java.sql.*;
import com.sgs.model.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    UserDAO userDAO = new UserDAO();

    // Show login page
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    // Handle form submit
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        User user = null;
        try {
            user = userDAO.findByUsernameAndPassword(username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("error", "Database error. Try again.");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
            return;
        }
        if (user != null) {
            HttpSession session = req.getSession(true);
            session.setAttribute("userId",   user.getId());
            session.setAttribute("username", user.getUserName());
            session.setAttribute("role",     user.getRole());
            if ("ADMIN".equals(user.getRole())) {
                resp.sendRedirect(req.getContextPath() + "/admin");
            } else {
                resp.sendRedirect(req.getContextPath() + "/user/suggest.jsp");
            }
        } else {
            req.setAttribute("error", "Wrong username or password!");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }
}