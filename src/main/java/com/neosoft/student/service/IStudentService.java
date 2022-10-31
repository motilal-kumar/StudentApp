package com.neosoft.student.service;

import com.neosoft.student.dto.StudentDto;
import com.neosoft.student.entity.Student;

import java.util.List;

public interface IStudentService {

    void saveStudent(StudentDto studentDto);

    Student findStudentByStudent(String student_id);

    List<StudentDto> findAllStudents();
    Student findStudentByEmail(String email);

}
