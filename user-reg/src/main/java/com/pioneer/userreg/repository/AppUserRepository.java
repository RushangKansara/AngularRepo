package com.pioneer.userreg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pioneer.userreg.domain.AppUser;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
	public AppUser findByUserName(String userName);
}
