package top.headtop.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import top.headtop.mapper.TbItemParamKeyMapper;
import top.headtop.pojo.EasyBuyResult;
import top.headtop.pojo.TbItemParamKey;
import top.headtop.pojo.TbItemParamKeyExample;
import top.headtop.pojo.TbItemParamKeyExample.Criteria;
@Service
public class ItemParamServiceImpl implements ItemParamService{

	@Autowired
	private TbItemParamKeyMapper itemParamKeyMapper;
	
	@Override
	public EasyBuyResult queryParamByCatId(Long catId) {
		TbItemParamKeyExample example = new TbItemParamKeyExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemCatIdEqualTo(catId);
		List<TbItemParamKey> list = itemParamKeyMapper.selectByExampleWithBLOBs(example);
		if(list.size()>0){
			TbItemParamKey tbItemParamKey = list.get(0);
			return EasyBuyResult.ok(tbItemParamKey);
		}
		return EasyBuyResult.ok();
	}

	@Override
	public EasyBuyResult saveParamGroupKey(Long catId, String paramData) {

		TbItemParamKey itemParamKey = new TbItemParamKey();
		itemParamKey.setItemCatId(catId);
		itemParamKey.setParamData(paramData);
		itemParamKey.setCreated(new Date());
		itemParamKey.setUpdated(new Date());
		itemParamKeyMapper.insert(itemParamKey);
		return EasyBuyResult.ok();
	}

}
