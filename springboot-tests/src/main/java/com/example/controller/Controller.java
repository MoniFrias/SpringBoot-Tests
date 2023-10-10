package com.example.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.Response;
import com.example.entity.Student;
import com.example.services.StudentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
public class Controller {
	
	private final StudentService studentService;

	@PostMapping("/save")
	public ResponseEntity<Response> saveStudent(@RequestBody Student student) {
		Response response = studentService.saveStudent(student);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/getAll")
	public ResponseEntity<Response> listarStudent() {
		Response response = studentService.getAllStudent();
		return new ResponseEntity<>(response, HttpStatus.FOUND);
	}

	@GetMapping("/getById/{id}")
	public ResponseEntity<Response> getStudentId(@PathVariable("id") long studentId) {
		Response response = studentService.getStudentId(studentId);
		return new ResponseEntity<>(response,HttpStatus.FOUND);
	}
	
	@GetMapping("/getAllList")
	public List<Student> listStudent(){
		return studentService.listStudent();
	}

	@PutMapping("/edit/{id}")
	public ResponseEntity<Response> updateStudent(@PathVariable("id") long studentId, @RequestBody Student student) {
		Response response = studentService.updateStudent(studentId,student);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Response> eliminarEmpleado(@PathVariable("id") long studentId) {
		Response response = studentService.deleteById(studentId);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
}
