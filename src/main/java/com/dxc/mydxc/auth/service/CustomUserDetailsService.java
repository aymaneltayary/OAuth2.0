/**
 * 
 */
package com.dxc.mydxc.auth.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dxc.mydxc.auth.entity.RoleEnity;
import com.dxc.mydxc.auth.entity.UserEntity;
import com.dxc.mydxc.auth.repo.UserRepository;

/**
 * @author aeltayary
 *
 */
@Service
@Transactional
public class CustomUserDetailsService implements  UserDetailsService {

	@Autowired
	private UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserEntity> userOtional=userRepo.findByEmail(username);
		
		if (userOtional.isPresent()){
			UserEntity userEntity=userOtional.get();
			RoleEnity userRole=userEntity.getRole();
			SimpleGrantedAuthority auth= new SimpleGrantedAuthority(userRole.getRoleName());
			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			authorities.add(auth);
			return new User(userEntity.getEmail(), userEntity.getPassword(), authorities);
		}else {
			throw new UsernameNotFoundException("user is not found");
		}

	}

}
