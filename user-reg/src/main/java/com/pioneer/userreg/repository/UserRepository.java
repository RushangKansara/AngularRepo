package com.pioneer.userreg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.pioneer.userreg.domain.UserDTO;

@Component
public interface UserRepository extends JpaRepository<UserDTO, Long> {
	UserDTO findById(Long id);
	UserDTO findByName(String name);
}
