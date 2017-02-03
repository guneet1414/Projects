package com.employeeapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.employeeapp.entity.Employee;
import com.employeeapp.service.EmployeeService;

/*
 * Defining controller for home page
 * A normal mvc controller
 */

@Controller
public class HomeController {
	
	private EmployeeService employeeService;
	
	/*
	 * Parameterized constructor to set the value
	 * of employeeService
	 */
	@Autowired
	public HomeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	/*
	 * Mapping to handle the root context of application
	 * or "/" or to display the home page. This method sets 
	 * a list of all employees as model value
	 */
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String home(Model model) {
		
		//setting model attribute
		model.addAttribute("emplist", employeeService.getEmployees());
		
		return "index";
	}
	
	/*
	 * Method to delete a employee from database
	 * The employee id is taken as a path variable
	 */
	@RequestMapping(value="/delete/{Id}", method=RequestMethod.GET)
	public String delete(@PathVariable("Id")String Id, Model model) {
		
		//first retrieving emplyee by id
		Employee e = employeeService.getEmployeeById(Id);
		
		//and then removing the record
		employeeService.remove(e);
		
		//refreshing the list
		model.addAttribute("emplist", employeeService.getEmployees());
		
		return "index";
	}
	
	/*
	 * method to get a request for add page
	 */
	@RequestMapping(value="/add", method=RequestMethod.GET)
	public String add(Model model) {
		
		//set a new employee object in model
		//which will be used for storing values 
		//given by user
		model.addAttribute("employee", new Employee());
		return "add";
	}
	
	/*
	 * method to save the employee
	 */
	@RequestMapping(value="/saveEmployee", method=RequestMethod.POST)
	public String saveEmployee(@ModelAttribute("employee")Employee employee, Model model) {
		
		//saving employee
		employeeService.save(employee);
		
		//refreshing list of employee to be displayed on index page
		model.addAttribute("emplist", employeeService.getEmployees());
		
		return "index";
	}
}
