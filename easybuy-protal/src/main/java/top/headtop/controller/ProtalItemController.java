package top.headtop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import top.headtop.pojo.BaseInfo;
import top.headtop.service.RestItemService;
import top.headtop.utils.JsonUtils;

@Controller
public class ProtalItemController {
	
	@Autowired
	private RestItemService restItemService;
	
	@RequestMapping("/item/{itemId}")
	public String getBaseInfo(@PathVariable long itemId,Model model){
		
		String baseInfo = restItemService.getBaseInfo(itemId);
		BaseInfo info = JsonUtils.jsonToPojo(baseInfo, BaseInfo.class);
		model.addAttribute("item",info);
		return "item";
	}
	
	@RequestMapping("/item/desc/{itemId}")
	@ResponseBody
	public String getItemDesc(@PathVariable long itemId,Model model){
		return restItemService.getItemDesc(itemId);
	}
	
	@RequestMapping(value="/item/param/{itemId}" ,method=RequestMethod.GET,produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getItemParam(@PathVariable long itemId,Model model){
		return restItemService.getItemParam(itemId);
	}

}
