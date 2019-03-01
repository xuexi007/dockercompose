package com.offcn.service;

import java.util.List;
import java.util.Map;

import com.offcn.po.User;

public interface UserService {
 
 public Map<String,Object> getUserMap();
 
 public void createUser(User user);
 
 public User getUser(Long id);
 
 public void updateUser(Long id,User user);
 
 public void deleteUser(Long id);
 
}
