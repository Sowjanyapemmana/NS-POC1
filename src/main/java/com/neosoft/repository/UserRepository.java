package com.neosoft.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.neosoft.entity.User;

@Repository
public interface UserRepository extends JpaSpecificationExecutor<User>,JpaRepository<User, Integer>, PagingAndSortingRepository<User, Integer> {

	public User findByid(int employeeId);

	@Modifying
	@Query("UPDATE Employee SET deleted=1 WHERE id= :employeeId")
	public int deleteById(int employeeId);	
	

	@Modifying
	@Query("DELETE FROM Employee e WHERE e.id = :employeeId")
	public boolean hardDeleteByid(@Param("employeeId") int employeeId);
    
	public List<User> findByFirstName(String firstName);
	public List<User> findByLastName(String lastName);
	public List<User> findByPincode(int pincode);
	public boolean existsById(int id);
	
	public boolean existsByFirstName(String firstName);
	public boolean existsByLastName(String lastName);
	public boolean existsByPincode(int pincode);
}
