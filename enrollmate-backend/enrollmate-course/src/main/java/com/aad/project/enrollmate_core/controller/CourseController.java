package com.aad.project.enrollmate_core.controller;

import com.aad.project.enrollmate_core.data.Course;
import com.aad.project.enrollmate_core.repo.CourseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseRepo courseAccess;

    // Create a new course
    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        Course createdCourse = courseAccess.save(course);
        return new ResponseEntity<>(createdCourse, HttpStatus.CREATED);
    }

    // Get all courses
    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courses = courseAccess.findAll();
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    // Get course by ID
    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        Optional<Course> course = courseAccess.findById(id);
        if (course.isPresent()) {
            return new ResponseEntity<>(course.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Update course
    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable Long id, @RequestBody Course course) {
        if (courseAccess.existsById(id)) {
            course.setId(id);
            Course updatedCourse = courseAccess.save(course);
            return new ResponseEntity<>(updatedCourse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete course
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        if (courseAccess.existsById(id)) {
            courseAccess.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
