package com.example.demo.controller;

import com.example.demo.dto.StudentDTO;
import com.example.demo.model.Address;
import com.example.demo.model.Student;
import com.example.demo.service.StudentService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<StudentDTO> getStudents(){
        return studentService.getStudents();
    }

    @PostMapping
    public String registerNewStudent(@RequestBody Student student, @RequestBody Address address){
        studentService.addNewStudent(student, address);
        return "Student added successfully!";
    }

    @PutMapping(path = "/{studentID}")
    public void updateStudent(
            @PathVariable("studentID") Integer id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email){
        studentService.updateStudent(id, name, email);
    }

    @DeleteMapping(path = "/{studentID}")
    public void deleteStudent(@PathVariable("studentID") Integer id){
        studentService.deleteStudent(id);
    }
}
