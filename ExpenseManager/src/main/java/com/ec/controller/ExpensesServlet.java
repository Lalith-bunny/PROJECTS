package com.ec.controller;

import com.ec.dao.ExpensesDAO;
import com.ec.model.Expenses;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

@WebServlet("/expenses")
public class ExpensesServlet extends HttpServlet {

    ExpensesDAO expenseDAO = new ExpensesDAO();

    // GET - show expenses list
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Session check
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
        	response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        int userId = (int) session.getAttribute("userId");
        String action = request.getParameter("action");

        // DELETE action via GET
        if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            expenseDAO.delete(id);
            response.sendRedirect(request.getContextPath() + "/expenses");  // ✅ absolute
            return;
        }

        // EDIT - load expense into form
        if ("edit".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            Expenses expense = expenseDAO.getById(id);
            request.setAttribute("expense", expense);
            request.getRequestDispatcher("/expenses/form.jsp").forward(request, response);
            return;
        }

        // Default - load list page
        List<Expenses> expenses = expenseDAO.getAll(userId);
        double total = expenseDAO.getTotal(userId);

        request.setAttribute("expenses", expenses);
        request.setAttribute("total", total);
        request.setAttribute("categoryTotals", expenseDAO.getTotalByCategory(userId));
        request.getRequestDispatcher("/expenses/list.jsp").forward(request, response);
    }

    // POST - add or update expense
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Session check
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
        	response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        int userId = (int) session.getAttribute("userId");
        String action = request.getParameter("action");

        // Parse form fields
        String description = request.getParameter("description");
        double amount      = Double.parseDouble(request.getParameter("amount"));
        String category    = request.getParameter("category");
        Date date          = Date.valueOf(request.getParameter("expenseDate")); // expects yyyy-MM-dd

        if ("update".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            Expenses expense = new Expenses(userId, description, amount, category, date);
            expense.setId(id);
            expenseDAO.update(expense);
        } else {
            // Default - add new expense
            Expenses expense = new Expenses(userId, description, amount, category, date);
            expenseDAO.add(expense);
        }

        response.sendRedirect(request.getContextPath() + "/expenses");  // ✅ absolute
    }
}