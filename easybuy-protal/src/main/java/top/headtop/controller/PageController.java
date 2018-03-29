package top.headtop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import top.headtop.service.RestContentService;

@Controller
public class PageController {
	@Autowired
	private RestContentService restContentService;
	
	@RequestMapping("index")
	public String showIndex(Model model){
		String advertise = restContentService.syscAdvertise();
		model.addAttribute("ad1", advertise);
		return "index";
	}

}
