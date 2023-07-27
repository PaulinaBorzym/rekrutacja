package com.example.rekrutacja.controller;

import com.example.rekrutacja.domain.Lecturer;
import com.example.rekrutacja.dto.LecturerDto;
import com.example.rekrutacja.exception.LecturerNotFoundException;
import com.example.rekrutacja.mapper.LecturerMapper;
import com.example.rekrutacja.service.LecturerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/lecturers")
public class LecturerController {
    private final LecturerService service;
    private final LecturerMapper lecturerMapper;

    @GetMapping
    public ResponseEntity<List<LecturerDto>> getLecturers() {
        List<Lecturer> lecturers = service.getAllLecturers();
        return ResponseEntity.ok(lecturerMapper.mapToLecturerDtoList(lecturers));
    }

    @GetMapping(value = "{lecturerId}")
    public ResponseEntity<LecturerDto> getLecturer(@PathVariable Long lecturerId) {
        try {
            return ResponseEntity.ok(lecturerMapper.mapToLecturerDto(service.getLecturer(lecturerId)));
        } catch (LecturerNotFoundException exception) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createLecturer(@RequestBody LecturerDto lecturerDto) {
        Lecturer lecturer = lecturerMapper.mapToLecturer(lecturerDto);
        service.saveLecturer(lecturer);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<LecturerDto> updateLecturer(@RequestBody LecturerDto lecturerDto) {
        Lecturer lecturer = lecturerMapper.mapToLecturer(lecturerDto);
        Lecturer savedLecturer = service.saveLecturer(lecturer);
        return ResponseEntity.ok(lecturerMapper.mapToLecturerDto(savedLecturer));
    }

    @DeleteMapping(value = "{lecturerId}")
    public ResponseEntity<Void> deleteLecturer(@PathVariable Long lecturerId) throws LecturerNotFoundException {
        service.deleteLecturer(lecturerId);
        return ResponseEntity.ok().build();
    }
}