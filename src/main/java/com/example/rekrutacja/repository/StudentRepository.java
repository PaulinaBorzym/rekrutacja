package com.example.rekrutacja.repository;

import com.example.rekrutacja.domain.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface StudentRepository extends CrudRepository<Student, Long> {
    @Override
    List<Student> findAll();

    @Override
    Optional<Student> findById(Long studentId);

    @Override
    Student save(Student student);

    @Override
    void deleteById(Long studentId);
}