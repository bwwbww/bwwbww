package com.taotao.protal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.pojo.TbItemDesc;
import com.taotao.protal.pojo.ItemInfo;
import com.taotao.protal.service.ItemService;

@Controller
public class ItemController {

	@Autowired
	private ItemService itemService;
	
	@RequestMapping(value="/item/{itemId}")
	public String showItem(@PathVariable long itemId,Model model){
		
		ItemInfo itemInfo = itemService.getItemById(itemId);
		//System.out.println(itemInfo);
		model.addAttribute("item",itemInfo);
		//逻辑视图 不要responsebody
		return "item";		
	}
	
	@RequestMapping(value="/item/desc/{itemId}",produces=MediaType.TEXT_HTML_VALUE+";charset=utf-8")
	@ResponseBody
	public String showItemDesc(@PathVariable long itemId){		
            String desc = itemService.getItemDesc(itemId);	
            //System.out.println(desc);
		    return desc;		
	}
	
	@RequestMapping(value="/item/param/{itemId}",produces=MediaType.TEXT_HTML_VALUE+";charset=utf-8")
	@ResponseBody
	public String showItemParam(@PathVariable long itemId){		
		String param = itemService.getItemParamItem(itemId);
		System.out.println(param);
		return param;		
	}
	
	
}
