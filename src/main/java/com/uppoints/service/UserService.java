package com.uppoints.service;

import java.util.List;

import com.uppoints.model.User;

public interface UserService {

	
	List<User> list();
	
	User getUser(Long id);
	
	User save(User user);
	
	User update(User user);
	
	void delete(Long id);
}
