package com.taotao.protal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.protal.pojo.CartItem;
import com.taotao.protal.service.CartService;
import com.taotao.protal.service.ContentService;

@Controller
public class ShowIndexController {
	@Autowired
	private ContentService contentService;
    @Autowired
	private CartService cartService;
    
	@RequestMapping("/index")
	public String showIndex(Model model){
		String list = contentService.getContentList();
		model.addAttribute("ad1",list);
		return "index";
		
	}
	@RequestMapping("/order/order-cart")
	public String showOrder(HttpServletRequest request,HttpServletResponse response,Model model){
		List<CartItem> cartItemList = cartService.getCartItemList(request, response);
		model.addAttribute("cartList",cartItemList);
		
		return "order-cart";
		
	}
}
