package com.test.servlet.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.test.servlet.entity.Applicant;
import com.test.servlet.entity.Consultant;
import com.test.servlet.entity.Slot;
import com.test.servlet.persistance.dao.ApplicantDAO;
import com.test.servlet.persistance.dao.ConsultantDAO;
import com.test.servlet.utility.HibernateProxyTypeAdapter;
import com.test.servlet.utility.LocalDateTimeSerializer;
import org.hibernate.Session;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

@WebServlet(name = "UniApplicantServlet", urlPatterns = "/uniapplicantServlet")

public class UniApplicantServlet extends HttpServlet {

    private ConsultantDAO consultantDAO;
    private ApplicantDAO applicantDAO;

    public void init() {
        consultantDAO = new ConsultantDAO();
        applicantDAO = new ApplicantDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doGet(req, resp);



        try{
            // HttpSession httpSession = req.getSession();
            //int id = (Integer) httpSession.getAttribute("id");

            int id = Integer.parseInt(req.getParameter("applicantId"));

            Applicant applicant = applicantDAO.getApplicant(id);

            if (applicant != null) {
                sendAsJson(resp, applicant);
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{


        int id = Integer.parseInt(request.getParameter("applicantId"));

        System.out.println(id);

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

        Applicant updatedApplicant = applicantDAO.updateApplicant(id,model);

        sendAsJson(response, updatedApplicant);



    }


    private void sendAsJson(
            HttpServletResponse response,
            Object obj) throws IOException {

        response.setContentType("application/json");
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new LocalDateTimeSerializer())
                .registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY)
                .setPrettyPrinting().create();

        String res = gson.toJson(obj);

        PrintWriter out = response.getWriter();

        out.print(res);
        out.flush();
    }




}
