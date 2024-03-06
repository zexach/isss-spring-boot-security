package com.example.demo.student;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents(){
        return studentRepository.findAll();
    }

    public void addNewStudent(Student student){
        Optional<Student> studentByEmail = studentRepository.findStudentByEmail(student.getEmail());
        if(studentByEmail.isPresent()){
            throw new IllegalStateException("email is taken");
        }

        studentRepository.save(student);
    }

    @Transactional
    public void updateStudent(Integer id, String name, String email){
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("student doesn't exist"));

        if (name != null && name.length() > 0 && !Objects.equals(student.getName(), name)) {
            student.setName(name);
        }
        if (email != null && email.length() > 0 && !Objects.equals(student.getEmail(), email)) {
            Optional<Student> studentOptional = studentRepository.findStudentByEmail(email);
            if(studentOptional.isPresent()){
                throw new IllegalStateException("email is taken");
            }
            student.setEmail(email);
        }
    }

    public void deleteStudent(Integer id){
        boolean exists = studentRepository.existsById(id);
        if(!exists){
            throw new IllegalStateException("id doesn't exist");
        }
        studentRepository.deleteById(id);
    }
}
