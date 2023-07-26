package com.example.rekrutacja.mapper;

import com.example.rekrutacja.domain.Lecturer;
import com.example.rekrutacja.domain.Mark;
import com.example.rekrutacja.dto.LecturerDto;
import com.example.rekrutacja.dto.MarkDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MarkMapper {

    public Mark mapToMark(final MarkDto markDto) {
        return new Mark(
                markDto.getMarkId(),
                markDto.getSubject(),
                markDto.getMarkValue()
        );
    }

    public MarkDto mapToMarkDto(final Mark mark) {
        return new MarkDto(
                mark.getMarkId(),
                mark.getSubject(),
                mark.getMarkValue()
        );
    }

    public List<MarkDto> mapToMarkDtoList(final List<Mark> marksList) {
        return marksList.stream()
                .map(this::mapToMarkDto)
                .collect(Collectors.toList());
    }
}
