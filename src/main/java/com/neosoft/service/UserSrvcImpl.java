package com.neosoft.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neosoft.bean.UserDto;
import com.neosoft.entity.User;
import com.neosoft.exception.NoNameResourceFoundException;
import com.neosoft.exception.ResourceAlreadyExistException;
import com.neosoft.exception.ResourceNotFoundException;
import com.neosoft.repository.UserRepository;


@Service
public class UserSrvcImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	User user;
	UserDto userDto;
	List<User> users;
	List<UserDto> userDtos;

	// add user
	@Transactional(readOnly = false)
	public UserDto adduser(User user) {
		if (userRepository.existsById(user.getId())) {
			throw new ResourceAlreadyExistException("User " + user.getId() + " already Existed");
		}
		user = userRepository.save(user);
		return empTodto(user);
	}

	// get all user
	@Transactional(readOnly = true)
	public List<UserDto> getAlluser() {
		users = userRepository.findAll();
		return listEmpToDto(users);
	}

	// get user
	@Transactional(readOnly = true)
	public UserDto getuserById(int id) {
		if (userRepository.existsById(id))
			user = userRepository.findByid(id);
		else
			throw new ResourceNotFoundException("given user " + id + " not available");
		return empTodto(user);
	}

	// update user
	@Transactional(readOnly = false)
	public UserDto updateuser(UserDto userDto) {
		if (userRepository.existsById(userDto.getId())) {
			user = dtoToEmp(userDto, new User());
			user = userRepository.save(user);
		} else
			throw new ResourceNotFoundException("given user " + userDto.getId() + " not available");
		return empTodto(user);
	}

	// soft delete user
	@Transactional(readOnly = false)
	public boolean deleteuserById(int id) throws ResourceNotFoundException {
		if (userRepository.existsById(id))
			userRepository.deleteById(id);
		else
			throw new ResourceNotFoundException("given user " + id + " not available");
		return true;
	}

	// hard delete user
	@Transactional(readOnly = false)
	public boolean hardDeleteuserById(int id) throws ResourceNotFoundException {
		if (userRepository.existsById(id))
			userRepository.hardDeleteByid(id);
		else
			throw new ResourceNotFoundException("given user " + id + " not available");
		return true;
	}

	// search by firstName
	@Transactional(readOnly = true)
	public List<UserDto> searchByFirstName(String firstName) {
		if (userRepository.existsByFirstName(firstName))
			users = userRepository.findByFirstName(firstName);
		else
			throw new NoNameResourceFoundException("given users by Name " + firstName + " is not available");
		return listEmpToDto(users);
	}



	// sort by dob/doj
	@Transactional(readOnly = true)
	public List<UserDto> sortByField(String field) {
		users = userRepository.findAll(Sort.by(Sort.Direction.ASC, field));
		return listEmpToDto(users);
	}

	// converting user to dto
	public UserDto empTodto(User user) {
		userDto = new UserDto();
		userDto.setFirstName(user.getFirstName());
		userDto.setLastName(user.getLastName());
		userDto.setId(user.getId());
		userDto.setPincode(user.getPincode());
		userDto.setCity(user.getCity());
		userDto.setBloodGroup(user.getBloodGroup());
		userDto.setAge(user.getAge());
		userDto.setDateOfBirth(user.getDateOfBirth());
		userDto.setDateOfJoin(user.getDateOfJoin());
		userDto.setDeleted(user.isDeleted());
		return userDto;
	}

	// converting List user to List dto
	public List<UserDto> listEmpToDto(List<User> users) {
		userDtos = new ArrayList<UserDto>();
		return userDtos = users.stream().map(emp -> {
			userDto = new UserDto();
			userDto.setFirstName(emp.getFirstName());
			userDto.setLastName(emp.getLastName());
			userDto.setId(emp.getId());
			userDto.setPincode(emp.getPincode());
			userDto.setCity(emp.getCity());
			userDto.setBloodGroup(emp.getBloodGroup());
			userDto.setAge(emp.getAge());
			userDto.setDateOfBirth(emp.getDateOfBirth());
			userDto.setDateOfJoin(emp.getDateOfJoin());
			userDto.setDeleted(emp.isDeleted());
			return userDto;
		}).collect(Collectors.toList());
	}

	// converting userDto to user
	public User dtoToEmp(UserDto userDto, User user) {
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setId(userDto.getId());
		user.setPincode(userDto.getPincode());
		user.setCity(userDto.getCity());
		user.setBloodGroup(userDto.getBloodGroup());
		user.setAge(userDto.getAge());
		user.setDateOfBirth(userDto.getDateOfBirth());
		user.setDateOfJoin(userDto.getDateOfJoin());
		user.setDeleted(userDto.isDeleted());
		return user;
	}
}
