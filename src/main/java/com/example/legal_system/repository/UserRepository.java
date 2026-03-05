package com.example.legal_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.legal_system.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {   
	boolean existsByLogin(String login);
}
