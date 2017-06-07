package com.taotao.protal.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParam;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.protal.pojo.ItemInfo;
import com.taotao.protal.service.ItemService;
@Service
public class ItemServiceImpl implements ItemService {

	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;

	@Value("${ITEM_INFO_URL}")
	private String ITEM_INFO_URL;
	@Value("${ITEM_DESC_URL}")
	private String ITEM_DESC_URL;
	@Value("${ITEM_PARAM_URL}")
	private String ITEM_PARAM_URL;
	
	@Override
	public ItemInfo getItemById(long itemId) {
		try {
			// 调用服务
			String json = HttpClientUtil.doGet(REST_BASE_URL + ITEM_INFO_URL + itemId);
			if (!StringUtils.isBlank(json)) {
				// 装换成对象
				TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, ItemInfo.class);
				if (taotaoResult.getStatus() == 200) {
					ItemInfo info = (ItemInfo) taotaoResult.getData();
					return info;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getItemDesc(long itemId) {
		try {
			String sjon = HttpClientUtil.doGet(REST_BASE_URL+ITEM_DESC_URL+itemId);
			//System.out.println(sjon);
			if (!StringUtils.isBlank(sjon)) {
				TaotaoResult result = TaotaoResult.formatToPojo(sjon, TbItemDesc.class);
				if (result.getStatus()==200) {
					TbItemDesc itemDesc = (TbItemDesc) result.getData();
					String string = itemDesc.getItemDesc();
					
					//System.out.println("gggg");
					return string;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//System.out.println("sddd");
		return null;
	}

	@Override
	public String getItemParamItem(long itemId) {
		try {
			String string = HttpClientUtil.doGet(REST_BASE_URL+ITEM_PARAM_URL+itemId);
			if (!StringUtils.isBlank(string)) {
				TaotaoResult result = TaotaoResult.formatToPojo(string, TbItemParamItem.class);
				if (result.getStatus()==200) {
					TbItemParamItem param =	(TbItemParamItem) result.getData();
					String paramData = param.getParamData();
					System.out.println(paramData);
					//生成HTML
					// 把规格参数json数据转换成java对象
					List<Map> jsonList = JsonUtils.jsonToList(paramData, Map.class);
					StringBuffer sb = new StringBuffer();
					sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\">\n");
					sb.append("    <tbody>\n");
					for(Map m1:jsonList) {
						sb.append("        <tr>\n");
						sb.append("            <th class=\"tdTitle\" colspan=\"2\">"+m1.get("group")+"</th>\n");
						sb.append("        </tr>\n");
						List<Map> list2 = (List<Map>) m1.get("params");
						for(Map m2:list2) {
							sb.append("        <tr>\n");
							sb.append("            <td class=\"tdTitle\">"+m2.get("k")+"</td>\n");
							sb.append("            <td>"+m2.get("v")+"</td>\n");
							sb.append("        </tr>\n");
						}
					}
					sb.append("    </tbody>\n");
					sb.append("</table>");
					//返回html片段
					System.out.println(sb.toString());
					return sb.toString();

				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "规格参数不存在！";
	}

}
