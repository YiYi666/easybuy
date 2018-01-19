package top.headtop.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import top.headtop.mapper.TbItemCatMapper;
import top.headtop.mapper.TbItemMapper;
import top.headtop.pojo.EUDataGridResult;
import top.headtop.pojo.EUTreeNode;
import top.headtop.pojo.TbItem;
import top.headtop.pojo.TbItemCat;
import top.headtop.pojo.TbItemCatExample;
import top.headtop.pojo.TbItemCatExample.Criteria;
import top.headtop.pojo.TbItemExample;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private TbItemCatMapper itemCatMapper;
	
	@Override
	public TbItem queryItemById(Long itemId) {
		TbItem tbItem = itemMapper.selectByPrimaryKey(itemId);
		return tbItem;
	}

	@Override
	public EUDataGridResult queryItemList(Integer page, Integer rows) {
		EUDataGridResult result = new EUDataGridResult();
		TbItemExample example = new TbItemExample();
		PageHelper.startPage(page, rows);
		List<TbItem> list = itemMapper.selectByExample(example);
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		result.setRows(list);
		result.setTotal(pageInfo.getTotal());
		return result;
	}

	@Override
	public List<EUTreeNode> queryItemCatList(Long parentId) {

		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbItemCat> list = itemCatMapper.selectByExample(example);
		List<EUTreeNode> nodes = new ArrayList<EUTreeNode>();
		for (TbItemCat tbItemCat : list) {
			EUTreeNode node = new EUTreeNode();
			node.setId(tbItemCat.getId());
			node.setText(tbItemCat.getName());
			node.setState(tbItemCat.getIsParent()?"closed":"open");
			nodes.add(node);
		}
		return nodes;
	}
}