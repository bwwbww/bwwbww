package com.taotao.protal.service.impl;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.protal.pojo.SearchResult;
import com.taotao.protal.service.SearchService;
@Service
public class SearchServiceImpl implements SearchService {

	@Value("${SEARCH_BASE_URL}")
	private String SEARCH_BASE_URL;
	
	@Override
	public SearchResult search(String queryString, Integer page) {
		//调用seach的服务
		HashMap<String,String> param = new HashMap<>();
		param.put("q", queryString);
		param.put("page", page+"");
		try {
			String string = HttpClientUtil.doGet(SEARCH_BASE_URL,param);
			//将字符串转对象
			TaotaoResult result = TaotaoResult.formatToPojo(string, SearchResult.class);
			if (result.getStatus()==200) {
				SearchResult searchResult = (SearchResult) result.getData();
				return searchResult;
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
