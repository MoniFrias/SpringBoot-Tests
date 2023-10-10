package com.example.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.entity.Student;

@DataJpaTest // sirve para probar componentes de la capa persistence(entity, repository)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class StudentRepositoryTest {

	@Autowired
	private StudentRepository studentRepository;

	private Student student;

	@BeforeEach
	void setup() {
		student = Student.builder()
				.name("karla")
				.email("karla@email.com")
				.dateOfBirth(new Date(1986, 6, 12))
				.age(36)
				.build();
	}

	@DisplayName("Test to save a student") // solo da nombre al metodo
	@Test
	void saveStudentTest() {
		// given - condicion previa o config
		Student student1 = Student.builder()
				.name("Juan")
				.email("juan@email.com")
				.dateOfBirth(new Date(1986, 6, 12))
				.age(36)
				.build();
		// when - accion o comportamiento que se va a probar
		Student studentSave = studentRepository.save(student1);

		// then - verificar las salida
		assertThat(studentSave).isNotNull();
		assertThat(studentSave.getId()).isGreaterThan(0);
	}

	@Test
	void testListStudents() {
		// given
		Student student1 = Student.builder()
				.name("Luis")
				.email("luis@email.com")
				.dateOfBirth(new Date(1986, 6, 12))
				.age(36).build();

		studentRepository.save(student1);
		studentRepository.save(student);

		// when
		List<Student> listStudent = studentRepository.findAll();
		
		//then
		assertThat(listStudent).isNotNull();
		assertThat(listStudent.size()).isEqualTo(6); //6 because there is 5 records in the db + 2 that save here
	}
	
	@Test
	void testStudentById() {
		studentRepository.save(student);
		//when
		Student studentFind = studentRepository.findById(student.getId()).get();
		//then
		assertThat(studentFind).isNotNull();
	}
	
	@Test 
	void testUpdateStudent() {
		studentRepository.save(student);
		
		//when
		Student studentSave = studentRepository.findById(student.getId()).get();
		studentSave.setName("Maria");
		studentSave.setEmail("maria@email");
		
		Student studentUpdated = studentRepository.save(studentSave);
		
		//then
		assertThat(studentUpdated.getEmail()).isEqualTo("maria@email");
		assertThat(studentUpdated.getName()).isEqualTo("Maria");
	}
	
	@Test
	void testDeleteStudent() {
		studentRepository.save(student);
		
		//when
		studentRepository.deleteById(student.getId());
		Optional<Student> studentOptional = studentRepository.findById(student.getId());
		
		//then
		assertThat(studentOptional).isEmpty();
	}
}
