package com.aad.project.enrollmate_core.repo;

import com.aad.project.enrollmate_core.data.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepo extends JpaRepository<Student, Long> {
    List<Student> findAllByDepartment(String department);
}
