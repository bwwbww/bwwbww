package com.taotao.protal.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.protal.pojo.SearchResult;
import com.taotao.protal.service.SearchService;

@Controller
public class SearchController {

	@Autowired
	private SearchService searchService;
	@RequestMapping("/search")
	public String search(@RequestParam("q") String queryString,@RequestParam(defaultValue="1") Integer page,Model model){
		//querystring处理乱码
		if (queryString!=null) {
			try {
				queryString = new String(queryString.getBytes("iso8859-1"),"utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}	
		SearchResult search = searchService.search(queryString, page);
		model.addAttribute("totalPages", search.getPageCount());
		model.addAttribute("page",page);
		model.addAttribute("query", queryString);
		model.addAttribute("itemList", search.getItemList());
		
		return "search";
		
	}
}
