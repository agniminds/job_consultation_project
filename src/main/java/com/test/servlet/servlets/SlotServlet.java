package com.test.servlet.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.test.servlet.entity.Consultant;
import com.test.servlet.entity.Slot;
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

@WebServlet(name = "SlotServlet", urlPatterns = "/slotServlet")
public class SlotServlet extends HttpServlet {

    private SlotDAO slotDAO;

    public void init() {
        slotDAO = new SlotDAO();
    }

    // get method for getting user data
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("-------- do get called ------");

        try {
            HttpSession httpSession = request.getSession();
            int id = (Integer) httpSession.getAttribute("id");

            if (id != 0) {
                List<Slot> slots = slotDAO.findSlotConsultantId(id);
                System.out.println(slots);
                sendAsJson(response, slots);
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

        HttpSession httpSession = request.getSession();
        int id = (Integer) httpSession.getAttribute("id");

        try {
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


            Slot slot = gson.fromJson(payload, Slot.class);
            ConsultantDAO consaltantDao = new ConsultantDAO();
            Consultant consultant1 =consaltantDao.getUser(id);
            slot.setConsultant(consultant1);
            System.out.println(slot.getConsultant());
            System.out.println(slot.getStartTime());
            System.out.println(slot.getEndTime());

            /*LocalDateTime date2 = LocalDateTime.parse("15-8-2023 11:10:00",
                    DateTimeFormatter.ofPattern("dd-M-yyyy hh:mm:ss").withLocale(Locale.ENGLISH));

            System.out.println("------- testing ---------");
            System.out.println(date2.toString());
            System.out.println("--------testing----------");*/

            SimpleDateFormat date1 = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");

            try {
                Date dateCreated = date1.parse("15-8-2023 11:10:00");
                System.out.println("------testing--------");
                System.out.println(dateCreated.toLocaleString());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            //SimpleDateFormat date1 = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");


            slotDAO.saveSlot(slot);

            sendAsJson(response, slot);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("----------------");

        HttpSession httpSession = request.getSession();
        int id = (Integer) httpSession.getAttribute("id");

        try {
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


            Slot slot = gson.fromJson(payload, Slot.class);
            ConsultantDAO consaltantDao = new ConsultantDAO();
            Consultant consultant1 =consaltantDao.getUser(id);
            slot.setConsultant(consultant1);
            System.out.println(slot.getConsultant());
            System.out.println(slot.getStartTime());
            System.out.println(slot.getEndTime());

            /*LocalDateTime date2 = LocalDateTime.parse("15-8-2023 11:10:00",
                    DateTimeFormatter.ofPattern("dd-M-yyyy hh:mm:ss").withLocale(Locale.ENGLISH));

            System.out.println("------- testing ---------");
            System.out.println(date2.toString());
            System.out.println("--------testing----------");*/

            SimpleDateFormat date1 = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");

            try {
                Date dateCreated = date1.parse("15-8-2023 11:10:00");
                System.out.println("------testing--------");
                System.out.println(dateCreated.toLocaleString());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            //SimpleDateFormat date1 = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");


            slotDAO.updateSlot(slot);

            sendAsJson(response, slot);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("-------- do delete called ------");


        try {
            String slotIdParam = request.getParameter("slotId");

            if (slotIdParam != null) {
                int slotId = Integer.parseInt(slotIdParam);

                slotDAO.deleteSlot(slotId);


                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().println("Slot deleted successfully");
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing slotId parameter");
            }

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid slotId parameter");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error deleting slot");
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