package com.edu.portal.service.impl;

import com.edu.bean.TbItem;
import com.edu.bean.TbItemDesc;
import com.edu.bean.TbItemParamItem;
import com.edu.common.bean.ShoppingResult;
import com.edu.common.util.HttpClientUtil;
import com.edu.common.util.JsonUtils;
import com.edu.portal.bean.ItemCustomer;
import com.edu.portal.service.ItemService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ItemServiceImpl implements ItemService {
    @Value("${CONTENT_BASE_URL}")
    private String CONTENT_BASE_URL;
    @Value("${ITEM_BASE_URL}")
    private String ITEM_BASE_URL;
    @Value("${ITEM_BASE_DESC}")
    private String ITEM_BASE_DESC;
    @Value("${ITEM_BASE_PARAM}")
    private String ITEM_BASE_PARAM;

    @Override
    public ItemCustomer getInfo(long itemId) {
        ItemCustomer itemCustomer = null;
        String itemInfo = HttpClientUtil.doGet(CONTENT_BASE_URL + ITEM_BASE_URL + itemId);
        ShoppingResult shoppingResult = ShoppingResult.formatToPojo(itemInfo, TbItem.class);
        if (shoppingResult.getStatus()==200){
            TbItem tbItem = (TbItem)shoppingResult.getData();
            if (null != tbItem){
                itemCustomer = new ItemCustomer();
                BeanUtils.copyProperties(tbItem,itemCustomer);
                return itemCustomer;
            }
        }
        return null;
    }

    @Override
    public String getDesc(long itemId) {
        TbItemDesc itemDesc = null;
        String itemInfo = HttpClientUtil.doGet(CONTENT_BASE_URL + ITEM_BASE_DESC + itemId);
        ShoppingResult shoppingResult = ShoppingResult.formatToPojo(itemInfo, TbItemDesc.class);
        if (shoppingResult.getStatus()==200){
            itemDesc = (TbItemDesc)shoppingResult.getData();
            if (null != itemDesc){
                return itemDesc.getItemDesc();
            }
        }
        return null;
    }

    @Override
    public String getParam(long itemId) {
        StringBuffer sb = new StringBuffer();
        TbItemParamItem itemParamItem = null;
        String itemInfo = HttpClientUtil.doGet(CONTENT_BASE_URL + ITEM_BASE_PARAM + itemId);
        ShoppingResult shoppingResult = ShoppingResult.formatToPojo(itemInfo, TbItemParamItem.class);
        if (shoppingResult.getStatus()==200){
            itemParamItem = (TbItemParamItem)shoppingResult.getData();
            if (null != itemParamItem){
                String paramItem = itemParamItem.getParamData();
                List<Map> maps =JsonUtils.jsonToList(paramItem, Map.class);
                sb.append("<table class=\"tm-tableAttr\">\n");
                sb.append("	<thead>\n");
                sb.append("		<tr>\n");
                sb.append("			<td colspan=\"2\" data-spm-anchor-id=\"a220o.1000855.0.i0.66b829003o3V0f\">规格参数</td>\n");
                sb.append("		</tr>\n");
                sb.append("	</thead>\n");
                sb.append("	<tbody>\n");
                for(Map map :maps){
                   String group = (String)map.get("group");
                    sb.append("		<tr class=\"tm-tableAttrSub\">\n");
                    sb.append("			<th colspan=\"2\" data-spm-anchor-id=\"a220o.1000855.0.i2.66b829003o3V0f\">"+group+"</th>\n");
                    sb.append("		</tr>\n");
                   List<Map> childrens = (List<Map>) map.get("params");
                    for(Map child :childrens){
                        String key =(String)child.get("k");
                        String value = (String)child.get("v");
                        sb.append("		<tr>\n");
                        sb.append("			<th data-spm-anchor-id=\"a220o.1000855.0.i1.66b829003o3V0f\">"+key+"</th>\n");
                        sb.append("			<td data-spm-anchor-id=\"a220o.1000855.0.i4.66b829003o3V0f\">&nbsp;"+value+"</td>\n");
                        sb.append("		</tr>\n");
                    }
                }
                sb.append("	</tbody>\n");
                sb.append("</table>");
            }
        }
        return sb.toString();
    }
}
