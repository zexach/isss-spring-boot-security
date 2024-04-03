package com.example.demo.service;

import com.example.demo.dto.StudentDTO;
import com.example.demo.enums.Role;
import com.example.demo.model.Address;
import com.example.demo.model.Student;
import com.example.demo.repository.StudentRepository;
import com.example.demo.request.RegisterRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final AddressService addressService;
    private final PasswordEncoder passwordEncoder;
    public final ModelMapper modelMapper;

    public List<StudentDTO> getStudents() {
        return studentRepository.findAll()
                .stream()
                .map(this::convertToStudentDTO)
                .collect(Collectors.toList());
    }

    public Student getStudent(Integer id) {
        return studentRepository.findById(id).orElseThrow();
    }

    @Transactional
    public Student addNewStudent(RegisterRequest student){
        Optional<Student> studentByEmail = this.studentRepository.findStudentByEmail(student.getEmail());

        if(studentByEmail.isPresent()){
            throw new IllegalStateException("Email is taken");
        }

        Address userAddress = addressService.addAddress(student);

        Student studentToAdd = new Student();
        studentToAdd.setName(student.getName());
        studentToAdd.setEmail(student.getEmail());
        studentToAdd.setPassword(passwordEncoder.encode(student.getPassword()));
        studentToAdd.setBirth(student.getBirth());
        studentToAdd.setAddress(userAddress);
        studentToAdd.setRole(Role.USER);

        this.studentRepository.save(studentToAdd);

        return studentToAdd;
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

    public StudentDTO convertToStudentDTO(Student student) {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        StudentDTO studentDTO = new StudentDTO();
        studentDTO = modelMapper.map(student, StudentDTO.class);
        return studentDTO;
    }
}
