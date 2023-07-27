package com.example.rekrutacja.service;

import com.example.rekrutacja.domain.Student;
import com.example.rekrutacja.exception.StudentNotFoundException;
import com.example.rekrutacja.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public List<Student> getAllStudents() {
        return repository.findAll();
    }

    public Student getStudent(final Long studentId) throws StudentNotFoundException {
        return repository.findById(studentId).orElseThrow(StudentNotFoundException::new);
    }

    public Student saveStudent(final Student student) {
        return repository.save(student);
    }

    public void deleteStudent(final Long studentId) throws StudentNotFoundException {
        if (repository.findById(studentId).isPresent()) {
            repository.deleteById(studentId);
        } else {
            throw new StudentNotFoundException();
        }
    }
}