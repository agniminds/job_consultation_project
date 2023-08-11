package com.test.servlet.servlets;

import com.google.gson.Gson;
import com.test.servlet.entity.Consultant;
import com.test.servlet.persistance.dao.ConsultantDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "LoginServlet", urlPatterns = "/loginServlet")
public class LoginServlet extends HttpServlet {

    private ConsultantDAO consultantDAO;

    public void init() {
        consultantDAO = new ConsultantDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("----------------");


        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }

        String payload = buffer.toString();
        Gson _gson = new Gson();

        Consultant model = _gson.fromJson(payload, Consultant.class);

        System.out.println("username : " + model.getUsername());

        System.out.println("password : " + model.getPassword());

        Consultant consultant = consultantDAO.findUser(model.getUsername(), model.getPassword());
        if(consultant!=null) {
            System.out.println("Found user ");

            sendAsJson(response,consultant);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);

        }
    }

    //a utility method to send object
    //as JSON response
    private void sendAsJson(
            HttpServletResponse response,
            Object obj) throws IOException {

        response.setContentType("application/json");
        Gson _gson = new Gson();
        String res = _gson.toJson(obj);

        PrintWriter out = response.getWriter();

        out.print(res);
        out.flush();
    }
}
