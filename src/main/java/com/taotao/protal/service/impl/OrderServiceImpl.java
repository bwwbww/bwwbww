package com.taotao.protal.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.protal.pojo.Order;
import com.taotao.protal.service.OrderService;
@Service
public class OrderServiceImpl implements OrderService {

	@Value("${ORDER_BASE_URL}")
	private String ORDER_BASE_URL;
	@Value("${ORDER_CREATE_URL}")
	private String ORDER_CREATE_URL;
	
	@Override
	public String createOrder(Order order) {
		//调用order服务
		try {
			String json = HttpClientUtil.doPostJson(ORDER_BASE_URL+ORDER_CREATE_URL, JsonUtils.objectToJson(order));
			//吧json转换成taotaoresult
			TaotaoResult result = TaotaoResult.format(json);
			if (result.getStatus()==200) {
				Object data = result.getData();
				//System.out.println(data.toString());
				return data.toString();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

}
