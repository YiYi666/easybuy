package top.headtop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import top.headtop.pojo.EasyBuyResult;
import top.headtop.service.GeneratePageService;

@Controller
public class StaticController {
	@Autowired 
	private GeneratePageService generatePageService;
	
	@RequestMapping("/static/{itemId}")
	public EasyBuyResult genratePage(@PathVariable long itemId){
		generatePageService.genratePage(itemId);
		return EasyBuyResult.ok();
	}

}
