package com.taotao.protal.service;

import com.taotao.pojo.TbItemDesc;
import com.taotao.protal.pojo.ItemInfo;

public interface ItemService {
      ItemInfo getItemById(long itemId);
      String getItemDesc(long itemId);
      String getItemParamItem(long itemId);
      
}
