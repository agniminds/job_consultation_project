package com.test.servlet.servlets;

import org.hibernate.Session;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LoggedInUserServlet", urlPatterns = "/loggedinuserServlet")

public class LoggedInUserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);

        HttpSession httpSession = req.getSession();
        String id = (String) httpSession.getAttribute("id");
        String userType = (String) httpSession.getAttribute("userType");


    }
}
