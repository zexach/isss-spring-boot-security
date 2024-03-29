package com.example.demo.controller;

import com.example.demo.dto.StudentDTO;
import com.example.demo.model.Address;
import com.example.demo.model.Student;
import com.example.demo.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    public List<StudentDTO> getStudents(){
        return studentService.getStudents();
    }

    @GetMapping(path = "/{studentID}")
    public Student getStudent(@PathVariable("studentID") Integer id) {
        return studentService.getStudent(id);
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
