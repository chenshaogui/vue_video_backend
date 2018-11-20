package com.localvideo.dev.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.localvideo.dev.service.VideoHelpService;
import com.localvideo.dev.util.Page;
import com.localvideo.dev.util.ResponseJsonUtils;


@Controller
@RequestMapping("/api")
@CrossOrigin(origins="*")
public class LocalVideoController {
	@Autowired
	VideoHelpService video;
	
	/**
	 * 查询分页数据
	 * @param request
	 * @param response
	 */
	
	@RequestMapping(value = "/findByPage", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	@CrossOrigin(origins="*")
	public void FindByPage(HttpServletRequest request,String callback,String value, HttpServletResponse response,Page page) {
		Map<String, Object> result=new HashMap<>();
		result.put("status", 200);
		//result.put("data", video.findByPage(page,value));
		result.put("data", video.findByPage(page,value));
		result.put("page", page);
		ResponseJsonUtils.jsonp(response,callback, result);
	}
	
	/**
	 * 查询net分页数据
	 * @param request
	 * @param response
	 */
	
	@RequestMapping(value = "/net/findByPage", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	@CrossOrigin(origins="*")
	public void FindNetByPage(HttpServletRequest request,String callback,String value, HttpServletResponse response,Page page) {
		Map<String, Object> result=new HashMap<>();
		result.put("status", 200);
		//result.put("data", video.findByPage(page,value));
		result.put("data", video.findNetByPage(page,value));
		result.put("page", page);
		ResponseJsonUtils.jsonp(response,callback, result);
	}
	
	/**
	 * 查询所有数据
	 * @param request
	 * @param response
	 */
	
	@RequestMapping(value = "/findAll", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public void FindAll(HttpServletRequest request,String callback, HttpServletResponse response) {
		ResponseJsonUtils.jsonp(response,callback, video.findall());
	}
	
	
	/**
	 * 得到总集合大小
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/collSize", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public void CollSize(HttpServletRequest request, HttpServletResponse response) {
		ResponseJsonUtils.json(response, video.getCollectionSize());
	}
	
}
