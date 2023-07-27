package com.example.rekrutacja;

import com.example.rekrutacja.domain.Student;
import com.example.rekrutacja.dto.StudentDto;
import com.example.rekrutacja.exception.StudentNotFoundException;
import com.example.rekrutacja.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class StudentControllerIntegrationTestSuite {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private StudentService studentService;

    @BeforeEach
    public void setup() {
        Student student1 = new Student(1L, "Ania", "Kania", "ania@kania");
        Student student2 = new Student(2L, "Hania", "Bania", "hania@bania");
        studentService.saveStudent(student1);
        studentService.saveStudent(student2);
    }

    @Test
    public void shouldGetStudents() throws Exception {

        mockMvc.perform(get("/v1/students"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].studentId").value(1))
                .andExpect(jsonPath("$[0].firstName").value("Ania"))
                .andExpect(jsonPath("$[0].lastName").value("Kania"))
                .andExpect(jsonPath("$[0].email").value("ania@kania"))
                .andExpect(jsonPath("$[1].studentId").value(2))
                .andExpect(jsonPath("$[1].firstName").value("Hania"))
                .andExpect(jsonPath("$[1].lastName").value("Bania"))
                .andExpect(jsonPath("$[1].email").value("hania@bania"));
    }

    @Test
    public void shouldGetStudentById() throws Exception {
        // Given
        Long studentId = 1L;

        // When&Then
        mockMvc.perform(get("/v1/students/{studentId}", studentId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.studentId").value(1))
                .andExpect(jsonPath("$.firstName").value("Ania"))
                .andExpect(jsonPath("$.lastName").value("Kania"))
                .andExpect(jsonPath("$.email").value("ania@kania"));
    }

    @Test
    public void shouldGetStudentById_WhenStudentNotFound() throws Exception {
        // Given
        Long nonExistentStudentId = 10L;

        // When&Then
        mockMvc.perform(get("/v1/students/{studentId}", nonExistentStudentId))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void shouldCreateStudent() throws Exception {
        // Given
        StudentDto newStudentDto = new StudentDto(3L, "Marek", "Skok", "marek@skok");

        // When&then
        mockMvc.perform(post("/v1/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(newStudentDto)))
                .andExpect(status().isOk());

        List<Student> allStudents = studentService.getAllStudents();
        assertEquals(3, allStudents.size());
        Student addedStudent = allStudents.get(2);
        assertEquals("Marek", addedStudent.getFirstName());
        assertEquals("Skok", addedStudent.getLastName());
        assertEquals("marek@skok", addedStudent.getEmail());
    }

    @Test
    public void shouldUpdateStudent() throws Exception, StudentNotFoundException {
        // Given
        StudentDto updatedStudentDto = new StudentDto(1L, "Janusz", "Pies", "janusz@pies");

        // When&Then
        mockMvc.perform(put("/v1/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedStudentDto)))
                .andExpect(status().isOk());

        Student updatedStudent = studentService.getStudent(1L);
        assertEquals("Janusz", updatedStudent.getFirstName());
        assertEquals("Pies", updatedStudent.getLastName());
        assertEquals("janusz@pies", updatedStudent.getEmail());
    }

    @Test
    public void shouldDeleteStudent() throws Exception {

        // Given&When&Then
        mockMvc.perform(delete("/v1/students/{studentId}", 2L))
                .andExpect(status().isOk());

        List<Student> allStudents = studentService.getAllStudents();
        assertEquals(1, allStudents.size());
        assertFalse(allStudents.stream().anyMatch(students -> students.getStudentId().equals(2L)));
    }

    private String asJsonString(Object object) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }
}