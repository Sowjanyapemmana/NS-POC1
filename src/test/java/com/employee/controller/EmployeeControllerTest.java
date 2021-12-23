package com.employee.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.junit.jupiter.api.Test;
import org.junit.runner.Result;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neosoft.bean.UserDto;
import com.neosoft.controller.UserController;
import com.neosoft.entity.User;
import com.neosoft.exception.NoNameResourceFoundException;
import com.neosoft.exception.NoPincodeResourceFoundException;
import com.neosoft.exception.ResourceAlreadyExistException;
import com.neosoft.exception.ResourceNotFoundException;
import com.neosoft.repository.UserRepository;
import com.neosoft.service.UserSrvcImpl;

@WebMvcTest(value = UserController.class)
public class EmployeeControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	UserSrvcImpl empSrvcImpl;
	@MockBean
	UserRepository repository;

	@Mock
	UserController controller;

	MvcResult mvcResult;

	User employee;

	private static ObjectMapper mapper = new ObjectMapper();

	public UserDto buildResp() {
		UserDto employeeDto = new UserDto();
		employeeDto.setFirstName("bob");
		employeeDto.setLastName("chohan");
		employeeDto.setId(1010);
		employeeDto.setPincode(919191);
		employeeDto.setDateOfJoin(new Date());
		employeeDto.setDateOfBirth(new Date());
		employeeDto.setCity("goa");
		employeeDto.setBloodGroup("A+");
		employeeDto.setAge(28);
		employeeDto.setDeleted(false);
		return employeeDto;
	}

	@Test
	public void addEmployeeTest() throws Exception {
		employee = new User();
		employee.setId(1010);
		employee.setFirstName("bob");
		employee.setLastName("chohan");
		employee.setPincode(919191);
		employee.setCity("goa");
		employee.setBloodGroup("A+");
		employee.setAge(28);
		employee.setDateOfBirth(new Date());
		employee.setDateOfJoin(new Date());

		Mockito.when(empSrvcImpl.adduser(ArgumentMatchers.any())).thenReturn(buildResp());
		String json = mapper.writeValueAsString(employee);

		mockMvc.perform(post("/emp/addemp").contentType(MediaType.APPLICATION_JSON_VALUE).characterEncoding("utf-8")
				.content(json).accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isCreated())
				.andExpect(jsonPath("$.firstName", is("bob"))).andExpect(jsonPath("$.lastName", is("chohan")))
				.andExpect(jsonPath("$.pincode", is(919191))).andExpect(jsonPath("$.id", is(1010)))
				.andExpect(jsonPath("$.city", is("goa"))).andExpect(jsonPath("$.bloodGroup", is("A+")))
				.andExpect(jsonPath("$.age", is(28))).andExpect(jsonPath("$.deleted", is(false)));
	}

	@Test
	public void getEmployeeByIdTest() throws Exception {
		Mockito.when(empSrvcImpl.getuserById(1010)).thenReturn(buildResp());

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/emp/getemp/1010")
				.contentType(MediaType.APPLICATION_JSON_VALUE);

		mvcResult = mockMvc.perform(builder).andReturn();

		mockMvc.perform(builder).andExpect(status().isOk()).andExpect(jsonPath("$.firstName", is("bob")))
				.andExpect(jsonPath("$.lastName", is("chohan"))).andExpect(jsonPath("$.pincode", is(919191)))
				.andExpect(jsonPath("$.id", is(1010))).andExpect(jsonPath("$.city", is("goa")))
				.andExpect(jsonPath("$.bloodGroup", is("A+"))).andExpect(jsonPath("$.age", is(28)))
				.andExpect(jsonPath("$.deleted", is(false)));
	}

	@Test
	public void updateEmployeeTest() throws Exception {
		UserDto employeeDto = new UserDto();
		employeeDto.setFirstName("bob");
		employeeDto.setLastName("chohan");
		employeeDto.setId(20302);
		employeeDto.setPincode(919191);
		employeeDto.setDateOfBirth(new Date());
		employeeDto.setDateOfJoin(new Date());
		employeeDto.setCity("goa");
		employeeDto.setBloodGroup("A+");
		employeeDto.setAge(28);
		employeeDto.setDeleted(false);

		Mockito.when(empSrvcImpl.updateuser(ArgumentMatchers.any())).thenReturn(employeeDto);
		String json = mapper.writeValueAsString(employeeDto);
		mockMvc.perform(put("/emp/updateemp").contentType(MediaType.APPLICATION_JSON_VALUE).characterEncoding("utf-8")
				.content(json).accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName", is("bob"))).andExpect(jsonPath("$.lastName", is("chohan")))
				.andExpect(jsonPath("$.pincode", is(919191))).andExpect(jsonPath("$.id", is(20302)))
				.andExpect(jsonPath("$.city", is("goa"))).andExpect(jsonPath("$.bloodGroup", is("A+")))
				.andExpect(jsonPath("$.age", is(28))).andExpect(jsonPath("$.deleted", is(false)));
	}

	@Test
	public void deleteEmployeeByIdTest() throws Exception {
		String uri = "/emp/deleteemp/101010";
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		assertEquals(content, "employee number" + 101010 + " Deactivated");
	}

	@Test
	public void hardDeleteEmployeeByIdTest() throws Exception {
		String uri = "/emp/purgeemp/101010";
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(204, status);
		String content = mvcResult.getResponse().getContentAsString();
		assertEquals(content, "employee number " + 101010 + " deleted");
	}

	@Test
	public void getAllEmployeeTest() throws Exception {

		UserDto employee1 = new UserDto("rolley", "methue", 1010, 27, "ratlam", 457001, "A+", new Date(),
				new Date(), false);
		UserDto employee2 = new UserDto("Alex", "Gussin", 2020, 25, "channai", 223344, "A+", new Date(),
				new Date(), false);

		List<UserDto> employees = new ArrayList<UserDto>();
		employees.add(employee1);
		employees.add(employee2);

		Mockito.when(empSrvcImpl.getAlluser()).thenReturn(employees);
		mockMvc.perform(MockMvcRequestBuilders.get("/emp/getallemp").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andExpect(jsonPath("$[0].firstName", is("rolley")));
	}

	@Test
	public void searchEmployeesByfirstNameTest() throws Exception {

		UserDto employee1 = new UserDto("alex", "Gussin", 1010, 27, "ratlam", 457001, "A+", new Date(),
				new Date(), false);
		UserDto employee2 = new UserDto("alex", "nelson", 2020, 25, "ratlam", 457001, "A+", new Date(),
				new Date(), false);

		List<UserDto> employees = new ArrayList<UserDto>();
		employees.add(employee1);
		employees.add(employee2);
		String firstName = "alex";

		Mockito.when(empSrvcImpl.searchByFirstName(firstName)).thenReturn(employees);
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/emp/name/firstName")
				.contentType(MediaType.APPLICATION_JSON_VALUE);

		mvcResult = mockMvc.perform(builder).andReturn();
		mockMvc.perform(builder).andExpect(status().isOk());
	}




	@Test
	public void addEmployeeExceptionTest() throws Exception {
		employee = new User("bob", "thomas", 1010, 22, "indore", 44450, "A+", new Date(), new Date());

		Mockito.when(empSrvcImpl.adduser(ArgumentMatchers.any())).thenThrow(ResourceAlreadyExistException.class);
		String json = mapper.writeValueAsString(employee);
		mockMvc.perform(post("/emp/addemp").contentType(MediaType.APPLICATION_JSON_VALUE).characterEncoding("utf-8")
				.content(json).accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isAlreadyReported());
	}

	@Test
	public void updateEmployeeExceptionTest() throws Exception {
		UserDto employeedto = new UserDto("bob", "thomas", 1010, 22, "ratlam", 457001, "A+", new Date(),
				new Date(), false);

		Mockito.when(empSrvcImpl.updateuser(ArgumentMatchers.any())).thenThrow(ResourceNotFoundException.class);
		String json = mapper.writeValueAsString(employeedto);
		mockMvc.perform(put("/emp/updateemp").contentType(MediaType.APPLICATION_JSON_VALUE).characterEncoding("utf-8")
				.content(json).accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isNotFound());
	}

	@Test
	public void getEmployeeByIdExceptionTest() throws Exception {
		int id = 1010;
		Mockito.when(empSrvcImpl.getuserById(id)).thenThrow(ResourceNotFoundException.class);
		mockMvc.perform(get("/emp/getemp/" + id).contentType(MediaType.APPLICATION_JSON_VALUE)
				.characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isNotFound());
	}

	@Test
	public void getEmployeeByIdUnKnownExceptionTest() throws Exception {
		int id = 1010;
		Mockito.when(empSrvcImpl.getuserById(id)).thenThrow(ClassCastException.class);
		mockMvc.perform(get("/emp/getemp/" + id).contentType(MediaType.APPLICATION_JSON_VALUE)
				.characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isInternalServerError());
	}

	@Test
	public void searchEmloyeeByfirstNameExceptionTest() throws Exception {
		String firstName = "alex";
		Mockito.when(empSrvcImpl.searchByFirstName(firstName)).thenThrow(NoNameResourceFoundException.class);
		mockMvc.perform(get("/emp/name/" + firstName).contentType(MediaType.APPLICATION_JSON_VALUE)
				.characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isNotFound());
	}


	@Test
	public void addEmployeeValidationExceptonTest() throws Exception {
		employee = new User("bob", "", 1010, 26, "ratlam", 457001, "A+", new Date(), new Date());

		Mockito.when(empSrvcImpl.adduser(ArgumentMatchers.any())).thenReturn(buildResp());
		String json = mapper.writeValueAsString(employee);

		mockMvc.perform(post("/emp/addemp").contentType(MediaType.APPLICATION_JSON_VALUE).characterEncoding("utf-8")
				.content(json).accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isBadRequest());
	}
}
