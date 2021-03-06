package top.headtop.service;

import java.util.List;

import top.headtop.pojo.EUDataGridResult;
import top.headtop.pojo.EUTreeNode;
import top.headtop.pojo.EasyBuyResult;
import top.headtop.pojo.TbItem;

public interface ItemService {

	TbItem queryItemById(Long itemId);

	EUDataGridResult queryItemList(Integer page, Integer rows);

	List<EUTreeNode> queryItemCatList(Long parentId);

	EasyBuyResult saveItem(TbItem item, String desc, String itemParams);

}
