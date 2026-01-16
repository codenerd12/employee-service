package com.employee.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.employee.dao.model.Employee;
import com.employee.dao.repository.EmployeeR2dbcRepository;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Repository("employeeDao")
@AllArgsConstructor
public class EmployeeDaoImpl implements EmployeeDao {
	
	private final EmployeeR2dbcRepository employeeR2dbcRepository;

	@Override
	public Mono<List<Employee>> getAllEmployee() {
		return employeeR2dbcRepository.findAll().collectList();
		//return employeeR2dbcRepository.getAllEmployee().collectList();
	}

	@Override
	public Mono<Employee> getEmployee(Long id) {
		return employeeR2dbcRepository.findById(id);
	}

	@Override
	public Mono<Employee> create(Employee employee) {
		/*Mono<StudentDTO> blockingWrapper = Mono.fromCallable(() -> {
			final Mono<StudentDTO> studentDTO = studentR2dbcRepository.save(student).b;
			return studentDTO;
		});
		
		return blockingWrapper.subscribeOn(Schedulers.boundedElastic());*/
		return employeeR2dbcRepository.save(employee);
	}

}
