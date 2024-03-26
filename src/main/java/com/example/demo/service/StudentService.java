package com.example.demo.service;

import com.example.demo.dto.StudentDTO;
import com.example.demo.enums.Role;
import com.example.demo.model.Address;
import com.example.demo.model.Student;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.StudentRepository;
import com.example.demo.request.RegisterRequest;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final AddressRepository addressRepository;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public StudentService(StudentRepository studentRepository, AddressRepository addressRepository, PasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.addressRepository = addressRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Autowired
    public ModelMapper modelMapper;

    public List<StudentDTO> getStudents() {
        return studentRepository.findAll()
                .stream()
                .map(this::convertToStudentDTO)
                .collect(Collectors.toList());
    }
    @Transactional
    public Student addNewStudent(RegisterRequest student){
        Optional<Student> studentByEmail = studentRepository.findStudentByEmail(student.getEmail());
        Optional<Address> existingAddress = addressRepository.
                findAddressByStreetNameAndHouseNumber(student.getAddress().getStreetName(), student.getAddress().getHouseNumber());

        if(studentByEmail.isPresent()){
            throw new IllegalStateException("Email is taken");
        }
        if(existingAddress.isPresent()){
            throw new IllegalStateException("Address is taken");
        }

        Address userAddress = new Address();
        userAddress.setCity(student.getAddress().getCity());
        userAddress.setStreetName(student.getAddress().getStreetName());
        userAddress.setHouseNumber(student.getAddress().getHouseNumber());
        userAddress.setZipCode(student.getAddress().getZipCode());
        addressRepository.save(userAddress);

        Student studentToAdd = new Student();
        studentToAdd.setName(student.getName());
        studentToAdd.setEmail(student.getEmail());
        studentToAdd.setPassword(passwordEncoder.encode(student.getPassword()));
        studentToAdd.setBirth(student.getBirth());
        studentToAdd.setAddress(userAddress);
        studentToAdd.setRole(Role.USER);

        studentRepository.save(studentToAdd);

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
