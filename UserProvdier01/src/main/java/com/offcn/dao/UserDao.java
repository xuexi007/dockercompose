package com.offcn.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.offcn.po.User;
@Component
public interface UserDao extends JpaRepository<User, Long> {

	
}
