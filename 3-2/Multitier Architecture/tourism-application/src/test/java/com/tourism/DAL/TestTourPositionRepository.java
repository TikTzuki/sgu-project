package com.tourism.DAL;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.tourism.ProjectProperties;

import junit.framework.TestCase;

public class TestTourPositionRepository extends TestCase {
	TourPositionRepository TPRepos = new TourPositionRepository();
	Logger logger = Logger.getLogger("MyLog");
	
	public TestTourPositionRepository() {

	}
	
//	public void testFindAll() {
//		TPRepos.findAll().forEach(System.out::println);
//	}
	
	public void testFindAllByTouristGroupId() {
		TPRepos.findAllByTouristGroupId(Long.valueOf(1)).forEach(System.out::println);
	
	}
	
	public void testFindAllById() {
		List<Long> ids = new ArrayList<>();
		ids.add(Long.valueOf(1));
		logger.info(TPRepos.findAllById(ids).toString());
	}
}
