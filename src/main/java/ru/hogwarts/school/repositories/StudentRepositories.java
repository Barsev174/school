package ru.hogwarts.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface StudentRepositories extends JpaRepository<Student, Long> {
    public Collection<Student> findByAge(int age);
}
