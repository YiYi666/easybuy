package top.headtop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import top.headtop.pojo.EasyBuyResult;
import top.headtop.service.ItemParamService;

@Controller
@RequestMapping("/item/param")
public class ItemParamController {
	
	@Autowired
	private ItemParamService itemParamService;

	@RequestMapping("/query/itemcatid/{catId}")
	@ResponseBody
	public EasyBuyResult queryParamByCatId(@PathVariable long catId){
		return itemParamService.queryParamByCatId(catId);
	}
	@RequestMapping("/save/{catId}")
	@ResponseBody
	public EasyBuyResult saveParamGroupKey(@PathVariable long catId ,String paramData){
		return itemParamService.saveParamGroupKey(catId,paramData);
	}
}
