package com.taotao.protal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.protal.pojo.CartItem;
import com.taotao.protal.service.CartService;

@Controller
@RequestMapping("/cart")
public class CartController {
	@Autowired
	private CartService cartService;

	@RequestMapping("/add/{itemId}")
	public String addCart(@PathVariable long itemId, @RequestParam(defaultValue = "1") Integer num,
			HttpServletRequest request, HttpServletResponse response) {
		TaotaoResult addCartItem = cartService.addCartItem(itemId, num, request, response);

		return "redirect:/cart/success.html";

	}
	@RequestMapping("/success")
	public String showSuccess(){
		return "cartSuccess";
	}
	
    @RequestMapping("/cart")
	public String showCart(HttpServletRequest request, HttpServletResponse response,Model model){
    	List<CartItem> cartItemList = cartService.getCartItemList(request, response);
    	model.addAttribute("cartList",cartItemList);
		return "cart";
		
	}
    @RequestMapping("/delete/{itemId}")
    public String deleteCart(@PathVariable long itemId,HttpServletRequest request, HttpServletResponse response){
    	cartService.deleteCartItem(itemId, request, response);
    	return "redirect:/cart/cart.html";
    }
}
