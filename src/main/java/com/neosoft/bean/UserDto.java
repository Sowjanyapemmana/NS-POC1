package com.neosoft.bean;

import javax.persistence.Column;

//import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.neosoft.entity.User;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

	@NotBlank(message = "firstName can not be blank")
	private String firstName;
	
	@NotBlank(message = "lastName can not be blank")
	private String lastName;
	
	@NotNull(message = "userId can not be blank")
	private int id;
	@Min(value = 18, message = "min age should be 18")
	private int age;
	
	@NotBlank(message = "city can not be blank")
	private String city;
	private int pincode;
	
	@NotBlank(message = "bloodGroup can not be blank")
	private String bloodGroup;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Column(name = "dob")
	private java.util.Date dateOfBirth;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Column(name = "doj")
	private java.util.Date dateOfJoin;

	private boolean deleted;
	
	public UserDto(
			@NotBlank(message = "firstName can not be blank") String firstName,
			@NotBlank(message = "lastName can not be blank") String lastName,
			@NotNull(message = "userId can not be blank") int id,
			@Min(value = 18, message = "minimum age should be 18") int age,
			@NotBlank(message = "city can not be blank") String city,
			int pincode,
			@NotBlank(message = "bloodGroup can not be blank") String bloodGroup,
			java.util.Date dateOfBirth, java.util.Date dateOfJoin, boolean deleted) {   //NOSONAR
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.id = id;
		this.age = age;
		this.city = city;
		this.pincode = pincode;
		this.bloodGroup = bloodGroup;
		this.dateOfBirth = dateOfBirth;
		this.dateOfJoin = dateOfJoin;
		this.deleted = deleted;
	}
	
	public UserDto() {
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getPincode() {
		return pincode;
	}

	public void setPincode(int pincode) {
		this.pincode = pincode;
	}

	public String getBloodGroup() {
		return bloodGroup;
	}

	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	public java.util.Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(java.util.Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public java.util.Date getDateOfJoin() {
		return dateOfJoin;
	}

	public void setDateOfJoin(java.util.Date dateOfJoin) {
		this.dateOfJoin = dateOfJoin;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	
}
