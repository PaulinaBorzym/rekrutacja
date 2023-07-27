package com.example.rekrutacja;

import com.example.rekrutacja.domain.Lecturer;
import com.example.rekrutacja.dto.LecturerDto;
import com.example.rekrutacja.mapper.LecturerMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class LecturerMapperTestSuite {
    private LecturerMapper lecturerMapper = new LecturerMapper();

    @Test
    public void shouldMapLecturerDtoToLecturer() {
        // Given
        LecturerDto lecturerDto = new LecturerDto(1L, "Jan", "Pan", "jan@pan",
                "Java");

        // When
        Lecturer lecturer = lecturerMapper.mapToLecturer(lecturerDto);

        // Then
        assertEquals(lecturerDto.getLecturerId(), lecturer.getLecturerId());
        assertEquals(lecturerDto.getFirstName(), lecturer.getFirstName());
        assertEquals(lecturerDto.getLastName(), lecturer.getLastName());
        assertEquals(lecturerDto.getEmail(), lecturer.getEmail());
        assertEquals(lecturerDto.getSubject(), lecturer.getSubject());
    }

    @Test
    public void shouldMapLecturerToLecturerDto() {
        // Given
        Lecturer lecturer = new Lecturer(1L, "Jan", "Pan", "jan@pan",
                "Java");

        // When
        LecturerDto lecturerDto = lecturerMapper.mapToLecturerDto(lecturer);

        // Then
        assertEquals(lecturer.getLecturerId(), lecturerDto.getLecturerId());
        assertEquals(lecturer.getFirstName(), lecturerDto.getFirstName());
        assertEquals(lecturer.getLastName(), lecturerDto.getLastName());
        assertEquals(lecturer.getEmail(), lecturerDto.getEmail());
        assertEquals(lecturer.getSubject(), lecturerDto.getSubject());
    }

    @Test
    public void shouldMapListOfLecturersToListOfLecturersDto() {
        // Given
        List<Lecturer> lecturersList = Arrays.asList(
                new Lecturer(1L, "Ania", "Kania", "kania@ania", "Java"),
                new Lecturer(2L, "Hania", "Bania", "bania@hania", "C++"));

        // When
        List<LecturerDto> lecturersDtoList = lecturerMapper.mapToLecturerDtoList(lecturersList);

        // Then
        assertEquals(lecturersList.size(), lecturersDtoList.size());
        for (int i = 0; i < lecturersList.size(); i++) {
            Lecturer lecturer = lecturersList.get(i);
            LecturerDto lecturerDto = lecturersDtoList.get(i);

            assertEquals(lecturer.getLecturerId(), lecturerDto.getLecturerId());
            assertEquals(lecturer.getFirstName(), lecturerDto.getFirstName());
            assertEquals(lecturer.getLastName(), lecturerDto.getLastName());
            assertEquals(lecturer.getEmail(), lecturerDto.getEmail());
            assertEquals(lecturer.getSubject(), lecturerDto.getSubject());
        }
    }
}