package top.headtop.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import top.headtop.mapper.TbItemCatMapper;
import top.headtop.pojo.CatNode;
import top.headtop.pojo.CatResult;
import top.headtop.pojo.TbItemCat;
import top.headtop.pojo.TbItemCatExample;
import top.headtop.pojo.TbItemCatExample.Criteria;
import top.headtop.service.RestItemCatService;
@Service
public class RestItemCatServiceImpl implements RestItemCatService {

	@Autowired
	private TbItemCatMapper itemCatMapper;
	
	@Override
	public CatResult getItemList() {
		CatResult catResult = new CatResult();
		List data = getItemCatList(0L);
		catResult.setData(data);
		return catResult;
	}

	private List<?> getItemCatList(long parentId) {

		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbItemCat> list = itemCatMapper.selectByExample(example);
		List catNodes = new ArrayList<>();
		for (TbItemCat itemCat : list) {
			CatNode catNode = new CatNode();
			if(itemCat.getIsParent()){
//				"/products/1.html",
				catNode.setUrl("/products/"+itemCat.getId()+".html");
				if(parentId==0L){
//					 "n": "<a href='/products/1.html'>图书、音像、电子书刊</a>",
					catNode.setName("<a href='/products/"+itemCat.getId()+".html'>"+itemCat.getName()+"</a>");
				}else {
//					"n": "电子书刊",
					catNode.setName(itemCat.getName());
				}
//				 "u": "/products/1.html",
				catNode.setUrl("/products/"+itemCat.getId()+".html");
				catNode.setItem(getItemCatList(itemCat.getId()));
				catNodes.add(catNode);
			}else {
//				"/products/3.html|电子书",
				catNodes.add("/products/"+itemCat.getId()+".html|"+itemCat.getName());
			}
			
		}
		return catNodes;
	}
	
}














