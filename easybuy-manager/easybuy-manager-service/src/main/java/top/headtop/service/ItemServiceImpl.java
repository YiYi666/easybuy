package top.headtop.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import top.headtop.mapper.TbItemCatMapper;
import top.headtop.mapper.TbItemDescMapper;
import top.headtop.mapper.TbItemMapper;
import top.headtop.mapper.TbItemParamValueMapper;
import top.headtop.pojo.EUDataGridResult;
import top.headtop.pojo.EUTreeNode;
import top.headtop.pojo.EasyBuyResult;
import top.headtop.pojo.TbItem;
import top.headtop.pojo.TbItemCat;
import top.headtop.pojo.TbItemCatExample;
import top.headtop.pojo.TbItemCatExample.Criteria;
import top.headtop.pojo.TbItemDesc;
import top.headtop.pojo.TbItemExample;
import top.headtop.pojo.TbItemParamValue;
import top.headtop.utils.IDUtils;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private TbItemCatMapper itemCatMapper;
	@Autowired
	private TbItemDescMapper itemDescMapper;
	@Autowired
	private TbItemParamValueMapper itemParamValueMapper;
	@Autowired
	private JmsTemplate jmsTemplate;
	@Autowired
	private ActiveMQTopic topicDestination;
	
	@Override
	public TbItem queryItemById(Long itemId) {
		TbItem tbItem = itemMapper.selectByPrimaryKey(itemId);
		return tbItem;
	}

	@Override
	public EUDataGridResult queryItemList(Integer page, Integer rows) {
		EUDataGridResult result = new EUDataGridResult();
		TbItemExample example = new TbItemExample();
		example.setOrderByClause("created desc");
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

	@Override
	public EasyBuyResult saveItem(TbItem item,String desc,String itemParams) {
		long itemId = IDUtils.genItemId();
		item.setId(itemId);
		item.setStatus((byte)1);
		item.setCreated(new Date());
		item.setUpdated(new Date());
		itemMapper.insert(item);
		
		EasyBuyResult saveItemDesc = saveItemDesc(itemId,desc);
		if(saveItemDesc.getStatus()!=200){
			return EasyBuyResult.build(500, "服务器繁忙！");
		}
		
		EasyBuyResult saveItemParams = saveItemParams(itemId,itemParams);
		if(saveItemParams.getStatus()!=200){
			return EasyBuyResult.build(500, "服务器繁忙！");
		}
		//
		sendMessage(itemId);
		
		return EasyBuyResult.ok();
	}
	
	

	private void sendMessage(final long itemId) {

		jmsTemplate.send(topicDestination, new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage textMessage = session.createTextMessage(itemId+"");
				return textMessage;
			}
		});
		
	}

	private EasyBuyResult saveItemParams(long itemId, String itemParams) {
		TbItemParamValue itemParamValue = new TbItemParamValue();
		itemParamValue.setItemId(itemId);
		itemParamValue.setParamData(itemParams);
		itemParamValue.setCreated(new Date());
		itemParamValue.setUpdated(new Date());
		itemParamValueMapper.insert(itemParamValue);
		return EasyBuyResult.ok();
	}

	private EasyBuyResult saveItemDesc(long itemId, String desc) {
		if(org.apache.commons.lang3.StringUtils.isNotEmpty(desc)){
			TbItemDesc itemDesc = new TbItemDesc();
			itemDesc.setItemId(itemId);
			itemDesc.setItemDesc(desc);
			itemDesc.setCreated(new Date());
			itemDesc.setUpdated(new Date());
			itemDescMapper.insert(itemDesc);
		}
		return EasyBuyResult.ok();
		
	}



}
