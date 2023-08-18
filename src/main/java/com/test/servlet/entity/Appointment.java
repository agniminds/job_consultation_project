package com.test.servlet.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="appointment",
        uniqueConstraints={@UniqueConstraint(columnNames={"ID"})})
public class Appointment {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="ID", nullable=false, unique=true, length=11)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "consultant_id", referencedColumnName = "id")
    private Consultant consultant;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "applicant_id", referencedColumnName = "id")
    private Applicant applicant;


    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "end_time")
    private Date endTime;

    @Column(name = "notes")
    private String notes;

    @Column(name = "feedback")
    private String feedback;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Consultant getConsultant() {
        return consultant;
    }

    public void setConsultant(Consultant consultant) {
        this.consultant = consultant;
    }

    public Applicant getApplicant() {
        return applicant;
    }

    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
