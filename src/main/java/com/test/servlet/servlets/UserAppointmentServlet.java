package com.test.servlet.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.test.servlet.entity.Appointment;
import com.test.servlet.entity.Consultant;
import com.test.servlet.persistance.dao.ApplicantDAO;
import com.test.servlet.persistance.dao.AppoinmentDAO;
import com.test.servlet.persistance.dao.ConsultantDAO;
import com.test.servlet.utility.HibernateProxyTypeAdapter;
import com.test.servlet.utility.LocalDateTimeSerializer;

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

@WebServlet(name = "UserAppointmentServlet", urlPatterns = "/userappointmentServlet")

public class UserAppointmentServlet extends HttpServlet {

    private ConsultantDAO consultantDAO;
    private ApplicantDAO applicantDAO;
    private AppoinmentDAO appoinmentDAO;

    public void init() {
        consultantDAO = new ConsultantDAO();
         appoinmentDAO = new AppoinmentDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doGet(req, resp);
        HttpSession httpSession = req.getSession();
        int id = (Integer) httpSession.getAttribute("id");

        try{

            List<Appointment> consultantList = appoinmentDAO.findAppoinmentApplicantId(id);

            if (consultantList != null) {
                sendAsJson(resp, consultantList);
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
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
