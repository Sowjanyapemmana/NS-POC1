package com.employee.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import com.neosoft.bean.UserDto;
import com.neosoft.entity.User;
import com.neosoft.exception.NoNameResourceFoundException;
import com.neosoft.exception.NoPincodeResourceFoundException;
import com.neosoft.exception.ResourceAlreadyExistException;
import com.neosoft.exception.ResourceNotFoundException;
import com.neosoft.repository.UserRepository;
import com.neosoft.service.UserSrvcImpl;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
class EmployeeSrvcImplTest {

	@Mock
	private UserRepository employeeRepository;

	@InjectMocks
	private UserSrvcImpl empSrvcImpl;
	private User employee;

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

	public User buildEmployee() {
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
		return employee;
	}

	@Test
	public void addEmployeeTest() {
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

		Mockito.when(employeeRepository.existsById(buildEmployee().getId())).thenReturn(false);
		Mockito.when(employeeRepository.save(employee)).thenReturn(employee);
		UserDto employeeDto = empSrvcImpl.adduser(employee);
		assertThat(employeeDto.getFirstName()).isEqualTo(buildResp().getFirstName());
	}

	@Test
	public void getEmployeeByIdTest() {
		Mockito.when(employeeRepository.findByid(1010)).thenReturn(buildEmployee());
		Mockito.when(employeeRepository.existsById(1010)).thenReturn(true);

		UserDto employeeDto = empSrvcImpl.getuserById(1010);
		assertThat(employeeDto.getFirstName()).isEqualTo(buildResp().getFirstName());
	}

	@Test
	public void getAllEmployeesTest() {
		User employee1 = new User("alex", "martin", 1010, 22, "ratlam", 457001, "A+", new Date(), new Date());
		User employee2 = new User("Alex", "Gussin", 2020, 25, "channai", 223344, "A+", new Date(), new Date());
		List<User> listEmp = new ArrayList<User>();
		listEmp.add(employee1);
		listEmp.add(employee2);

		Mockito.when(employeeRepository.findAll()).thenReturn(listEmp);

		List<UserDto> listEmpDto = empSrvcImpl.getAlluser();

		assertEquals(2, listEmpDto.size());
	}

	@Test
	public void updateEmployeeTest() {
		Mockito.when(employeeRepository.existsById(1010)).thenReturn(true);
		Mockito.when(employeeRepository.save(Mockito.any(User.class))).thenReturn(buildEmployee());

		UserDto employeeDto = empSrvcImpl.updateuser(buildResp());
		assertThat(employeeDto.getFirstName()).isEqualTo(buildResp().getFirstName());
	}

	@Test
	public void deleteEmployeeByIdTest() {
		Mockito.when(employeeRepository.existsById(1010)).thenReturn(true);
		Mockito.when(employeeRepository.deleteById(1010)).thenReturn(1);
		boolean flag = empSrvcImpl.deleteuserById(1010);
		assertThat(flag).isTrue();
	}

	@Test
	public void hardDeleteEmployeeByIdTest() {
		Mockito.when(employeeRepository.existsById(1010)).thenReturn(true);
		Mockito.when(employeeRepository.hardDeleteByid(1010)).thenReturn(true);
		boolean flag = empSrvcImpl.hardDeleteuserById(1010);
		assertThat(flag).isTrue();
	}
	
	@Test
	public void searchEmployeeByFirstNameTest() {
		User employee1 = new User("alex", "martin", 1010, 22, "ratlam", 457001, "A+", new Date(), new Date());
		User employee2 = new User("alex", "Gussin", 2020, 25, "channai", 223344, "A+", new Date(), new Date());
		List<User> listEmp = new ArrayList<User>();
		listEmp.add(employee1);
		listEmp.add(employee2);
		
		Mockito.when(employeeRepository.existsByFirstName("alex")).thenReturn(true);
		Mockito.when(employeeRepository.findByFirstName("alex")).thenReturn(listEmp);
		List<UserDto> listEmpDto = empSrvcImpl.searchByFirstName("alex");
		
		assertEquals(2, listEmpDto.size());
	}
	
	
	

	@Test
	public void sortByFieldTest() {
		User employee1 = new User("alex", "martin", 1010, 22, "ratlam", 457001, "A+", new Date(), new Date());
		User employee2 = new User("rock", "martin", 2020, 25, "ratlam", 457001, "A+", new Date(), new Date());
		List<User> listEmp = new ArrayList<User>();
		listEmp.add(employee1);
		listEmp.add(employee2);
		String field = "firstName";
		Mockito.when(employeeRepository.findAll(Sort.by(Sort.Direction.ASC, field))).thenReturn(listEmp);
		List<UserDto> listEmpDto = empSrvcImpl.sortByField(field);
		
		assertEquals(2, listEmpDto.size());
	}

		@Test
		public void addEmployeeExceptionTest() {
			User employee = new User("alex", "martin", 1010, 22, "ratlam", 457001, "A+", new Date(), new Date());
			
			Mockito.when(employeeRepository.existsById(1010)).thenReturn(true);
			ResourceAlreadyExistException exception = assertThrows(ResourceAlreadyExistException.class, () -> { empSrvcImpl.adduser(employee);});
		    assertTrue(exception.getMessage().contains("User " + employee.getId() + " already Existed"));
		}

		@Test
		public void getEmployeeByIdExceptionTest() {
			Mockito.when(employeeRepository.existsById(1010)).thenReturn(false);
			ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> { empSrvcImpl.getuserById(1010); });
		    assertTrue(exception.getMessage().contains("given user " + 1010 + " not available"));
		}

		@Test
		public void updateEmployeeExceptionTest() {
			UserDto employeeDto = new UserDto("alex", "martin", 1010, 22, "ratlam", 457001, "A+", new Date(), new Date(),false);
			
			Mockito.when(employeeRepository.existsById(1010)).thenReturn(false);
			ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> { empSrvcImpl.updateuser(employeeDto); });
		    assertTrue(exception.getMessage().contains("given user " + employeeDto.getId() + " not available"));
		}
	
		@Test
		public void deleteEmployeeByIdExceptionTest() {
			Mockito.when(employeeRepository.existsById(1010)).thenReturn(false);
			ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> { empSrvcImpl.deleteuserById(1010); });
		    assertTrue(exception.getMessage().contains("given user " + 1010 + " not available"));
		}

		@Test
		public void hardDeleteEmployeeByIdExceptionTest() {
			Mockito.when(employeeRepository.existsById(1010)).thenReturn(false);
			ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> { empSrvcImpl.hardDeleteuserById(1010); });
		    assertTrue(exception.getMessage().contains("given user " + 1010 + " not available"));
		}
	
		@Test
		public void searchByfirstNameExceptionTest() {
			String firstName = "methue";
			Mockito.when(employeeRepository.existsByFirstName(firstName)).thenReturn(false);
			NoNameResourceFoundException exception = assertThrows(NoNameResourceFoundException.class, () -> { empSrvcImpl.searchByFirstName(firstName); });
		    assertTrue(exception.getMessage().contains(("given users by Name " + firstName + " is not available")));
		}
	
}

