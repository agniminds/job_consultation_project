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
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

@WebServlet(name = "LoggedInUserServlet", urlPatterns = "/loggedinuserServlet")

public class LoggedInUserServlet extends HttpServlet {

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
            HttpSession httpSession = req.getSession();
            int id = (Integer) httpSession.getAttribute("id");
            String userType = (String) httpSession.getAttribute("userType");

            if(userType.equals("applicant")){
                Applicant applicant = applicantDAO.getApplicant(id);

                if (applicant != null) {
                    sendAsJson(resp, applicant);
                } else {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                }


            } else if (userType.equals("consultant")) {
                Consultant consultant = consultantDAO.getUser(id);

                if (consultant != null) {
                    sendAsJson(resp, consultant);
                } else {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                }

            }else {
                System.out.println("Error");
            }
        }catch (Exception e){
            e.printStackTrace();
        }



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
