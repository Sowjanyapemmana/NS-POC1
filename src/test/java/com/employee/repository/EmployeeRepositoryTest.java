package com.employee.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.neosoft.entity.User;
import com.neosoft.repository.UserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class EmployeeRepositoryTest {

	@Autowired
	UserRepository repository;

	@Test
	public void addEmployeeTest() {
		User employee = repository
				.save(new User("piter", "parker", 1010, 27, "ratlam", 333999, "A+", new Date(), new Date()));
		assertThat(employee).hasFieldOrPropertyWithValue("firstName", "piter");
		assertThat(employee).hasFieldOrPropertyWithValue("pincode", 333999);
		assertThat(employee).hasFieldOrPropertyWithValue("city", "ratlam");
	}

	@Test
	public void findAllEmployeeTest() {
		User employee1 = new User("piter", "parker", 1010, 27, "ratlam", 333999, "A+", new Date(), new Date());
		User employee2 = new User("jack", "john", 1020, 27, "ratlam", 333000, "A+", new Date(), new Date());

		repository.save(employee1);
		repository.save(employee2);
		assertNotNull(repository.findAll());
	}

	@Test
	public void findEmployeeByIdTest() {
		User employee1 = new User("piter", "parker", 1010, 27, "ratlam", 333999, "A+", new Date(), new Date());
		User employee2 = new User("jack", "john", 1020, 27, "ratlam", 333000, "A+", new Date(), new Date());
		repository.save(employee1);
		repository.save(employee2);

		User employee3 = repository.findByid(employee1.getId());
		assertThat(employee3).isEqualTo(employee1);
	}

	@Test
	public void findEmployeeByFirstNameTest() {

		User employee1 = repository
				.save(new User("piter", "parker", 1010, 27, "ratlam", 333999, "A+", new Date(), new Date()));
		User employee2 = repository
				.save(new User("jack", "john", 1020, 27, "ratlam", 333000, "A+", new Date(), new Date()));

		List<User> employees = repository.findByFirstName(employee1.getFirstName());
		assertThat(employees).hasSize(1).contains(employee1);
	}

	@Test
	public void findEmployeeBylastNameTest() {
		User employee1 = repository
				.save(new User("piter", "parker", 1010, 27, "ratlam", 333999, "A+", new Date(), new Date()));
		User employee2 = repository
				.save(new User("jack", "john", 1020, 27, "ratlam", 333000, "A+", new Date(), new Date()));

		List<User> employees = repository.findByLastName(employee1.getLastName());
		assertThat(employees).hasSize(1).contains(employee1);
	}

	@Test
	public void findEmployeeByPincodeTest() {
		User employee1 = new User("piter", "parker", 1010, 27, "ratlam", 333999, "A+", new Date(), new Date());
		User employee2 = new User("jack", "john", 1020, 27, "indore", 333000, "A+", new Date(), new Date());
		User employee3 = new User("marry", "walter", 1030, 27, "ratlam", 333999, "A+", new Date(), new Date());
		repository.save(employee1);
		repository.save(employee2);
		repository.save(employee3);

		List<User> employees = repository.findByPincode(employee1.getPincode());
		assertThat(employees).hasSize(2).contains(employee1, employee3);
	}

	@Test
	public void updateById() {
		User employee1 = new User("piter", "parker", 2323, 27, "ratlam", 333999, "A+", new Date(), new Date());
		User employee2 = new User("jack", "john", 3434, 27, "indore", 333000, "A+", new Date(), new Date());
		User employee3 = new User("marry", "walter", 4545, 27, "ratlam", 333999, "A+", new Date(), new Date());
		repository.save(employee1);
		repository.save(employee2);
		repository.save(employee3);

		User employee = new User("marry", "walter", 4545, 29, "pune", 333222, "A+", new Date(), new Date());

		User employeeNew = repository.findByid(4545);
		employeeNew.setFirstName(employee.getFirstName());
		employeeNew.setLastName(employee.getLastName());
		employeeNew.setId(employee.getId());
		employeeNew.setPincode(employee.getPincode());
		employeeNew.setDateOfBirth(employee.getDateOfBirth());
		employeeNew.setDateOfJoin(employee.getDateOfJoin());
		employeeNew.setAge(employee.getAge());
		employeeNew.setCity(employee.getCity());
		employeeNew.setBloodGroup(employee.getBloodGroup());

		repository.save(employeeNew);
		User checkEmp = repository.findByid(4545);

		assertThat(checkEmp.getId()).isEqualTo(employee3.getId());
		assertThat(checkEmp.getFirstName()).isEqualTo(employee3.getFirstName());
		assertThat(checkEmp.getCity()).isEqualTo(employee3.getCity());
		assertThat(checkEmp.getPincode()).isEqualTo(employee3.getPincode());
		assertThat(checkEmp.getAge()).isEqualTo(employee3.getAge());
	}

	@Test
	public void deleteByIdTest() {
		User employee1 = new User("piter", "parker", 1010, 27, "ratlam", 333999, "A+", new Date(), new Date());
		User employee2 = new User("jack", "john", 1020, 27, "indore", 333000, "A+", new Date(), new Date());
		User employee3 = new User("marry", "walter", 10300, 27, "ratlam", 333999, "A+", new Date(), new Date());
		repository.save(employee1);
		repository.save(employee2);
		repository.save(employee3);

		repository.deleteById(1010);
	}
}
