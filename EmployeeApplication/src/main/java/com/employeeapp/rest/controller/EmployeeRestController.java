package com.employeeapp.rest.controller;

/*
 * Rest controller
 */

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employeeapp.entity.Employee;
import com.employeeapp.service.EmployeeService;

@RestController
public class EmployeeRestController {
	
	private EmployeeService employeeService;
	
	/*
	 * Parameterized constructor
	 * to set the value for employeeService 
	 */
	@Autowired
	public EmployeeRestController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	/*
	 * a method to get a list of all the employees
	 * default response is in json form
	 */
	@GetMapping("/rest/employees")
	public List<Employee> getEmployees() {
		return employeeService.getEmployees();
	}
	
}
