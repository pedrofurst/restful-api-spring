package com.uppoints.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uppoints.model.User;
import com.uppoints.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository repository;

	@Override
	public List<User> list() {
		return repository.findAll();
	}

	@Override
	public User getUser(Long id) {
		return repository.findOne(id);
	}

	@Override
	public User save(User user) {
		return repository.save(user);
	}

	@Override
	public User update(User user) {
		return save(user);
	}

	@Override
	public void delete(Long id) {
		repository.delete(id);
	}

}
