package com.tourism.BUS;

import java.util.Arrays;
import java.util.Optional;

import com.tourism.DAL.UserRepository;
import com.tourism.DTO.User;

public class UserController {
	UserRepository userRepository;
	public UserController() {
		userRepository = new UserRepository();
		
	}
	
	public User login(String phoneNumber,  char[] password) {
		Optional<User> opt = userRepository.findByPhone(phoneNumber);
		if(opt.isPresent() && Arrays.equals(password, opt.get().getPassword().toCharArray())) {
			return opt.get();
		} else {
			return null;
		}
	}
}
