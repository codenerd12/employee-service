package com.employee.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.config.EnableWebFlux;

import com.employee.model.Employee;
import com.employee.service.EmployeeServiceImpl;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
@EnableWebFlux
public class EmployeeController {
	
	private final EmployeeServiceImpl employeeService;
	
	@GetMapping("/test")
	public Mono<ResponseEntity<Employee>> getEmployee() {
		System.out.println("----->Hello");
		Employee employee = Employee.builder()
				.id(1l)
				.name("manish")
				.department("IT")
				.build();
		return Mono.just(new ResponseEntity<>(employee, HttpStatus.OK));
	}
	
	@GetMapping("/employee")
	public Mono<ResponseEntity<Mono<List<Employee>>>> getAllEmployee() {
		return Mono.just(new ResponseEntity<>(employeeService.getAllEmployee(), HttpStatus.OK));
	}
	
	@GetMapping("/employee/{id}")
	public Mono<ResponseEntity<Mono<Employee>>> getEmployee(@PathVariable("id") Long id) {
		return Mono.just(new ResponseEntity<>(employeeService.getEmployee(id), HttpStatus.OK));
	}

}
