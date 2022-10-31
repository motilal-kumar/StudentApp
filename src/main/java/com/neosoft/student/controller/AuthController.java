package com.neosoft.student.controller;

import com.neosoft.student.dto.StudentDto;
import com.neosoft.student.entity.Student;
import com.neosoft.student.exception.ResourceNotFoundException;
import com.neosoft.student.repository.StudentRepository;
import com.neosoft.student.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@Transactional
public class AuthController {

    @Autowired
    private IStudentService studentService;

    @Autowired
    private StudentRepository studentRepository;

    // handler method to handle home page request
    @GetMapping("/index")
    public String home(){
        return "index";
    }


    // handler method to handle login request
    @GetMapping("/login")
    public String login(){

        System.out.println("login");
        return "login";

    }


    // handler method to handle student registration form request
    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        // create model object to store form data
        StudentDto student = new StudentDto();
        model.addAttribute("student", student);
        return "register";
    }



    // handler method to handle student registration form submit request
    @PostMapping("/register/save")
    public String registration(@Valid  @ModelAttribute("student") StudentDto studentDto,
                               BindingResult result,
                               Model model){
        Student existingStudent = studentService.findStudentByEmail(studentDto.getEmail());

        System.out.println("existingStudent:"+existingStudent);

        if(existingStudent != null && existingStudent.getEmail() != null && !existingStudent.getEmail().isEmpty()){
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        if(result.hasErrors()){
            model.addAttribute("student", studentDto);
            return "/register";
        }

        studentService.saveStudent(studentDto);
        return "redirect:/register?success";
    }


    // handler method to handle list of students
    @GetMapping("/students")
    public String students(Model model){
        List<StudentDto> students = studentService.findAllStudents();
        model.addAttribute("students", students);
        return "students";
    }


    @GetMapping("/api/{id}/students")
    public ResponseEntity<List<Student>> getAllStudentByStudentId(@PathVariable(value = "id") Long id) {
        if (!studentRepository.existsById(id)) {

            throw new ResourceNotFoundException("Not found Student with id = " +id);
        }

        List<Student> students = studentRepository.findAllById(id);

        System.out.println("students: "+students);

        return new ResponseEntity<>(students, HttpStatus.OK);
    }



    // handler method to handle student registration form submit request
    /*@PostMapping("/register/save")
    public String registration( @ModelAttribute("student") StudentDto studentDto,
                               BindingResult result,
                               Model model){


        studentService.saveStudent(studentDto);
        return "redirect:/register?success";
    }
*/


}





