package top.headtop.service.impl;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateNotFoundException;
import top.headtop.pojo.BaseInfo;
import top.headtop.service.GeneratePageService;
import top.headtop.service.RestItemService;
import top.headtop.utils.JsonUtils;

@Service
public class GeneratePageServiceImpl implements GeneratePageService{

	@Autowired
	private FreeMarkerConfigurer configurer;
	@Autowired
	private RestItemService restItemService;
	
	@Value("${STATIC_PATH}")
	private String STATIC_PATH;
	
	@Override
	public void genratePage(long itemId) {
		String baseInfo = restItemService.getBaseInfo(itemId);
		BaseInfo item = JsonUtils.jsonToPojo(baseInfo, BaseInfo.class);
		String itemDesc = restItemService.getItemDesc(itemId);
		String itemParam = restItemService.getItemParam(itemId);
		Configuration configuration = configurer.getConfiguration();
		try {
			Template template = configuration.getTemplate("item.ftl");
			Map<Object,Object> map = new HashMap<>();
			map.put("item", item);
			map.put("itemDesc", itemDesc);
			map.put("itemParam", itemParam);
			
			Writer writer = new FileWriter(STATIC_PATH + itemId +".html");
			template.process(map, writer);
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
