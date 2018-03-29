package top.headtop.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import top.headtop.dao.JedisClient;
import top.headtop.mapper.TbItemDescMapper;
import top.headtop.mapper.TbItemMapper;
import top.headtop.mapper.TbItemParamValueMapper;
import top.headtop.pojo.TbItem;
import top.headtop.pojo.TbItemDesc;
import top.headtop.pojo.TbItemParamValue;
import top.headtop.pojo.TbItemParamValueExample;
import top.headtop.pojo.TbItemParamValueExample.Criteria;
import top.headtop.service.RestItemService;
import top.headtop.utils.JsonUtils;

@Service
public class RestItemServiceImpl implements RestItemService {

	@Autowired
	private JedisClient jedisClient;
	@Autowired
	private TbItemMapper tbItemMapper;
	@Autowired
	private TbItemDescMapper tbItemDescMapper;
	@Autowired
	private TbItemParamValueMapper itemParamValueMapper;

	@Value("${REDIS_ITEM_KEY}")
	private String REDIS_ITEM_KEY;
	@Value("${REDIS_ITEM_EXPIRE}")
	private int REDIS_ITEM_EXPIRE;

	@Override
	public String getBaseInfo(long itemId) {
		String cache = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":baseInfo");
		if (StringUtils.isNotEmpty(cache)) {
			return cache;
		}
		TbItem item = tbItemMapper.selectByPrimaryKey(itemId);
		String result = JsonUtils.objectToJson(item);
		jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":baseInfo", result);
		jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":baseInfo", REDIS_ITEM_EXPIRE);
		return result;
	}

	@Override
	public String getItemDesc(long itemId) {
		String cache = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":desc");
		if (StringUtils.isNotEmpty(cache)) {
			return cache;
		}
		TbItemDesc itemDesc = tbItemDescMapper.selectByPrimaryKey(itemId);
		String result = itemDesc.getItemDesc();
		jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":desc", result);
		jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":desc", REDIS_ITEM_EXPIRE);
		return result;
	}

	@Override
	public String getItemParam(long itemId) {
		String cache = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":param");
		if (StringUtils.isNotEmpty(cache)) {
			return  assembleData(cache);
		}
		TbItemParamValueExample example = new TbItemParamValueExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemId);
		List<TbItemParamValue> list = itemParamValueMapper.selectByExampleWithBLOBs(example);
		
		String result = null;
		if (list.size() > 0) {
			TbItemParamValue paramValue = list.get(0);
			String paramData = paramValue.getParamData();
			jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":param", paramData);
			jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":param", REDIS_ITEM_EXPIRE);
			result = assembleData(paramData);
		}
		
		return result;
	}

	private String assembleData(String paramData) {

		List<Map> jsonToList = JsonUtils.jsonToList(paramData, Map.class);
		StringBuilder sb=new StringBuilder();
		sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\">\n");
		sb.append("    <tbody>\n");
		for (Map m1 : jsonToList) {
			sb.append("        <tr>\n");
			sb.append("            <th class=\"tdTitle\" colspan=\"2\">" + m1.get("group") + "</th>\n");
			sb.append("        </tr>\n");
			List<Map> list2 = (List<Map>) m1.get("params");
			for (Map m2 : list2) {
				sb.append("        <tr>\n");
				sb.append("            <td class=\"tdTitle\">" + m2.get("k") + "</td>\n");
				sb.append("            <td>" + m2.get("v") + "</td>\n");
				sb.append("        </tr>\n");
			}
		}
		sb.append("    </tbody>\n");
		sb.append("</table>");
		return sb.toString();
	}

}
