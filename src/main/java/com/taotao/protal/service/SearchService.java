package com.taotao.protal.service;

import com.taotao.protal.pojo.SearchResult;

public interface SearchService {

	public SearchResult search(String queryString,Integer page);
}
