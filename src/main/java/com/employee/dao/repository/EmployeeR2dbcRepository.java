package com.employee.dao.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

import com.employee.dao.model.Employee;
import com.employee.dao.query.constants.DatabaseQueriesConstants;

import reactor.core.publisher.Flux;

public interface EmployeeR2dbcRepository extends R2dbcRepository<Employee, Long>{
	
	@Query(DatabaseQueriesConstants.EMPLOYEE_LIST)
	Flux<Employee> getAllEmployee();

}
