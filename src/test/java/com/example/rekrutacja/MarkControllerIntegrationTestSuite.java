package com.example.rekrutacja;

import com.example.rekrutacja.domain.Mark;
import com.example.rekrutacja.dto.MarkDto;
import com.example.rekrutacja.exception.MarkNotFoundException;
import com.example.rekrutacja.service.MarkService;
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
public class MarkControllerIntegrationTestSuite {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MarkService markService;

    @BeforeEach
    public void setup() {
        Mark mark1 = new Mark(1L, "Java", 4);
        Mark mark2 = new Mark(2L, "Java", 3);
        markService.saveMark(mark1);
        markService.saveMark(mark2);
    }

    @Test
    public void shouldGetMarks() throws Exception {

        mockMvc.perform(get("/v1/marks"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].markId").value(1))
                .andExpect(jsonPath("$[0].subject").value("Java"))
                .andExpect(jsonPath("$[0].markValue").value(4))
                .andExpect(jsonPath("$[1].markId").value(2))
                .andExpect(jsonPath("$[1].subject").value("Java"))
                .andExpect(jsonPath("$[1].markValue").value(3));
    }

    @Test
    public void shouldGetMarkById() throws Exception {
        // Given
        Long markId = 1L;

        // When&Then
        mockMvc.perform(get("/v1/marks/{markId}", markId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.markId").value(1))
                .andExpect(jsonPath("$.subject").value("Java"))
                .andExpect(jsonPath("$.markValue").value("4"));
    }

    @Test
    public void shouldGetMarkById_WhenMarkNotFound() throws Exception {
        // Given
        Long nonExistentMarkId = 10L;

        // When&Then
        mockMvc.perform(get("/v1/marks/{markId}", nonExistentMarkId))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void shouldCreateMark() throws Exception {
        // Given
        MarkDto newMarkDto = new MarkDto(3L, "C++", 5);

        // When&then
        mockMvc.perform(post("/v1/marks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(newMarkDto)))
                .andExpect(status().isOk());

        List<Mark> allMarks = markService.getAllMarks();
        assertEquals(3, allMarks.size());
        Mark addedMark = allMarks.get(2);
        assertEquals("C++", addedMark.getSubject());
        assertEquals(5, addedMark.getMarkValue());
    }

    @Test
    public void shouldUpdateMark() throws Exception, MarkNotFoundException {
        // Given
        MarkDto updatedMarkDto = new MarkDto(1L, "C++", 2);

        // When&Then
        mockMvc.perform(put("/v1/marks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedMarkDto)))
                .andExpect(status().isOk());

        Mark updatedMark = markService.getMark(1L);
        assertEquals("C++", updatedMark.getSubject());
        assertEquals(2, updatedMark.getMarkValue());
    }

    @Test
    public void shouldDeleteMark() throws Exception {

        mockMvc.perform(delete("/v1/marks/{markId}", 2L))
                .andExpect(status().isOk());

        List<Mark> allMarks = markService.getAllMarks();
        assertEquals(1, allMarks.size());
        assertFalse(allMarks.stream().anyMatch(marks -> marks.getMarkId().equals(2L)));
    }

    private String asJsonString(Object object) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }
}