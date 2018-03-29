package top.headtop.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import top.headtop.mapper.TbContentCategoryMapper;
import top.headtop.mapper.TbContentMapper;
import top.headtop.pojo.EUTreeNode;
import top.headtop.pojo.EasyBuyResult;
import top.headtop.pojo.TbContent;
import top.headtop.pojo.TbContentCategory;
import top.headtop.pojo.TbContentCategoryExample;
import top.headtop.pojo.TbContentCategoryExample.Criteria;

@Service
public class ContentServiceImpl implements ContentService {
	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;
	@Autowired
	private TbContentMapper contentMapper;
	
	@Override
	public List<EUTreeNode> getContentList(long parentId) {
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		ArrayList<EUTreeNode> nodes = new ArrayList<EUTreeNode>();
		for (TbContentCategory contentCategory : list) {
			EUTreeNode node = new EUTreeNode();
			node.setId(contentCategory.getId());
			node.setText(contentCategory.getName());
			node.setState(contentCategory.getIsParent() ? "closed" : "open");
			nodes.add(node);
		}
		return nodes;
	}

	@Override
	public EasyBuyResult saveContent(long parentId, String name) {
		TbContentCategory contentCategory = new TbContentCategory();
		contentCategory.setParentId(parentId);
		contentCategory.setName(name);
		contentCategory.setCreated(new Date());
		contentCategory.setIsParent(false);
		contentCategory.setSortOrder(1);
		contentCategory.setStatus(1);
		contentCategory.setUpdated(new Date());
		contentCategoryMapper.insert(contentCategory);

		TbContentCategory parentContentCategory = contentCategoryMapper.selectByPrimaryKey(parentId);
		if (parentContentCategory != null && !parentContentCategory.getIsParent()) {
			parentContentCategory.setIsParent(true);
			contentCategoryMapper.updateByPrimaryKeySelective(parentContentCategory);
		}
		return EasyBuyResult.ok(contentCategory);
	}

	@Override
	public EasyBuyResult deleteContent(long id) {

		TbContentCategory contentCategory = contentCategoryMapper.selectByPrimaryKey(id);
		if (contentCategory.getIsParent()) {
			TbContentCategoryExample example = new TbContentCategoryExample();
			Criteria criteria = example.createCriteria();
			criteria.andParentIdEqualTo(id);
			List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
			for (TbContentCategory tbContentCategory : list) {
				deleteContent(tbContentCategory.getId());
			}
			contentCategoryMapper.deleteByPrimaryKey(id);
		} else {
			contentCategoryMapper.deleteByPrimaryKey(id);
		}
		Long parentId = contentCategory.getParentId();
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		if (list.isEmpty()) {
			TbContentCategory category = contentCategoryMapper.selectByPrimaryKey(parentId);
			category.setIsParent(false);
			category.setUpdated(new Date());
			contentCategoryMapper.updateByPrimaryKeySelective(category);
		}
		return EasyBuyResult.ok();
	}

	@Override
	public EasyBuyResult saveBigContent(TbContent content) {
		content.setCreated(new Date());
		content.setUpdated(new Date());
		contentMapper.insert(content);
		return EasyBuyResult.ok();
	}

}
