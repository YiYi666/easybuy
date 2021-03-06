package top.headtop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import top.headtop.pojo.PicResult;
import top.headtop.service.PicService;

@Controller
public class PicController {
	
	@Autowired
	private PicService picService;
	
	@RequestMapping("/pic/upload")
	@ResponseBody
	public PicResult uploadFile(MultipartFile uploadFile){
		return picService.uploadFile(uploadFile);
	}

}
