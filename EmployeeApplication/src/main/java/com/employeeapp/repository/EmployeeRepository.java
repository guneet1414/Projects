package com.employeeapp.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.employeeapp.entity.Employee;

/*
 *  Interface provide by spring for most common database operations
 */

public interface EmployeeRepository extends MongoRepository<Employee, String> {
	
	//find a employee by name - spring constructor query according to the name of method
	public Employee findByFirstName(String firstName);
	
	//get a list of employee by last name
	public List<Employee> findByLastName(String lastName);

}
