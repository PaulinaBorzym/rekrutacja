package com.example.rekrutacja.mapper;

import com.example.rekrutacja.domain.Lecturer;
import com.example.rekrutacja.dto.LecturerDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LecturerMapper {

    public Lecturer mapToLecturer(final LecturerDto lecturerDto) {
        return new Lecturer(
                lecturerDto.getLecturerId(),
                lecturerDto.getFirstName(),
                lecturerDto.getLastName(),
                lecturerDto.getEmail(),
                lecturerDto.getSubject()
        );
    }

    public LecturerDto mapToLecturerDto(final Lecturer lecturer) {
        return new LecturerDto(
                lecturer.getLecturerId(),
                lecturer.getFirstName(),
                lecturer.getLastName(),
                lecturer.getEmail(),
                lecturer.getSubject()
        );
    }

    public List<LecturerDto> mapToLecturerDtoList(final List<Lecturer> lecturersList) {
        return lecturersList.stream()
                .map(this::mapToLecturerDto)
                .collect(Collectors.toList());
    }
}