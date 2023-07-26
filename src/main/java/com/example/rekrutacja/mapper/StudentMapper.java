package com.example.rekrutacja.mapper;

import com.example.rekrutacja.domain.Lecturer;
import com.example.rekrutacja.domain.Student;
import com.example.rekrutacja.dto.LecturerDto;
import com.example.rekrutacja.dto.StudentDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentMapper {

    public Student mapToStudent(final StudentDto studentDto) {
        return new Student(
                studentDto.getStudentId(),
                studentDto.getFirstName(),
                studentDto.getLastName(),
                studentDto.getEmail()
        );
    }

    public StudentDto mapToStudentDto(final Student student) {
        return new StudentDto(
                student.getStudentId(),
                student.getFirstName(),
                student.getLastName(),
                student.getEmail()
        );
    }

    public List<StudentDto> mapToStudentDtoList(final List<Student> studentsList) {
        return studentsList.stream()
                .map(this::mapToStudentDto)
                .collect(Collectors.toList());
    }
}
