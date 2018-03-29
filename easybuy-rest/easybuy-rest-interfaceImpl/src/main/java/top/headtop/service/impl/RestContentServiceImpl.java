package top.headtop.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import top.headtop.dao.JedisClient;
import top.headtop.mapper.TbContentMapper;
import top.headtop.pojo.TbContent;
import top.headtop.pojo.TbContentExample;
import top.headtop.pojo.TbContentExample.Criteria;
import top.headtop.service.RestContentService;
import top.headtop.utils.JsonUtils;

@Service
public class RestContentServiceImpl implements  RestContentService{

	@Autowired
	private TbContentMapper contentMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("${INDEX_ADVERTISE_REDIS_KEY}")
	private String INDEX_ADVERTISE_REDIS_KEY;
	
	@Override
	public String getAdvertise() {
		String cachData = jedisClient.hget(INDEX_ADVERTISE_REDIS_KEY, 89+"");
		if(StringUtils.isNotEmpty(cachData)){
			return cachData;
		}
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(89L);
		List<TbContent> list = contentMapper.selectByExample(example);
		ArrayList<Object> advertises = new ArrayList<>();
		for (TbContent content : list) {
			HashMap<Object,Object> map = new HashMap<>();
			map.put("width", 670);
			map.put("height", 240);
			map.put("widthB",550 );
			map.put("heightB",240 );
			map.put("src", content.getPic());
			map.put("srcB",content.getPic2());
			map.put("href", content.getUrl());
			map.put("alt",content.getContent() );
			advertises.add(map);
		}
		String advertisesJson = JsonUtils.objectToJson(advertises);
		jedisClient.hset(INDEX_ADVERTISE_REDIS_KEY, 89+"", advertisesJson);
		return advertisesJson;
	}

	@Override
	public String syscAdvertise() {
		
		String advertisesJson = getAdvertise();
		if(StringUtils.isEmpty(advertisesJson)){
			jedisClient.hset(INDEX_ADVERTISE_REDIS_KEY, 89+"", "");
		}
		jedisClient.expire(INDEX_ADVERTISE_REDIS_KEY, 10);
		return advertisesJson;
	}

}
