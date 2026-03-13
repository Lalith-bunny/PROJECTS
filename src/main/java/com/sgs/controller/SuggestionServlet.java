package com.sgs.controller;

import com.sgs.dao.SuggestionDAO;
import java.io.*;
import java.sql.*;
import com.sgs.model.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/suggestion")
public class SuggestionServlet extends HttpServlet {
    SuggestionDAO suggestionDAO = new SuggestionDAO();

    // Show user's own suggestions
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || !"USER".equals(session.getAttribute("role"))) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        int userId = (int) session.getAttribute("userId");
        try {
            req.setAttribute("suggestions", suggestionDAO.getSuggestionsByUser(userId));
        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("error", "Database error. Try again.");
        }
        req.getRequestDispatcher("/user/mysuggestions.jsp").forward(req, resp);
    }

    // Handle new suggestion submit
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || !"USER".equals(session.getAttribute("role"))) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        int userId = (int) session.getAttribute("userId");
        String title = req.getParameter("title");
        String message = req.getParameter("message");
        Suggestions s = new Suggestions(userId, title, message);
        try {
            suggestionDAO.addSuggestion(s);
        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("error", "Database error. Try again.");
            req.getRequestDispatcher("/user/suggest.jsp").forward(req, resp);
            return;
        }
        resp.sendRedirect(req.getContextPath() + "/suggestion");
    }
}