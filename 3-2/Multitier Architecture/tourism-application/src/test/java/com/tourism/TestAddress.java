
package com.tourism;

import com.tourism.service.JsonAddress;

import junit.framework.TestCase;

public class TestAddress extends TestCase {
	JsonAddress addressService = new JsonAddress();
	public TestAddress(String name) {
		super(name);
	}
	
	public void testGetAddressLv2() {

		assertNotNull(addressService);
		addressService.getAddressLv2("Thành phố Hà Nội").forEach(System.out::println);
	}
	
	public void testGetAddressFull() {
		addressService.getAddressLv2("Thành phố Hồ Chí Minh");
		addressService.getAddressLV3("Thành phố Hồ Chí Minh", "");
	}
}
