package com.kiet.student.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kiet.student.model.Student;

@RestController
@RequestMapping("/students")
public class StudentController {

    private List<Student> studentList = new ArrayList<>();

    // Constructor: 5 students pehle se list mein add karne ke liye
    public StudentController() {
        studentList.add(new Student(101, "Rahul Sharma", "rahul@kiet.edu", "CSE(AI & ML)", 2, 8.65));
        studentList.add(new Student(102, "Priya Verma", "priya@kiet.edu", "CSE", 2, 9.10));
        studentList.add(new Student(103, "Ankit Singh", "ankit@kiet.edu", "IT", 3, 7.85));
        studentList.add(new Student(104, "Sneha Gupta", "sneha@kiet.edu", "CSE(AI)", 1, 9.30));
        studentList.add(new Student(105, "Rohan Das", "rohan@kiet.edu", "ECE", 4, 8.20));
    }

    // Q3 - A: Get All Students -> GET /students
    @GetMapping
    public List<Student> getAllStudents() {
        return studentList;
    }

    // Q3 - B: Get Student by ID -> GET /students/{studentId}
    @GetMapping("/{studentId}")
    public Object getStudentById(@PathVariable int studentId) {
        for (Student s : studentList) {
            if (s.getStudentId() == studentId) {
                return s;
            }
        }
        Map<String, String> response = new HashMap<>();
        response.put("message", "Student not found");
        return response;
    }
    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        studentList.add(student);
        return student;
    }
    
    @PutMapping("/{studentId}")
    public Object updateStudent(@PathVariable int studentId, @RequestBody Student updatedStudent) {
        for (Student s : studentList) {
            if (s.getStudentId() == studentId) {
                s.setName(updatedStudent.getName());
                s.setEmail(updatedStudent.getEmail());
                s.setBranch(updatedStudent.getBranch());
                s.setYear(updatedStudent.getYear());
                s.setCgpa(updatedStudent.getCgpa());
                return s;
            }
        }
        Map<String, String> response = new HashMap<>();
        response.put("message", "Student not found");
        return response;
    }

    
    @DeleteMapping("/{studentId}")
    public Map<String, String> deleteStudent(@PathVariable int studentId) {
        Map<String, String> response = new HashMap<>();
        for (int i = 0; i < studentList.size(); i++) {
            if (studentList.get(i).getStudentId() == studentId) {
                studentList.remove(i);
                response.put("message", "Student deleted successfully");
                return response;
            }
        }
        response.put("message", "Student not found");
        return response;
    }
    // Q6 - A: Search by Branch and Year -> GET /students/search?branch=CSE&year=2
    @GetMapping("/search")
    public List<Student> searchStudents(@RequestParam String branch, @RequestParam int year) {
        List<Student> result = new ArrayList<>();
        for (Student s : studentList) {
            if (s.getBranch().equalsIgnoreCase(branch) && s.getYear() == year) {
                result.add(s);
            }
        }
        return result;
    }

    // Q6 - B: Find Students Based on CGPA -> GET /students/topper?cgpa=9.0
    @GetMapping("/topper")
    public List<Student> getTopperStudents(@RequestParam double cgpa) {
        List<Student> result = new ArrayList<>();
        for (Student s : studentList) {
            if (s.getCgpa() >= cgpa) {
                result.add(s);
            }
        }
        return result;
    }
}