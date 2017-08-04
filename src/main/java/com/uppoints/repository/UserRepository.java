package com.uppoints.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uppoints.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
