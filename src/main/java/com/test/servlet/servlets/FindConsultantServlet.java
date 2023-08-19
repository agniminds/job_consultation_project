package com.test.servlet.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.test.servlet.entity.Applicant;
import com.test.servlet.entity.Consultant;
import com.test.servlet.persistance.dao.ApplicantDAO;
import com.test.servlet.persistance.dao.ConsultantDAO;
import com.test.servlet.utility.HibernateProxyTypeAdapter;
import com.test.servlet.utility.LocalDateTimeSerializer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;


    @WebServlet(name = "FindConsultant", urlPatterns = "/findconsultantServlet")

    public class FindConsultantServlet extends HttpServlet {

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

                List<Consultant> consultantList = consultantDAO.getAllUser();

                if (consultantList != null) {
                    sendAsJson(resp, consultantList);
                } else {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                }

            }catch (Exception e){
                e.printStackTrace();
            }

        }

        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            //super.doGet(req, resp);

            System.out.println("----------------");
            try{


                StringBuilder buffer = new StringBuilder();
                BufferedReader reader = req.getReader();
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                String payload = buffer.toString();
                Gson _gson = new Gson();

                Applicant model = _gson.fromJson(payload, Applicant.class);

                System.out.println("name : " + model.getName());


                Consultant consultant = consultantDAO.findConsultant(model.getName());

                if (consultant != null) {
                    sendAsJson(resp, consultant);
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


