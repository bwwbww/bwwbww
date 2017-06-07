package com.taotao.protal.controller;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.pojo.TbUser;
import com.taotao.protal.pojo.Order;
import com.taotao.protal.service.OrderService;

@Controller
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@RequestMapping("/order/create")
	public String createOrder(Order order,Model model,HttpServletRequest request){
		//获取request中的信息
		TbUser user = (TbUser) request.getAttribute("user");
		order.setUserId(user.getId());
		order.setBuyerNick(user.getUsername());
		String orderId = orderService.createOrder(order);
		model.addAttribute("orderId",orderId);
		model.addAttribute("payment",order.getPayment());
		model.addAttribute("date",new DateTime().plusDays(3).toString("yyyy-MM-dd"));
		return "success";
		
	}
}
