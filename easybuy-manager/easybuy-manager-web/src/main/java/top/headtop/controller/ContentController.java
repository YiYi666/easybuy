package top.headtop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import top.headtop.pojo.EUTreeNode;
import top.headtop.pojo.EasyBuyResult;
import top.headtop.pojo.TbContent;
import top.headtop.service.ContentService;

@Controller
@RequestMapping("/content")
public class ContentController {
	@Autowired
	private ContentService contentService;
	
	@RequestMapping("/category/list")
	@ResponseBody
	public List<EUTreeNode> getContentList(@RequestParam(value="id",defaultValue="0")long parentId){
		
		return contentService.getContentList(parentId);
	}
	
	@RequestMapping("/category/create")
	@ResponseBody
	public EasyBuyResult saveContent(long parentId,String name){
		
		return contentService.saveContent(parentId,name);
	}
	
	@RequestMapping("/category/delete")
	@ResponseBody
	public EasyBuyResult deleteContent(long id){	
		return contentService.deleteContent(id);
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public EasyBuyResult saveBigContent(TbContent content){	
		return contentService.saveBigContent(content);
	}

	

}
