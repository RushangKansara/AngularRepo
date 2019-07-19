package com.pioneer.userreg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pioneer.userreg.domain.AppUser;
import com.pioneer.userreg.repository.AppUserRepository;

@Service
public class AppUserServiceImpl implements UserDetailsService{

	@Autowired
	private AppUserRepository appUserRepository;
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		AppUser appUser = appUserRepository.findByUserName(username);
		
		if(appUser == null) {
			throw new UsernameNotFoundException("Username not found.");
		}
		
		return new User(appUser.getUserName(),appUser.getPassword(),AuthorityUtils.createAuthorityList(appUser.getRole()));
	}

}
