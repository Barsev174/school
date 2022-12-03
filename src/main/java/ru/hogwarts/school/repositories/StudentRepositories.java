package ru.hogwarts.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface StudentRepositories extends JpaRepository<Student, Long> {
    Collection<Student>  findByAge(int age);

    Collection<Student> findByAgeBetween(int ageMin, int ageMax);

    Collection<Student> findByFacultyId(Long id);
}

