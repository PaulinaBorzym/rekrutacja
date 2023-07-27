package com.example.rekrutacja.service;

import com.example.rekrutacja.domain.Lecturer;
import com.example.rekrutacja.exception.LecturerNotFoundException;
import com.example.rekrutacja.repository.LecturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LecturerService {
    @Autowired
    LecturerRepository repository;

    public LecturerService(LecturerRepository repository) {
        this.repository = repository;
    }

    public List<Lecturer> getAllLecturers() {
        return repository.findAll();
    }

    public Lecturer getLecturer(final Long lecturerId) throws LecturerNotFoundException {
        return repository.findById(lecturerId).orElseThrow(LecturerNotFoundException::new);
    }

    public Lecturer saveLecturer(final Lecturer lecturer) {
        return repository.save(lecturer);
    }

    public void deleteLecturer(final Long lecturerId) throws LecturerNotFoundException {
        if (repository.findById(lecturerId).isPresent()) {
            repository.deleteById(lecturerId);
        } else {
            throw new LecturerNotFoundException();
        }
    }
}