package com.test.servlet.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="Consultant",
        uniqueConstraints={@UniqueConstraint(columnNames={"ID"})})
public class Consultant extends User {

    @Column(name = "isAdmin", nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean isAdmin;

    public boolean isAdmin(){
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin){
        this.isAdmin = isAdmin;
    }


}
