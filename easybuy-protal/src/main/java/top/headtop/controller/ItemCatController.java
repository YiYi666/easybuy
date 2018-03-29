package top.headtop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import top.headtop.pojo.CatResult;
import top.headtop.service.RestItemCatService;
import top.headtop.utils.JsonUtils;

@Controller
public class ItemCatController {
	
	@Autowired
	private RestItemCatService restItemCatService;
	
	
//	http://localhost:8082/rest/itemcat/list?callback=category.getDataService
	@RequestMapping(value="/rest/itemcat/list"/*,produces=MediaType.APPLICATION_JSON_VALUE+";charset=utf-8"*/)
	@ResponseBody
	public String getItemList(String callback){
		CatResult list = restItemCatService.getItemList();
		String result = JsonUtils.objectToJson(list);
		return callback +"("+result+")";
	}
}
