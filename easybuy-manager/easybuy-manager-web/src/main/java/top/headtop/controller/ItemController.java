package top.headtop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import top.headtop.pojo.EUDataGridResult;
import top.headtop.pojo.EUTreeNode;
import top.headtop.pojo.EasyBuyResult;
import top.headtop.pojo.TbItem;
import top.headtop.service.ItemService;

@Controller
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/item/save")
	@ResponseBody
	public EasyBuyResult saveItem(TbItem item,String desc,String itemParams){
		return itemService.saveItem(item,desc,itemParams);
	}
	
	@RequestMapping("/item/{itemId}")
	@ResponseBody
	public TbItem queryItemById(@PathVariable Long itemId){
		return itemService.queryItemById(itemId);
	}
	@RequestMapping("/item/list")
	@ResponseBody
	public EUDataGridResult queryItemList(Integer page,Integer rows){
		return itemService.queryItemList(page,rows);
	}
	@RequestMapping("/item/cat/list")
	@ResponseBody
	public List<EUTreeNode> queryItemCatList(@RequestParam(value="id",defaultValue="0") Long parentId){
		return itemService.queryItemCatList(parentId);
	}

}
