package top.headtop.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import top.headtop.dao.JedisClient;
import top.headtop.mapper.TbOrderItemMapper;
import top.headtop.mapper.TbOrderMapper;
import top.headtop.mapper.TbOrderShippingMapper;
import top.headtop.pojo.OrderParam;
import top.headtop.pojo.TbOrder;
import top.headtop.pojo.TbOrderItem;
import top.headtop.pojo.TbOrderShipping;
import top.headtop.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Value("${REDIS_INITIAL_ORDERID_KEY}")
	private String REDIS_INITIAL_ORDERID_KEY;
	@Value("${REDIS_INITIAL_ORDERID_VALUE}")
	private String REDIS_INITIAL_ORDERID_VALUE;

	@Autowired
	private JedisClient jedisClient;
	@Autowired
	private TbOrderMapper orderMapper;
	@Autowired
	private TbOrderItemMapper orderItemMapper;
	@Autowired
	private TbOrderShippingMapper orderShippingMapper;

	@Override
	public String createOrder(OrderParam orderParam) {
		String key = jedisClient.get(REDIS_INITIAL_ORDERID_KEY);
		if (StringUtils.isEmpty(key)) {
			jedisClient.set(REDIS_INITIAL_ORDERID_KEY, REDIS_INITIAL_ORDERID_VALUE);
		}
		String orderId = jedisClient.incr(REDIS_INITIAL_ORDERID_KEY) + "";
		TbOrder order = (TbOrder) orderParam;
		order.setOrderId(orderId);
		order.setStatus(1);
		order.setBuyerRate(0);
		order.setCreateTime(new Date());
		order.setUpdateTime(new Date());
		orderMapper.insert(order);

		List<TbOrderItem> orderItems = orderParam.getOrderItems();
		for (TbOrderItem tbOrderItem : orderItems) {
			tbOrderItem.setId(UUID.randomUUID().toString());
			tbOrderItem.setOrderId(orderId);
			//orderItemMapper.insert(tbOrderItem);
		}
		orderItemMapper.insertByBatch(orderItems);
		
		TbOrderShipping orderShipping = orderParam.getOrderShipping();
		orderShipping.setOrderId(orderId);
		orderShipping.setUpdated(new Date());
		orderShipping.setCreated(new Date());
		orderShippingMapper.insert(orderShipping);
		
		//TODO 订单生成后应将购物车清空
		return orderId;
	}

}
