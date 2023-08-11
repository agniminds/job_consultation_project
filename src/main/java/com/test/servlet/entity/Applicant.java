package com.test.servlet.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="Applicant",
        uniqueConstraints={@UniqueConstraint(columnNames={"ID"})})
public class Applicant extends User {
}
