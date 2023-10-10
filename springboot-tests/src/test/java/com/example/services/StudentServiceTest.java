package com.example.services;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.dto.Response;
import com.example.dto.ValidationException;
import com.example.entity.Student;
import com.example.repository.StudentRepository;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

	@Mock // crea un simulacro
	private StudentRepository studentRepository;
	
	@InjectMocks
	private StudentService studentService;
	
	private Student student;
	
	@BeforeEach
	void setup() {
		student = Student.builder()
				.id(1L)
				.name("karla")
				.email("karla@email.com")
				.dateOfBirth(new Date(1986, 6, 12))
				.age(36)
				.build();
	}
	
	@Test
	void testSaveStudent() {
		//given 
		given(studentRepository.findByEmail(student.getEmail()))
		.willReturn(Optional.empty());
		given(studentRepository.save(student)).willReturn(student);
		
		//when
		Response response = studentService.saveStudent(student);
		
		//then		
		assertTrue(response.isResult());
	}
	
	@Test
	void testSaveStudentThrowException() {
		//given 
		given(studentRepository.findByEmail(student.getEmail()))
		.willReturn(Optional.of(student));
		
		//when
		assertThrows(ValidationException.class, ()->{
			studentService.saveStudent(student);
		});
		
		//then		
		verify(studentRepository, never()).save(any(Student.class));
	}
	
	@Test
	void testGetAll() {
		//given
		Student student1 = Student.builder()
				.name("Maria")
				.email("maria@email.com")
				.dateOfBirth(new Date(1986, 6, 12))
				.age(36)
				.build();
		
		//when
		given(studentRepository.findAll()).willReturn(List.of(student, student1));
				
		//then
		assertTrue(studentService.getAllStudent().isResult());		
	}
	
	@Test
	void testGetAllEmpty() {		
		//when
		when(studentRepository.findAll()).thenReturn(Collections.emptyList());
		
		//then
		assertThrows(ValidationException.class, () -> {
			studentService.getAllStudent();
		});
	}
	
	@Test
	void testGetStudentById() {
		//when
		given(studentRepository.findStudentById(1L)).willReturn(Optional.of(student));
		
		//then
		assertTrue(studentService.getStudentId(1L).isResult());
	}
	
	@Test
	void testGetStudentByIdThrowException() {
		//when
		when(studentRepository.findStudentById(1L)).thenReturn(Optional.empty());
		
		//then
		assertThrows(ValidationException.class, () -> {
			studentService.getStudentId(1L);
		});
	}
	
	@Test
	void testUpdate() {
		//given
		given(studentRepository.findStudentById(1L)).willReturn(Optional.of(student));
		
		Student studentUpdate = Student.builder()
				.id(1L)
				.name("Maria")
				.email("maria@email.com")
				.dateOfBirth(new Date(1986, 6, 12))
				.age(36)
				.build();		
		
		//when
		given(studentRepository.save(studentUpdate)).willReturn(studentUpdate);
		
		//then
		assertTrue(studentService.updateStudent(1L, studentUpdate).isResult());
	}
	
	@Test
	void testDeleteStudent() {
		given(studentRepository.findStudentById(1L)).willReturn(Optional.of(student));
		
		//when
		willDoNothing().given(studentRepository).deleteById(student.getId());
		
		//then
		assertEquals(studentService.deleteById(1L).getMessage(), "Successfully removed");
		verify(studentRepository, times(1)).deleteById(1L);
	}
	

}
