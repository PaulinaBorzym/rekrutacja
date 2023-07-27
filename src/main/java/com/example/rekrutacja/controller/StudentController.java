package com.example.rekrutacja.controller;

import com.example.rekrutacja.domain.Student;
import com.example.rekrutacja.dto.StudentDto;
import com.example.rekrutacja.exception.StudentNotFoundException;
import com.example.rekrutacja.mapper.StudentMapper;
import com.example.rekrutacja.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/students")
public class StudentController {

    private final StudentService service;
    private final StudentMapper studentMapper;

    @GetMapping
    public ResponseEntity<List<StudentDto>> getStudents() {
        List<Student> students = service.getAllStudents();
        return ResponseEntity.ok(studentMapper.mapToStudentDtoList(students));
    }

    @GetMapping(value = "{studentId}")
    public ResponseEntity<StudentDto> getStudent(@PathVariable Long studentId) {
        try {
            return ResponseEntity.ok(studentMapper.mapToStudentDto(service.getStudent(studentId)));
        } catch (StudentNotFoundException exception) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createStudent(@RequestBody StudentDto studentDto) {
        Student student = studentMapper.mapToStudent(studentDto);
        service.saveStudent(student);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<StudentDto> updateStudent(@RequestBody StudentDto studentDto) {
        Student student = studentMapper.mapToStudent(studentDto);
        Student savedStudent = service.saveStudent(student);
        return ResponseEntity.ok(studentMapper.mapToStudentDto(savedStudent));
    }

    @DeleteMapping(value = "{studentId}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long studentId) throws StudentNotFoundException {
        service.deleteStudent(studentId);
        return ResponseEntity.ok().build();
    }
}
