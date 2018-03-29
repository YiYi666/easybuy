package top.headtop.pojo;

import java.io.Serializable;
import java.util.List;

public class OrderParam extends TbOrder implements Serializable {
	private static final long serialVersionUID = 1L;
	private TbOrderShipping orderShipping;
	private List<TbOrderItem> orderItems;
	public TbOrderShipping getOrderShipping() {
		return orderShipping;
	}
	public void setOrderShipping(TbOrderShipping orderShipping) {
		this.orderShipping = orderShipping;
	}
	public List<TbOrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<TbOrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	
}

