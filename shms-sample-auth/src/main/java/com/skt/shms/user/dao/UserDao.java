package com.skt.shms.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.skt.shms.user.model.User;

@Repository ("UserDao")
public interface UserDao extends JpaRepository<User, Integer> {

	User findByUsername(String username);

}
