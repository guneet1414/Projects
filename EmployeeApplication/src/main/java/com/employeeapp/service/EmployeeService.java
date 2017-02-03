package com.employeeapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.employeeapp.entity.Employee;
import com.employeeapp.repository.EmployeeRepository;

/*
 * A Service class to provide abstraction between repository class
 * and controller class 
 */

@Service //making this class visible to spring
public class EmployeeService {

	private EmployeeRepository employeeRespository;
	private MongoOperations mongoOperations; //for some operation had to use mondoOperaions class
	
	@Autowired
	public EmployeeService(EmployeeRepository employeeRepository,
			MongoOperations mongoOperations) {
		this.employeeRespository = employeeRepository;
		this.mongoOperations = mongoOperations;
	}
	
	public Employee getEmployee(String firstName) {
		return employeeRespository.findByFirstName(firstName);
	}
	
	public void removeAll() {
		//to delete all records
		employeeRespository.deleteAll();
	}
	
	public void save(Employee e) {
		//saving employees
		employeeRespository.save(e);
	}
	
	public List<Employee> getEmployees() {
		//return list of all employees
		return employeeRespository.findAll();
	}
	
	public List<Employee> getByLastName(String lastName) {
		//return list of employee by last name
		return employeeRespository.findByLastName(lastName);
	}
	
	public void remove(Employee e) {
		//creating query
		Query searchQuery = new Query(Criteria.where("_id").is(e.getId()));
		//find and removing all matching records
		//in this case only one will be removed since we are searching records using _id
		//which is unique in mongodb
		mongoOperations.findAllAndRemove(searchQuery, Employee.class);
	}
	
	public Employee getEmployeeById(String Id) {
		//creating query 
		Query searchQuery = new Query(Criteria.where("_id").is(Id));
		//searching database based on employee class
		return mongoOperations.findOne(searchQuery, Employee.class) ;
	}
}
