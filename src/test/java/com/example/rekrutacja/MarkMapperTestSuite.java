package com.example.rekrutacja;

import com.example.rekrutacja.domain.Mark;
import com.example.rekrutacja.dto.MarkDto;
import com.example.rekrutacja.mapper.MarkMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class MarkMapperTestSuite {
    MarkMapper markMapper = new MarkMapper();

    @Test
    public void shouldMapMarkDtoToMark() {
        // Given
        MarkDto markDto = new MarkDto(1L, "Java", 3);

        // When
        Mark mark = markMapper.mapToMark(markDto);

        // Then
        assertEquals(markDto.getMarkId(), mark.getMarkId());
        assertEquals(markDto.getSubject(), mark.getSubject());
        assertEquals(markDto.getMarkValue(), mark.getMarkValue());
    }

    @Test
    public void shouldMapMarkToMarkDto() {
        // Given
        Mark mark = new Mark(1L, "Java", 4);

        // When
        MarkDto markDto = markMapper.mapToMarkDto(mark);

        // Then
        assertEquals(mark.getMarkId(), markDto.getMarkId());
        assertEquals(mark.getSubject(), markDto.getSubject());
        assertEquals(mark.getMarkValue(), markDto.getMarkValue());
    }

    @Test
    public void shouldMapListOfMarksToListOfMarksDto() {
        // Given
        List<Mark> marksList = Arrays.asList(
                new Mark(1L, "Java", 4),
                new Mark(2L, "C++", 5));

        // When
        List<MarkDto> marksDtoList = markMapper.mapToMarkDtoList(marksList);

        // Then
        assertEquals(marksList.size(), marksDtoList.size());
        for (int i = 0; i < marksList.size(); i++) {
            Mark mark = marksList.get(i);
            MarkDto markDto = marksDtoList.get(i);

            assertEquals(mark.getMarkId(), markDto.getMarkId());
            assertEquals(mark.getSubject(), markDto.getSubject());
            assertEquals(mark.getMarkValue(), markDto.getMarkValue());
        }
    }
}