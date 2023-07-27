package com.example.rekrutacja.controller;

import com.example.rekrutacja.domain.Lecturer;
import com.example.rekrutacja.dto.LecturerDto;
import com.example.rekrutacja.exception.LecturerNotFoundException;
import com.example.rekrutacja.mapper.LecturerMapper;
import com.example.rekrutacja.service.LecturerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Lecturer controller")
@RequiredArgsConstructor
@RequestMapping("v1/lecturers")
public class LecturerController {
    private final LecturerService service;
    private final LecturerMapper lecturerMapper;

    @Operation(summary = "get list of all lecturers")
    @GetMapping
    public ResponseEntity<List<LecturerDto>> getLecturers() {
        List<Lecturer> lecturers = service.getAllLecturers();
        return ResponseEntity.ok(lecturerMapper.mapToLecturerDtoList(lecturers));
    }

    @Operation(summary = "get lecturer by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "found the lecturer",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Lecturer.class))}),
            @ApiResponse(responseCode = "500", description = "invalid Id")
    })
    @GetMapping(value = "{lecturerId}")
    public ResponseEntity<LecturerDto> getLecturer(@Parameter(description = "Id of lecturer") @PathVariable Long lecturerId) {
        try {
            return ResponseEntity.ok(lecturerMapper.mapToLecturerDto(service.getLecturer(lecturerId)));
        } catch (LecturerNotFoundException exception) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "create lecturer")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createLecturer(@Parameter(description = "data about new lecturer")
                                               @RequestBody LecturerDto lecturerDto) {
        Lecturer lecturer = lecturerMapper.mapToLecturer(lecturerDto);
        service.saveLecturer(lecturer);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "update lecturer")
    @PutMapping
    public ResponseEntity<LecturerDto> updateLecturer(@Parameter(description = "change data about lecturer")
                                                      @RequestBody LecturerDto lecturerDto) {
        Lecturer lecturer = lecturerMapper.mapToLecturer(lecturerDto);
        Lecturer savedLecturer = service.saveLecturer(lecturer);
        return ResponseEntity.ok(lecturerMapper.mapToLecturerDto(savedLecturer));
    }

    @Operation(summary = "delete lecturer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "deleted the lecturer",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Lecturer.class))}),
            @ApiResponse(responseCode = "500", description = "invalid Id")
    })
    @DeleteMapping(value = "{lecturerId}")
    public ResponseEntity<Void> deleteLecturer(@Parameter(description = "Lecturer Id to delete")
                                               @PathVariable Long lecturerId) {
        try {
            service.deleteLecturer(lecturerId);
            return ResponseEntity.ok().build();
        } catch (LecturerNotFoundException exception) {
            return ResponseEntity.internalServerError().build();
        }
    }
}