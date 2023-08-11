package com.test.servlet.servlets;

import com.test.servlet.persistance.dao.ConsultantDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UserServlet", urlPatterns = "/userServlet")
public class UserServlet extends HttpServlet {

    private ConsultantDAO userDao;

    public void init() {
        userDao = new ConsultantDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        System.out.println("---action : " + action);
        String param = request.getParameter("action");
        System.out.println("---param : " + param);



        switch (action) {
            case "new":
                showNewForm(request, response);
                break;
            case "insert":
            //    insertUser(request, response);
                break;
            case "delete":
           //     deleteUser(request, response);
                break;
            case "edit":
            //    showEditForm(request, response);
                break;
            case "update":
            //   updateUser(request, response);
                break;
            default:
            //    listUser(request, response);
                break;
        }
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
        dispatcher.forward(request, response);
    }
}
