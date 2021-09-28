package com.tourism.DAL;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

public class TestCustomerRepository extends TestCase {
	CustomerRepository customerRepos = new CustomerRepository();
	
	//Pass
	public void testFindAll() {
		customerRepos.findAll().forEach(System.out::println);
	}
	
	//Pass
	public void testFindAllById() {
		List<Long> ids = new ArrayList<Long>();
		ids.add(Long.valueOf(1));
		ids.add(Long.valueOf(2));
		customerRepos.findAllById(ids).forEach(System.out::println);
	}

	//Pass
	public void testFindAllByTouristGroupId() {
		customerRepos.findAllByTouristGroupId(Long.valueOf(1)).forEach(System.out::println);;
	}
}
