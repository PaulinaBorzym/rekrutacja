package com.example.rekrutacja;
import com.example.rekrutacja.domain.Lecturer;
import com.example.rekrutacja.dto.LecturerDto;
import com.example.rekrutacja.exception.LecturerNotFoundException;
import com.example.rekrutacja.service.LecturerService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class LecturerControllerIntegrationTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LecturerService lecturerService;

    @BeforeEach
    public void setup() {
        Lecturer lecturer1 = new Lecturer(1L,"Adam","Kot","adam@kot","Java");
        Lecturer lecturer2 = new Lecturer(2L,"Artur","Smok","artur@smok","Python");
        lecturerService.saveLecturer(lecturer1);
        lecturerService.saveLecturer(lecturer2);
    }

    @Test
    public void shouldGetLecturers() throws Exception {

        mockMvc.perform(get("/v1/lecturers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].lecturerId").value(1))
                .andExpect(jsonPath("$[0].firstName").value("Adam"))
                .andExpect(jsonPath("$[0].lastName").value("Kot"))
                .andExpect(jsonPath("$[0].email").value("adam@kot"))
                .andExpect(jsonPath("$[0].subject").value("Java"))
                .andExpect(jsonPath("$[1].lecturerId").value(2))
                .andExpect(jsonPath("$[1].firstName").value("Artur"))
                .andExpect(jsonPath("$[1].lastName").value("Smok"))
                .andExpect(jsonPath("$[1].email").value("artur@smok"))
                .andExpect(jsonPath("$[1].subject").value("Python"));
    }

    @Test
    public void shouldGetLecturerById() throws Exception {
        // Given
        Long lecturerId = 1L;

        // When&Then
        mockMvc.perform(get("/v1/lecturers/{lecturerId}", lecturerId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.lecturerId").value(1))
                .andExpect(jsonPath("$.firstName").value("Adam"))
                .andExpect(jsonPath("$.lastName").value("Kot"))
                .andExpect(jsonPath("$.email").value("adam@kot"))
                .andExpect(jsonPath("$.subject").value("Java"));
    }

    @Test
    public void shouldGetLecturerById_WhenLecturerNotFound() throws Exception {
        // Given
        Long nonExistentLecturerId = 10L;

        // When&Then
        mockMvc.perform(get("/v1/lecturers/{lecturerId}", nonExistentLecturerId))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void shouldCreateLecturer() throws Exception {
        // Given
        LecturerDto newLecturerDto = new LecturerDto(3L, "Marek", "Skok","marek@skok","C++");

        // When&then
        mockMvc.perform(post("/v1/lecturers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(newLecturerDto)))
                .andExpect(status().isOk());

        List<Lecturer> allLecturers = lecturerService.getAllLecturers();
        assertEquals(3, allLecturers.size());
        Lecturer addedLecturer = allLecturers.get(2);
        assertEquals("Marek", addedLecturer.getFirstName());
        assertEquals("Skok", addedLecturer.getLastName());
        assertEquals("marek@skok",addedLecturer.getEmail());
        assertEquals("C++",addedLecturer.getSubject());
    }

    @Test
    public void shouldUpdateLecturer() throws Exception, LecturerNotFoundException {
        // Given
        LecturerDto updatedLecturerDto = new LecturerDto(1L, "Janusz", "Pies","janusz@pies","Java");

        // When&Then
        mockMvc.perform(put("/v1/lecturers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedLecturerDto)))
                .andExpect(status().isOk());

        Lecturer updatedLecturer = lecturerService.getLecturer(1L);
        assertEquals("Janusz", updatedLecturer.getFirstName());
        assertEquals("Pies", updatedLecturer.getLastName());
        assertEquals("janusz@pies",updatedLecturer.getEmail());
        assertEquals("Java",updatedLecturer.getSubject());
    }

    @Test
    public void shouldDeleteLecturer() throws Exception {

        // Given&When&Then
        mockMvc.perform(delete("/v1/lecturers/{lecturerId}", 2L))
                .andExpect(status().isOk());

        List<Lecturer> allLecturers = lecturerService.getAllLecturers();
        assertEquals(1, allLecturers.size());
        assertFalse(allLecturers.stream().anyMatch(lecturer -> lecturer.getLecturerId().equals(2L)));
    }

    private String asJsonString(Object object) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }
}
