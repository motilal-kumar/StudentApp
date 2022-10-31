package com.neosoft.student.entity;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "project_tab")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    private  String proj_name;

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private Student associatedStudent;


}
