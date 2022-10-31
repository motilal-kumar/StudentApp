package com.neosoft.student.service;


import com.neosoft.student.dto.StudentDto;
import com.neosoft.student.entity.Project;
import com.neosoft.student.entity.Role;
import com.neosoft.student.entity.Student;
import com.neosoft.student.repository.ProjectRepository;
import com.neosoft.student.repository.RoleRepository;
import com.neosoft.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements IStudentService{

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired(required=true)
    private PasswordEncoder passwordEncoder;

    @Override
    public void saveStudent(StudentDto studentDto) {

        Student student = new Student();
        student.setFirstname(studentDto.getFirstname());
        student.setLastname(studentDto.getLastname());
        student.setEmail(studentDto.getEmail());
        // encrypt the password using spring security
        student.setPassword(passwordEncoder.encode(studentDto.getPassword()));
        student.setMobile_number(studentDto.getMobileNumber());


        Project project1 = new Project();
        List<Project> projects = new ArrayList<>();
        student.setProject(projects);
        Student savedStudent = studentRepository.save(student);
        project1.setAssociatedStudent(savedStudent);

        String projectNames =  studentDto.getProject()
                .stream().collect(Collectors
                        .joining( "," ));

        System.out.println("projectNames:"+projectNames);
        project1.setProj_name(projectNames);
        projects.add(project1);
        projectRepository.save(project1);

        Role role = roleRepository.findByName("ROLE_ADMIN");
        if(role == null){
            role = checkRoleExist();
        }
        student.setRoles(Arrays.asList(role));

    }

    @Override
    public List<StudentDto> findAllStudents() {

        List<Student> students = studentRepository.findAll();

        System.out.println("studentsstudents: "+students);

        return students.stream()
                    .map((student) -> mapToStudentDto(student))
                    .collect(Collectors.toList());

    }

    @Override
    public Student findStudentByEmail(String email) {

        return studentRepository.findByEmail(email);
    }


    private StudentDto mapToStudentDto(Student student){

        StudentDto studentDto = new StudentDto();
        //String[] str = student.getName().split(" ");
        studentDto.setFirstname(student.getFirstname());
        studentDto.setLastname(student.getLastname());
        studentDto.setEmail(student.getEmail());
        studentDto.setMobileNumber(student.getMobile_number());
        studentDto.setProject(student.getProject()
                .stream().map(p -> p.getProj_name())
                .collect(Collectors.toList()));

        return studentDto;
    }

    private Role checkRoleExist(){
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        return roleRepository.save(role);
    }

    @Override
    public Student findStudentByStudent(String student_id) {
        return null;
    }


     /* private StudentDto mapToUserDto(Student student){
        StudentDto studentDto = new StudentDto();
        String[] str = student.getName().split(" ");
        studentDto.setFirstname(str[0]);
        studentDto.setLastname(str[1]);
        studentDto.setEmail(student.getEmail());
        return studentDto;
    }*/


     /*private Project checkProjectExist(){
        Project project = new Project();
        project.setProj_name("proj_name");
        return projectRepository.save(project);
    }*/

}
