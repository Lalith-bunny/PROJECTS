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

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
    SuggestionDAO suggestionDAO = new SuggestionDAO();

    // Show all suggestions
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || !"ADMIN".equals(session.getAttribute("role"))) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        try {
            req.setAttribute("suggestions", suggestionDAO.getAllSuggestions());
        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("error", "Database error. Try again.");
        }
        req.getRequestDispatcher("/admin/list.jsp").forward(req, resp);
    }

    // Handle reply and delete
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || !"ADMIN".equals(session.getAttribute("role"))) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        String action = req.getParameter("action");
        int id = Integer.parseInt(req.getParameter("id"));
        try {
            if ("reply".equals(action)) {
                String replyText = req.getParameter("replyText");
                suggestionDAO.addReply(id, replyText);
            } else if ("delete".equals(action)) {
                suggestionDAO.deleteSuggestion(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("error", "Database error. Try again.");
            req.getRequestDispatcher("/admin/list.jsp").forward(req, resp);
            return;
        }
        resp.sendRedirect(req.getContextPath() + "/admin");
    }
}