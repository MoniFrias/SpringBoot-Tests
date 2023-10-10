package com.example.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.dto.Response;
import com.example.entity.Student;
import com.example.services.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
@WebMvcTest
public class StudentControllerTest {

	@MockBean
	private StudentService studentService;

	@InjectMocks
	private Controller controller;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MockMvc mockMvc;
	
	private Student student;
	private Response response;
	
	@BeforeEach
	void setup() {
		student = Student.builder().id(1L).name("Maria").email("maria@email.com")
				.dateOfBirth(new Date(1986, 6, 12)).age(36).build();
		 response = new Response(true, "Ok", student);
	}

	@Test
	void testSaveStudent() throws Exception {
		// given
		given(studentService.saveStudent(any(Student.class))).willReturn(response);

		// when
		ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/api/student/save")
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(student)));

		//then
		result.andDo(print()).andExpect(status().isOk());
	}

	@Test
	void testGetAll() throws Exception {
		List<Student> students = new ArrayList<>();
		students.add(student);
		students.add(Student.builder().id(1L).name("Luis").email("luis@email.com").dateOfBirth(new Date(1986, 6, 12))
				.age(36).build());
		
		when(studentService.getAllStudent()).thenReturn(response);
		
		ResultActions result =  mockMvc.perform(get("/api/student/getAll"));
		
		result.andExpect(status().isFound())
		.andExpect(jsonPath("$.result", is(response.isResult())))
		.andExpect(jsonPath("$.message", is(response.getMessage())));
		
	}
	
	@Test
	void testListStudent() throws Exception {
		Student student1 = Student.builder().id(1L).name("Luis").email("luis@email.com").dateOfBirth(new Date(1986, 6, 12))
				.age(36).build();
		List<Student> students = new ArrayList<>(Arrays.asList(student, student1));
		
		when(studentService.listStudent()).thenReturn(students);
		
		mockMvc.perform(get("/api/student/getAllList").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
		.andExpect(jsonPath("$[1].name", is("Luis")));
		
	}
	
	@Test
	void testGetById() throws Exception {
		
		when(studentService.getStudentId(1L)).thenReturn(response);
		
		mockMvc.perform(get("/api/student/getById/{id}", student.getId())).andExpect(status().isFound());
		
	}
	
	@Test
	void testUpdate() throws Exception {
		when(studentService.updateStudent(1L, student)).thenReturn(response);
		
		mockMvc.perform(put("/api/student/edit/{id}", student.getId())
		.contentType(MediaType.APPLICATION_JSON)
		.content(objectMapper.writeValueAsString(student)))
		.andExpect(status().isOk());
	}
	
	@Test
	void testDelete() throws Exception {
		when(studentService.deleteById(1L)).thenReturn(response);
		mockMvc.perform(delete("/api/student/delete/{id}", student.getId())).andExpect(status().isOk());
	}
}
