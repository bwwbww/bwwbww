package com.taotao.protal.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbItem;
import com.taotao.protal.pojo.CartItem;
import com.taotao.protal.service.CartService;

@Service
public class CartServiceImpl implements CartService {
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${ITEM_INFO_URL}")
	private String ITEM_INFO_URL;

	@Override
	public TaotaoResult addCartItem(long itemId, Integer num, HttpServletRequest request,
			HttpServletResponse response) {

		CartItem cartItem = null;
		List<CartItem> Cartlist = getCartItemList(request);
		for (CartItem cartItem2 : Cartlist) {
			// 判断是否存在此商品
			if (cartItem2.getId() == itemId) {
				cartItem2.setNum(cartItem2.getNum() + num);
				cartItem = cartItem2;
				break;
			}
		}
		if (cartItem == null) {
			//创建对象
			cartItem = new CartItem();
			// 根据ID取商品信息
			String json = HttpClientUtil.doGet(REST_BASE_URL + ITEM_INFO_URL + itemId);
			TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, TbItem.class);
			if (taotaoResult.getStatus() == 200) {
				TbItem item = (TbItem) taotaoResult.getData();
				cartItem.setId(item.getId());
				cartItem.setImage(item.getImage() == null ? "" : item.getImage().split(",")[0]);
				cartItem.setPrice(item.getPrice());
				System.out.println(num);
				System.out.println(item.getNum());
				cartItem.setNum(num);
				cartItem.setTitle(item.getTitle());
			}
			// 吧商品添加到购物车列表
			Cartlist.add(cartItem);

		}
		// 吧购物车写入cookie
		CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(Cartlist), true);
		return TaotaoResult.ok();

	}

	private List<CartItem> getCartItemList(HttpServletRequest request) {
		// 从cookie中取商品列表
		String cookieValue = CookieUtils.getCookieValue(request, "TT_CART", true);
		if (cookieValue == null) {
			return new ArrayList<>();
		}
		try {
			List<CartItem> list = JsonUtils.jsonToList(cookieValue, CartItem.class);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

	public List<CartItem> getCartItemList(HttpServletRequest request, HttpServletResponse response) {
		List<CartItem> cartItemList = getCartItemList(request);
		return cartItemList;
	}

	@Override
	public TaotaoResult deleteCartItem(long itemId, HttpServletRequest request, HttpServletResponse response) {
		//从cookie中取购物车商品列表
		List<CartItem> itemList = getCartItemList(request);
		for (CartItem cartItem : itemList) {
		if (cartItem.getId()==itemId) {
			itemList.remove(cartItem);
			break;
			
		}			
			}
		//把购物车列表重新写入cookie
		CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(itemList));		
		return TaotaoResult.ok();
	}
}
