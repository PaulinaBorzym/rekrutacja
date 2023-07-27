package com.example.rekrutacja;

import com.example.rekrutacja.domain.Student;
import com.example.rekrutacja.dto.StudentDto;
import com.example.rekrutacja.mapper.StudentMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class StudentMapperTestSuite {
    private StudentMapper studentMapper = new StudentMapper();

    @Test
    public void shouldMapStudentDtoToStudent() {
        // Given
        StudentDto studentDto = new StudentDto(1L, "Jan", "Pan", "jan@Pan");

        // When
        Student student = studentMapper.mapToStudent(studentDto);

        // Then
        assertEquals(studentDto.getStudentId(), student.getStudentId());
        assertEquals(studentDto.getFirstName(), student.getFirstName());
        assertEquals(studentDto.getLastName(), student.getLastName());
        assertEquals(studentDto.getEmail(), student.getEmail());
    }

    @Test
    public void shouldMapStudentToStudentDto() {
        // Given
        Student student = new Student(1L, "Jan", "Pan", "jan@pan");

        // When
        StudentDto studentDto = studentMapper.mapToStudentDto(student);

        // Then
        assertEquals(student.getStudentId(), studentDto.getStudentId());
        assertEquals(student.getFirstName(), studentDto.getFirstName());
        assertEquals(student.getLastName(), studentDto.getLastName());
        assertEquals(student.getEmail(), studentDto.getEmail());
    }

    @Test
    public void shouldMapListOfStudentsToListOfStudentsDto() {
        // Given
        List<Student> studentsList = Arrays.asList(
                new Student(1L, "Jan", "Pan", "jan@pan"),
                new Student(2L, "Adam", "Kot", "adam@kot"));

        // When
        List<StudentDto> studentsDtoList = studentMapper.mapToStudentDtoList(studentsList);

        // Then
        assertEquals(studentsList.size(), studentsDtoList.size());
        for (int i = 0; i < studentsList.size(); i++) {
            Student student = studentsList.get(i);
            StudentDto studentDto = studentsDtoList.get(i);

            assertEquals(student.getStudentId(), studentDto.getStudentId());
            assertEquals(student.getFirstName(), studentDto.getFirstName());
            assertEquals(student.getLastName(), studentDto.getLastName());
            assertEquals(student.getEmail(), studentDto.getEmail());
        }
    }
}