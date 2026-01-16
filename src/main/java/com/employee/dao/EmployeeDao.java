package com.employee.dao;

import java.util.List;

import com.employee.dao.model.Employee;

import reactor.core.publisher.Mono;

public interface EmployeeDao {
	
	Mono<List<Employee>> getAllEmployee();

	Mono<Employee> getEmployee(Long id);

	Mono<Employee> create(Employee student);

}
