package com.aad.project.enrollmate_core;

import com.aad.project.enrollmate_core.data.Course;
import com.aad.project.enrollmate_core.data.Student;
import com.aad.project.enrollmate_core.repo.CourseRepo;
import com.aad.project.enrollmate_core.repo.StudentRepo;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@SpringBootApplication
public class EnrollmateDataGen implements CommandLineRunner {

    @Autowired
    private StudentRepo studentAccess;

    @Autowired
    private CourseRepo courseAccess;

    public static void main(String[] args) {
        SpringApplication.run(EnrollmateDataGen.class, args);
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        studentAccess.deleteAll();
        courseAccess.deleteAll();
        generateTestData();
        generateTestData(studentAccess.findAll());
    }

    private void generateTestData() {
        Faker faker = new Faker();
        for (int i = 0; i < 10; i++) {
            Student student = new Student();
            student.setName(faker.name().fullName());
            student.setEmail(faker.internet().emailAddress());
            student.setDepartment(faker.company().industry());
            studentAccess.save(student);
        }
    }

    private void generateTestData(List<Student> students) {
        Faker faker = new Faker();
        for (int i = 0; i < 5; i++) {
            Course course = new Course();
            course.setName(faker.lorem().word() + " " + faker.lorem().word());
            course.setDescription(faker.lorem().sentence());
            course.setInstructor(faker.name().fullName());
            course.setMaxStudents(faker.number().numberBetween(20, 50));
            courseAccess.save(course);

            // Randomly enroll students in the course
            int numberOfStudents = faker.number().numberBetween(5, 15);  // Enroll 5 to 15 students
            for (int j = 0; j < numberOfStudents; j++) {
                Student randomStudent = getRandomStudent(students);
                if (randomStudent != null) {
                    randomStudent.getCourses().add(course);
                    studentAccess.save(randomStudent); // Save student with updated courses
                }
            }
        }
    }

    private Student getRandomStudent(List<Student> students) {
        if (students == null || students.isEmpty()) {
            return null;
        }
        Random random = new Random();
        int randomIndex = random.nextInt(students.size());
        return students.get(randomIndex);
    }
}

