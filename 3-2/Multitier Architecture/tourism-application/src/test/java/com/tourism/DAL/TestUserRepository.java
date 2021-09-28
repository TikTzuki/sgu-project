package com.tourism.DAL;

import junit.framework.TestCase;

public class TestUserRepository extends TestCase {
	UserRepository userRepository = new UserRepository();
	public void testFindByPhoneNumberAndPassword() {
		assertNotNull( userRepository.findByPhone("0858267296").get() );
	}
}
