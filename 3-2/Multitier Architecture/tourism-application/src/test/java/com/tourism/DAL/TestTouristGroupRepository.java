package com.tourism.DAL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.tourism.DAL.TouristGroupRepository;
import com.tourism.DTO.Customer;
import com.tourism.DTO.Employee;
import com.tourism.DTO.Tour;
import com.tourism.DTO.TourPosition;
import com.tourism.DTO.TouristGroup;

import junit.framework.TestCase;

public class TestTouristGroupRepository extends TestCase{
	TouristGroupRepository TGRepos = new TouristGroupRepository();
	
	public TestTouristGroupRepository(String name) {
		super(name);
	}
	
	public void testFindAll() {
		List<TouristGroup> TGs = TGRepos.findAll();;
		TouristGroup TG = TGs.get(0);
		TGs.forEach(System.out::println);
		assertNotNull(TGs);
	}

//	public void testInsertTouristGroup() {
//		TouristGroupRepository touristGroupRepos = new TouristGroupRepository();
//		
//		TouristGroup tg = new TouristGroup("name", new Date(), new Date(), "description",
//				Double.valueOf(300000), Double.valueOf(50000), Double.valueOf(130000), Double.valueOf(12300000));
//		//Customer customer = new Customer("customer name", "311842023", "customer address", "male", "340545345");
//		//Customer customer2 = new Customer("customer2 name", "3525445", "customer2 address", "female", "453463456");
//		//tg.setCustomers(new ArrayList<Customer>(Arrays.asList(new Customer[] {customer,customer2})));
//		tg = touristGroupRepos.save(tg);
//		
//		assertNotNull("Customer not null", tg.getCustomers().get(0));
//		assertNotNull("Customer 2 not null", tg.getCustomers().get(1));
//		assertNotNull("Tour not null", tg.getTour());
//	}
//	
//	public void testNullInName() {
//		TourPosition tp = new TourPosition();
//		//tp.setEmployee(new Employee());
//		assertNotNull(tp.getEmployee());
//	}
}