package com.neosoft.student.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class StudentDto {

    private Long id;

    @NotEmpty
    private String firstname;
    @NotEmpty
    private String lastname;

    private Long mobileNumber;

    @NotEmpty
    @Email
    private String email;

    private List<String> project;

    @NotEmpty
    private String password;
}
