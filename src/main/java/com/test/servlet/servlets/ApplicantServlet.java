package com.test.servlet.servlets;

import com.google.gson.Gson;
import com.test.servlet.entity.Applicant;
import com.test.servlet.persistance.dao.ApplicantDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ApplicantServlet", urlPatterns = "/applicantServlet")
public class ApplicantServlet extends HttpServlet {

    private ApplicantDAO applicantDAO;

    public void init() {
        applicantDAO = new ApplicantDAO();
    }

    // get method for getting user data
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("----------------");

        String applicantName = request.getParameter("name");

        Applicant applicant = applicantDAO.findApplicant(applicantName);

        if (applicant != null){
            sendAsJson(response, applicant);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    // Post method for saving the data
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

        Applicant model = _gson.fromJson(payload, Applicant.class);

        System.out.println("name : " + model.getName());

        applicantDAO.saveApplicant(model);

        sendAsJson(response, model);


    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{

        System.out.println("------------------");

        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }

        String payload = buffer.toString();
        Gson _gson = new Gson();

        Applicant model = _gson.fromJson(payload, Applicant.class);

        System.out.println("id : " + model.getId());

        applicantDAO.deleteApplicant(model.getId());

        sendAsJson(response, model);

    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{

        System.out.println("------------------");

        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }

        String payload = buffer.toString();
        Gson _gson = new Gson();

        Applicant model = _gson.fromJson(payload, Applicant.class);

        System.out.println("id : " + model.getId());

        applicantDAO.updateApplicant(model.getId(), model);

        sendAsJson(response, model);



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