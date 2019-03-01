package com.offcn.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.offcn.po.User;
import com.offcn.service.UserService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/user")
@RefreshScope
public class UserController {

	@Autowired
	UserService userService;
	@Value("${ProviderVersion}")
	private String ProviderVersion;

	/***
	 * 获取全部用户信息
	 * 
	 * @return
	 */
	@GetMapping("/getall")
	@ApiOperation(value = "获取全部用户信息", notes = "获取全部用户信息")
	public Map<String,Object> getUsers() {
		Map<String,Object> map=new HashMap<>();		
		List<User> list = userService.getUserList();
		map.put("list", list);		
		map.put("ProviderVersion", ProviderVersion);
		try {
			//让主线程随机睡眠0--6000毫秒，模拟发生熔断
			Thread.sleep(new Random().nextInt(100));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/***
	 * 保存新增用户接口
	 * 
	 * @param user
	 * @return
	 */
	@PostMapping("/save")
	@ApiOperation(value = "保存新增用户接口", notes = "调用创建用户需要传递json格式User信息")
	@ApiImplicitParam(name = "user", value = "用户json对象", required = true, dataType = "User")
	public String createUser(@RequestBody User user) {
		try {
			userService.createUser(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "error";
		}
		return "success";
	}

	/***
	 * 获取指定id用户信息
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/get/{id}")
	@ApiOperation(value = "获取指定id用户信息", notes = "传递用户id")
	@ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "Long")
	public User findUser(@PathVariable("id") Long id) {
		User user =null;
		try {
			user= userService.getUser(id);
			System.out.println("返回user:"+user);
		} catch (Exception e) {
			
			//e.printStackTrace();
			System.out.println("数据查询失败:");
			return user;
		}
		return user;
	}

	/***
	 * 编辑指定id用户信息
	 * 
	 * @param user
	 * @param id
	 * @return
	 */
	@PutMapping("/update/{id}")
	@ApiOperation(value = "编辑指定id用户信息", notes = "传递用户id")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "Long"),
			@ApiImplicitParam(name = "user", value = "用户json对象", required = true, dataType = "User")

	})
	public String editUser(@RequestBody User user, @PathVariable("id") Long id) {
		try {
			userService.updateUser(user.getId(), user);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return "success";
	}

	/***
	 * 删除指定id用户
	 * 
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "删除指定id用户信息", notes = "传递用户id")
	@ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "Long")
	@DeleteMapping("/delete/{id}")
	public String deleteUser(@PathVariable("id") Long id) {
		try {
			userService.deleteUser(id);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return "success";

	}
	
	/***
	 * 获取服务名称
	 * 
	 * @return
	 */
	@GetMapping("/getversion")
	@ApiOperation(value = "获取服务名称及版本信息", notes = "获取服务名称及版本信息")
	public String getVersion() {
		
		return "用户服务UserProvdier001:0.01V";
	}
}
