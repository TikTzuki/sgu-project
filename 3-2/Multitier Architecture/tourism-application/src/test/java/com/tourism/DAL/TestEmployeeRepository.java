package com.tourism.DAL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.tourism.DAL.EmployeeRepository;
import com.tourism.DTO.Employee;

import junit.framework.TestCase;

public class TestEmployeeRepository extends TestCase {
	public TestEmployeeRepository(String name) {
		super(name);
	}
	
	public void testFind() {
		List<Employee> employees = new ArrayList<Employee>();
		List<Long> ids = 
				new ArrayList<Long>(Arrays.asList(new Long[] {Long.valueOf(1), Long.valueOf(2)}));
		employees = new EmployeeRepository().findAllById(ids);
		assertNotNull(ids.get(0));
	}
}
