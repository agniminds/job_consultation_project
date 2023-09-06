package com.test.servlet.servlets;

import com.opencsv.CSVWriter;
import com.test.servlet.entity.Appointment;
import com.test.servlet.persistance.dao.AppoinmentDAO;

import java.io.*;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.opencsv.CSVWriter;


@WebServlet(name = "DownloadFileServlet", urlPatterns = "/downloadfileServlet")

public class DownloadFileServlet extends HttpServlet {



    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {


        // Fetch appointment data from AppoinmentDAO
        AppoinmentDAO appoinmentDAO = new AppoinmentDAO();
        List<Appointment> appointments = appoinmentDAO.getAllAppoinments();

        // Set response content type to CSV
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"appointments.csv\"");

        // Create CSVWriter
        PrintWriter writer = response.getWriter();
        CSVWriter csvWriter = new CSVWriter(writer);

        // Write CSV header
        String[] header = {"Appointment ID", "Consultant Name", "User Name", "Start Time", "End Time"};
        csvWriter.writeNext(header);

        // Write appointment data to CSV
        for (Appointment appointment : appointments) {
            String[] data = {
                    String.valueOf(appointment.getId()),
                    appointment.getConsultant().getName(),
                    appointment.getApplicant().getName(),
                    appointment.getStartTime().toString(),
                    appointment.getEndTime().toString()
            };
            csvWriter.writeNext(data);
        }

        // Close CSVWriter
        csvWriter.close();


    }

}