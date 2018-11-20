package com.localvideo.dev.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.localvideo.dev.service.VideoHelpService;
import com.localvideo.dev.util.Page;
import com.localvideo.dev.util.ResponseJsonUtils;


@Controller
@RequestMapping("/user")
public class UserController {

	/*@Autowired
	UserService userService;
	
	*/
	@Autowired
	VideoHelpService video;
	/**
	 * 返回JSON数据
	 * 登录
	 * @param request
	 * @param response
	 *//*
	@RequestMapping(value = "/userLogin", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public void userLogin(HttpServletRequest request, HttpServletResponse response) {
		
		String userName=request.getParameter("userName");
		String passWord=request.getParameter("passWord");
		List<Map<String, Object>> data=userService.userLogin(userName, passWord);
		ResponseJsonUtils.json(response, data);
	}
	
	*//**
	 * 返回JSON数据
	 * 注册
	 * @param request
	 * @param response
	 *//*
	@RequestMapping(value = "/userReg", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public void userRegister(HttpServletRequest request, HttpServletResponse response) {
		
		String username=request.getParameter("userName");
		String password=request.getParameter("passWord");
		int data=userService.userRegister(username, password);
		ResponseJsonUtils.json(response, data);
	}
	
	*//**
	 * 
	 * 继电器传感器数据采集
	 * @param request
	 * @param response
	 *//*
	@RequestMapping(value = "/relay_", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public void insertRelay(HttpServletRequest request, HttpServletResponse response) {
		
		int userId=Integer.parseInt(request.getParameter("userId"));
		int deviceNum=Integer.parseInt(request.getParameter("deviceNum"));
		int status=Integer.parseInt(request.getParameter("status"));
		int data=userService.addRelayDevice(userId, deviceNum, status);
		ResponseJsonUtils.json(response, data);
	}
	*//**
	 * 
	 * 光敏传感器数据采集
	 * @param request
	 * @param response
	 *//*
	@RequestMapping(value = "/photosensitive", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public void insertPhotosensitive(HttpServletRequest request, HttpServletResponse response) {
		
		int userId=Integer.parseInt(request.getParameter("userId"));
		String value=request.getParameter("lightVal");
		float m=Float.parseFloat(value);
		BigDecimal bd = new BigDecimal(m/10) ;
	    float lightValue = bd.floatValue();
		int data=userService.addLightDevice(userId,lightValue);
		ResponseJsonUtils.json(response, data);
	}
	
	*//**
	 * 
	 * 光敏传感器数据采集
	 * @param request
	 * @param response
	 *//*
	@RequestMapping(value = "/shock", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public void insertshock(HttpServletRequest request, HttpServletResponse response) {
		
		int userId=Integer.parseInt(request.getParameter("userId"));
		int checkstatus=Integer.parseInt(request.getParameter("checkstatus"));
		int data=userService.addShockDevice(userId,checkstatus);
		ResponseJsonUtils.json(response, data);
	}
	*/
	
	/**
	 * 返回JSON数据
	 * 登录
	 * @param request
	 * @param response
	 *//*
	@RequestMapping(value = "/userInfo", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public void getUserInfo(HttpServletRequest request, HttpServletResponse response) {
		
		String userId=request.getParameter("id");
		List<Map<String, Object>> data=userService.getUserInfo(userId);
		ResponseJsonUtils.json(response, data);
	}*/
	/**
	 * 返回JSON数据
	 * 登录
	 * @param request
	 * @param response
	 */
	
	@RequestMapping(value = "/test", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public void getUserInfo(HttpServletRequest request, HttpServletResponse response,Page page) {
		ResponseJsonUtils.json(response, video.list(page));
	}
	
}
