package com.ec.controller;

import com.ec.dao.UserDAO;
import com.ec.model.User;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    UserDAO userDAO = new UserDAO();

    // GET - show register page
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }

    // POST - handle register form submit
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Check if username already taken
        if (userDAO.usernameExists(username)) {
            request.setAttribute("error", "Username already taken");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        User user = new User(username, password);
        boolean success = userDAO.register(user);

        if (success) {
            response.sendRedirect("login");
        } else {
            request.setAttribute("error", "Registration failed, try again");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        }
    }
}