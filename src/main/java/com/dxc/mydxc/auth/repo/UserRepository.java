/**
 * 
 */
package com.dxc.mydxc.auth.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.dxc.mydxc.auth.entity.UserEntity;

/**
 * @author aeltayary
 *
 */
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

public Optional<UserEntity> findByEmail(String userEmail); 


}
