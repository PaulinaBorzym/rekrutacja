package com.example.rekrutacja.controller;

import com.example.rekrutacja.domain.Student;
import com.example.rekrutacja.dto.StudentDto;
import com.example.rekrutacja.exception.StudentNotFoundException;
import com.example.rekrutacja.mapper.StudentMapper;
import com.example.rekrutacja.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Student controller")
@RequiredArgsConstructor
@RequestMapping("v1/students")
public class StudentController {
    private final StudentService service;
    private final StudentMapper studentMapper;

    @Operation(summary = "get list of all students")
    @GetMapping
    public ResponseEntity<List<StudentDto>> getStudents() {
        List<Student> students = service.getAllStudents();
        return ResponseEntity.ok(studentMapper.mapToStudentDtoList(students));
    }

    @Operation(summary = "get student by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "found the student",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Student.class))}),
            @ApiResponse(responseCode = "500", description = "invalid Id")
    })
    @GetMapping(value = "{studentId}")
    public ResponseEntity<StudentDto> getStudent(@Parameter(description = "Id of student")
                                                 @PathVariable Long studentId) {
        try {
            return ResponseEntity.ok(studentMapper.mapToStudentDto(service.getStudent(studentId)));
        } catch (StudentNotFoundException exception) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "create student")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createStudent(@Parameter(description = "data about new student")
                                              @RequestBody StudentDto studentDto) {
        Student student = studentMapper.mapToStudent(studentDto);
        service.saveStudent(student);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "update student")
    @PutMapping
    public ResponseEntity<StudentDto> updateStudent(@Parameter(description = "change data about student")
                                                    @RequestBody StudentDto studentDto) {
        Student student = studentMapper.mapToStudent(studentDto);
        Student savedStudent = service.saveStudent(student);
        return ResponseEntity.ok(studentMapper.mapToStudentDto(savedStudent));
    }

    @Operation(summary = "delete student")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "deleted the student",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Student.class))}),
            @ApiResponse(responseCode = "500", description = "invalid Id")
    })
    @DeleteMapping(value = "{studentId}")
    public ResponseEntity<Void> deleteStudent(@Parameter(description = "student Id to delete")
                                              @PathVariable Long studentId) {
        try {
            service.deleteStudent(studentId);
            return ResponseEntity.ok().build();
        } catch (StudentNotFoundException exception) {
            return ResponseEntity.internalServerError().build();
        }
    }
}