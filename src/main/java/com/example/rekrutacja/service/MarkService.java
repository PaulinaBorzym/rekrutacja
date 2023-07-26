package com.example.rekrutacja.service;

import com.example.rekrutacja.domain.Mark;
import com.example.rekrutacja.domain.Student;
import com.example.rekrutacja.exception.MarkNotFoundException;
import com.example.rekrutacja.exception.StudentNotFoundException;
import com.example.rekrutacja.repository.MarkRepository;
import com.example.rekrutacja.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarkService {

    @Autowired
    MarkRepository repository;

    public MarkService(MarkRepository repository) {
        this.repository = repository;
    }

    public List<Mark> getAllMarks() {
        return repository.findAll();
    }

    public Mark getMark(final Long markId) throws MarkNotFoundException {
        return repository.findById(markId).orElseThrow(MarkNotFoundException::new);
    }

    public Mark saveMark(final Mark mark) {
        return repository.save(mark);
    }

    public void deleteMark(final Long markId) throws MarkNotFoundException {
        if (repository.findById(markId).isPresent()) {
            repository.deleteById(markId);
        } else {
            throw new MarkNotFoundException();
        }
    }
}
