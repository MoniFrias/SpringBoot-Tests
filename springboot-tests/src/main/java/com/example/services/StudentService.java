package com.example.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.dto.Response;
import com.example.dto.ValidationException;
import com.example.entity.Student;
import com.example.repository.StudentRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class StudentService {

	private final StudentRepository studentRepository;

	public Response saveStudent(Student student) {
		Response response = new Response();
		Optional<Student> findStudent = studentRepository.findByEmail(student.getEmail());
		if (findStudent.isPresent()) {
			throw new ValidationException("Student with that email is already save");
		}
		response.setData(studentRepository.save(student));
		return response;
	}

	public Response getAllStudent() {
		Response response = new Response();
		List<Student> students = studentRepository.findAll();
		if (!students.isEmpty()) {
			response.setData(students);
			return response;
		}
		throw new ValidationException("No students save");
	}
	
	public List<Student> listStudent(){
		List<Student> students = studentRepository.findAll();
		if (!students.isEmpty()) {
			return students;
		}
		throw new ValidationException("No students");
	}

	public Response getStudentId(long studentId) {
		Response response = new Response();
		response.setData(findById(studentId));
		return response;
	}

	public Optional<Student> findById(long studentId) {
		Optional<Student> student = studentRepository.findStudentById(studentId);
		if (!student.isPresent()) {
			throw new ValidationException("No student with that ID");
		}
		return student;
	}

	public Response updateStudent(long studentId, Student student) {
		Response response = new Response();
		Student studentSave = findById(studentId).get();

		studentSave.setName(student.getName());
		studentSave.setEmail(student.getEmail());
		studentSave.setDateOfBirth(student.getDateOfBirth());
		studentSave.setAge(student.getAge());

		response.setData(studentRepository.save(studentSave));
		return response;

	}

	public Response deleteById(long studentId) {
		Response response = new Response();
		studentRepository.deleteById(findById(studentId).get().getId());
		response.setMessage("Successfully removed");

		return response;
	}

}
