package com.example.rekrutacja.controller;

import com.example.rekrutacja.domain.Mark;
import com.example.rekrutacja.dto.MarkDto;
import com.example.rekrutacja.exception.MarkNotFoundException;
import com.example.rekrutacja.mapper.MarkMapper;
import com.example.rekrutacja.service.MarkService;
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
@Tag(name = "Mark controller")
@RequiredArgsConstructor
@RequestMapping("v1/marks")
public class MarkController {
    private final MarkService service;
    private final MarkMapper markMapper;

    @Operation(summary = "get list of all marks")
    @GetMapping
    public ResponseEntity<List<MarkDto>> getMarks() {
        List<Mark> marks = service.getAllMarks();
        return ResponseEntity.ok(markMapper.mapToMarkDtoList(marks));
    }

    @Operation(summary = "get mark by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "found the mark",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Mark.class))}),
            @ApiResponse(responseCode = "500", description = "invalid Id")
    })
    @GetMapping(value = "{markId}")
    public ResponseEntity<MarkDto> getMark(@Parameter(description = "Id of mark") @PathVariable Long markId) {
        try {
            return ResponseEntity.ok(markMapper.mapToMarkDto(service.getMark(markId)));
        } catch (MarkNotFoundException exception) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "create mark")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createMark(@Parameter(description = "data about new mark")
                                           @RequestBody MarkDto markDto) {
        Mark mark = markMapper.mapToMark(markDto);
        service.saveMark(mark);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "update mark")
    @PutMapping
    public ResponseEntity<MarkDto> updateMark(@Parameter(description = "change data about mark")
                                              @RequestBody MarkDto markDto) {
        Mark mark = markMapper.mapToMark(markDto);
        Mark savedMark = service.saveMark(mark);
        return ResponseEntity.ok(markMapper.mapToMarkDto(savedMark));
    }

    @Operation(summary = "delete mark")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "deleted the mark",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Mark.class))}),
            @ApiResponse(responseCode = "500", description = "invalid Id")
    })
    @DeleteMapping(value = "{markId}")
    public ResponseEntity<Void> deleteMark(@Parameter(description = "mark Id to delete")
                                           @PathVariable Long markId) {
        try {
            service.deleteMark(markId);
            return ResponseEntity.ok().build();
        } catch (MarkNotFoundException exception) {
            return ResponseEntity.internalServerError().build();
        }
    }
}