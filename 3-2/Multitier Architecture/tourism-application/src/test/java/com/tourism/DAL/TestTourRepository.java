package com.tourism.DAL;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.tourism.ProjectProperties;

import junit.framework.TestCase;

public class TestTourRepository extends TestCase {
	Logger logger = Logger.getLogger(getClass().getName());
	TourRepository tourRepository;
	public TestTourRepository() {
		tourRepository = new TourRepository();
//		logger.addHandler(ProjectProperties.getFileHandlerLogger());
	}
	
	public void testFindAllById() {
		List<Long> ids = new ArrayList<Long>();
		ids.add(Long.valueOf(1));
		logger.info(tourRepository.findAllById(ids).toString());
	}
}
