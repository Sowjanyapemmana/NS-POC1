package com.neosoft.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neosoft.bean.UserDto;
import com.neosoft.entity.User;
import com.neosoft.service.UserSrvcImpl;

@RestController
@RequestMapping("/emp")
public class UserController {

	@Autowired
	UserSrvcImpl usrSrvcImpl;

	List<UserDto> employeeDtos;
	UserDto employeeDto;

	@PostMapping(value = "/addemp", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDto> addEmployee(@Valid @RequestBody User employee) {
		employeeDto = usrSrvcImpl.adduser(employee);
		return new ResponseEntity<UserDto>(employeeDto, HttpStatus.CREATED);
	}

	@GetMapping(value = "/getallemp", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<UserDto>> getAllEmployee() {
		employeeDtos = usrSrvcImpl.getAlluser();
		return new ResponseEntity<List<UserDto>>(employeeDtos, HttpStatus.OK);
	}

	@GetMapping(value = "/getemp/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDto> getEmployeeById(@PathVariable("id") int id) {
		employeeDto = usrSrvcImpl.getuserById(id);
		return new ResponseEntity<UserDto>(employeeDto, HttpStatus.OK);
	}

	@PutMapping(value = "/updateemp", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDto> updateEmployeeById(@Valid @RequestBody UserDto employeeDto) {
		employeeDto = usrSrvcImpl.updateuser(employeeDto);
		return new ResponseEntity<UserDto>(employeeDto, HttpStatus.OK);
	}

	@DeleteMapping(value = "/deleteemp/{id}")
	public ResponseEntity<String> deleteEmployeeById(@PathVariable("id") int id) {
		usrSrvcImpl.deleteuserById(id);
		return new ResponseEntity<String>("employee number" + id + " Deactivated", HttpStatus.OK);
	}

	@DeleteMapping(value = "/purgeemp/{id}")
	public ResponseEntity<String> hardDeleteEmployeeById(@PathVariable("id") int id) {
		usrSrvcImpl.hardDeleteuserById(id);
		return new ResponseEntity<String>("employee number " + id + " deleted", HttpStatus.NO_CONTENT);
	}

	@GetMapping(value = "/name/{firstName}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<UserDto>> searchByfirstName(@PathVariable("firstName") String firstName) {
		employeeDtos = usrSrvcImpl.searchByFirstName(firstName);
		return new ResponseEntity<List<UserDto>>(employeeDtos, HttpStatus.OK);
	}

	@GetMapping(value = "/surname/{lastName}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<UserDto>> searchBylastName(@PathVariable("lastName") String lastName) {
		employeeDtos = usrSrvcImpl.searchByFirstName(lastName);
		return new ResponseEntity<List<UserDto>>(employeeDtos, HttpStatus.OK);
	}


	@GetMapping(value = "/sort/{field}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<UserDto>> sortByField(@PathVariable("field") String field) {
		employeeDtos = usrSrvcImpl.sortByField(field);
		return new ResponseEntity<List<UserDto>>(employeeDtos, HttpStatus.OK);
	}
}
