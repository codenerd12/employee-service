package com.employee.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
public class EmployeeResponse {
	
	private List<Employee> students;
	

}
