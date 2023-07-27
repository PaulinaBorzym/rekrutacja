package com.example.rekrutacja.controller;

import com.example.rekrutacja.domain.Mark;
import com.example.rekrutacja.dto.MarkDto;
import com.example.rekrutacja.exception.MarkNotFoundException;
import com.example.rekrutacja.mapper.MarkMapper;
import com.example.rekrutacja.service.MarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/marks")
public class MarkController {
    private final MarkService service;
    private final MarkMapper markMapper;

    @GetMapping
    public ResponseEntity<List<MarkDto>> getMarks() {
        List<Mark> marks = service.getAllMarks();
        return ResponseEntity.ok(markMapper.mapToMarkDtoList(marks));
    }

    @GetMapping(value = "{markId}")
    public ResponseEntity<MarkDto> getMark(@PathVariable Long markId) {
        try {
            return ResponseEntity.ok(markMapper.mapToMarkDto(service.getMark(markId)));
        } catch (MarkNotFoundException exception) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createMark(@RequestBody MarkDto markDto) {
        Mark mark = markMapper.mapToMark(markDto);
        service.saveMark(mark);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<MarkDto> updateMark(@RequestBody MarkDto markDto) {
        Mark mark = markMapper.mapToMark(markDto);
        Mark savedMark = service.saveMark(mark);
        return ResponseEntity.ok(markMapper.mapToMarkDto(savedMark));
    }

    @DeleteMapping(value = "{markId}")
    public ResponseEntity<Void> deleteMark(@PathVariable Long markId) throws MarkNotFoundException {
        service.deleteMark(markId);
        return ResponseEntity.ok().build();
    }
}