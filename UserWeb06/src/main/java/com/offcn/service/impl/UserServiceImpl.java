package com.offcn.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.offcn.po.User;
import com.offcn.service.UserService;
@Service
public class UserServiceImpl implements UserService {

	//远程服务调用客户端
	@Autowired
	RestTemplate restTemplate;	
	@Value("${gateway}")
	private String gatewayUrl;
	
	/***
	 * 通过zuul 网关地址获取接口地址
	 * @return
	 */
	public String getServerUrl() {		
		String url="http://"+gatewayUrl+"/service/user";
		return url;
	}
	
	
	@Override
	public Map<String, Object> getUserMap() {
		
		Map<String,Object> map = restTemplate.getForObject(getServerUrl()+"/getall?accesstoken=token", Map.class);
		
		return map;
	}
	
	
	@Override
	public void createUser(User user) {
		
		restTemplate.postForObject(getServerUrl()+"/save?accesstoken=token", user,String.class);
	   
	}

	@Override
	public User getUser(Long id) {
		
		return restTemplate.getForObject(getServerUrl()+"/get/"+id+"?accesstoken=token", User.class);
	}

	@Override
	public void updateUser(Long id, User user) {
		restTemplate.put(getServerUrl()+"/update/"+id+"?accesstoken=token", user);

	}

	@Override
	public void deleteUser(Long id) {
		restTemplate.delete(getServerUrl()+"/delete/"+id+"?accesstoken=token");

	}
	
	
	

}
