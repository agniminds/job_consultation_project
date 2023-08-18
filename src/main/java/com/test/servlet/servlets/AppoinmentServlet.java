
package com.test.servlet.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.test.servlet.entity.Applicant;
import com.test.servlet.entity.Appointment;
import com.test.servlet.entity.Consultant;
import com.test.servlet.entity.Slot;
import com.test.servlet.persistance.dao.ApplicantDAO;
import com.test.servlet.persistance.dao.AppoinmentDAO;
import com.test.servlet.persistance.dao.ConsultantDAO;
import com.test.servlet.persistance.dao.SlotDAO;
import com.test.servlet.utility.HibernateProxyTypeAdapter;
import com.test.servlet.utility.LocalDateTimeDeserializer;
import com.test.servlet.utility.LocalDateTimeSerializer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet(name = "AppoinmentServlet", urlPatterns = "/appoinmentServlet")
public class AppoinmentServlet extends HttpServlet {


    private AppoinmentDAO appoinmentDAO;
    private SlotDAO slotDAO;
    private ApplicantDAO applicantDAO;

    private ConsultantDAO consultantDAO;

    public void init() {
        appoinmentDAO = new AppoinmentDAO();
        slotDAO = new SlotDAO();
        applicantDAO = new ApplicantDAO();
        consultantDAO = new ConsultantDAO();
    }

    // get method for getting user data
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("-------- do get called ------");

        try {
            String appoinmentId = request.getParameter("id");

            if (appoinmentId != null) {
                Appointment appointment = appoinmentDAO.getAppoinment(Integer.parseInt(appoinmentId));
                System.out.println(appointment.getEndTime());
                sendAsJson(response, appointment);
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }

        }catch (Exception e){
            e.printStackTrace();
        }


    }

    // Post method for saving the data
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("----------------");

        try {
            HttpSession session = request.getSession();

            // I need to get the user Id somehow ....

            /* int userId = (int)session.getAttribute("id"); */

            String userId = session.getAttribute("id").toString();
            System.out.println("--------session user id -------------");
            System.out.println(userId);

            StringBuilder buffer = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            String payload = buffer.toString();
            System.out.println(payload);

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Date.class, new LocalDateTimeDeserializer())
                    .setPrettyPrinting().create();

            JsonObject jsonObject = gson.fromJson(payload, JsonObject.class);
            int slotId = jsonObject.get("id").getAsInt();

            Slot slot = slotDAO.getSlotUpdateAsBooked(slotId);


           // Applicant applicant = applicantDAO.getApplicant(userId);
            Applicant applicant = applicantDAO.getApplicant(1);
            Appointment appointment = new Appointment();
            appointment.setApplicant(applicant);



           // int consultantId = Integer.parseInt(slot.getConsultant_id());
            Consultant consultant = slot.getConsultant();
            appointment.setConsultant(consultant);

            appointment.setStartTime(slot.getStartTime());
            appointment.setEndTime(slot.getEndTime());

            /*slot.setBooked(true);
            slotDAO.updateSlot(slot);*/

            appoinmentDAO.saveAppoinment(appointment);
            sendAsJson(response, appointment);




            /*

            LocalDateTime date2 = LocalDateTime.parse("15-8-2023 11:10:00",
                    DateTimeFormatter.ofPattern("dd-M-yyyy hh:mm:ss").withLocale(Locale.ENGLISH));

            System.out.println("------- testing ---------");
            System.out.println(date2.toString());
            System.out.println("--------testing----------");*/



            /*SimpleDateFormat date1 = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");

            try {
                Date dateCreated = date1.parse("15-8-2023 11:10:00");
                System.out.println("------testing--------");
                System.out.println(dateCreated.toLocaleString());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            SimpleDateFormat date1 = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");*/


        } catch (Exception e) {
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