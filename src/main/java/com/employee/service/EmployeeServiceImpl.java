package com.employee.service;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.batch.infrastructure.item.validator.ValidationException;
import org.springframework.stereotype.Service;

import com.employee.cache.CacheService;
import com.employee.dao.EmployeeDao;
import com.employee.model.Employee;
import com.employee.model.EmployeeRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service("employeeService")
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl {

	private final CacheService<Employee> cacheService;
	private final EmployeeDao employeeDao;
	
	private final ModelMapper modelMapper;
	
	public Mono<List<Employee>> getAllEmployee() {
		return employeeDao.getAllEmployee()
				.map(list -> list.stream()
						.map(studentDTO -> modelMapper.map(studentDTO, Employee.class))
						.collect(Collectors.toList()));
	}


	public Mono<Employee> getEmployee(Long id) {
		String cacheKey = "EMPLOYEE_" + id;
		return cacheService.get(cacheKey)
				.doOnNext(val -> log.info("Retrive value from cache: {}", val))
				.switchIfEmpty(Mono.defer(() -> getEmployeeDataFromDB(id)
						.flatMap(employee -> {
							if(Objects.nonNull(employee)) {
								log.info("Saved employee data in cache for employee: {}, value: {}", id, employee);
								LocalDateTime today = LocalDateTime.now(ZoneOffset.UTC);
								LocalDateTime nextSunday = today.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
								return cacheService.put(cacheKey, employee, Duration.between(today, nextSunday))
										.thenReturn(employee);
							} else {
								log.error("Could not load employee data from database or api for employee: {}", id);
								return Mono.error(new ValidationException("Either employee details not found"));
							}
						}))
						);
	}
	
	public Mono<Employee> create(EmployeeRequest employeeRequest) {
		return Mono.just(employeeRequest)
				.map(emp -> modelMapper.map(emp, com.employee.dao.model.Employee.class))
				.doOnNext(employeeDTO -> log.info("Employee DETAIL: {} {} {}", employeeDTO.getId(), 
						employeeDTO.getName(), employeeDTO.getDepartment()))
				.flatMap(employeeDTO -> employeeDao.create(employeeDTO)
						.switchIfEmpty(Mono.defer(() -> Mono.empty()))
				)
				.map(employeeDTO -> modelMapper.map(employeeDTO, Employee.class));
	}


	private Mono<Employee> getEmployeeDataFromDB(Long id) {
		return employeeDao.getEmployee(id)
				.map(employeeDTO -> modelMapper.map(employeeDTO, Employee.class));
	}
}
