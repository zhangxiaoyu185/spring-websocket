package com.xiaoyu.lingdian.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/")
public class IndexController {

	/**
	 * 跳转到首页
	 **/
	@RequestMapping("")
	public ModelAndView index() {
		ModelAndView model = new ModelAndView("index");
		return  model;
	}

}