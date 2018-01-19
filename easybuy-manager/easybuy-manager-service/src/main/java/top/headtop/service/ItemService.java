package top.headtop.service;

import java.util.List;

import top.headtop.pojo.EUDataGridResult;
import top.headtop.pojo.EUTreeNode;
import top.headtop.pojo.TbItem;

public interface ItemService {

	TbItem queryItemById(Long itemId);

	EUDataGridResult queryItemList(Integer page, Integer rows);

	List<EUTreeNode> queryItemCatList(Long parentId);

}
