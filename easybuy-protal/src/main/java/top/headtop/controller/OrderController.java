package top.headtop.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import top.headtop.pojo.CartItem;
import top.headtop.pojo.OrderParam;
import top.headtop.service.CartService;
import top.headtop.service.OrderService;

@Controller
public class OrderController {
	
	@Autowired
	private CartService cartService;
	@Autowired
	private OrderService orderService;
	
	@RequestMapping("/order/order-cart")
	public String orderCount(HttpServletRequest request, Model model) {
		List<CartItem> cartCount = cartService.cartCount(request);
		model.addAttribute("cartList", cartCount);
		return "order-cart";
	}
	
	@RequestMapping("/order/create")
	public String createOrder(OrderParam orderParam,Model model) {
		String orderId = orderService.createOrder(orderParam);
		model.addAttribute("orderId", orderId);
		model.addAttribute("payment", orderParam.getPayment());
		model.addAttribute("date",new DateTime().plusDays(1).toString("yyyy-MM-dd"));
		return "success";
	}
	

}
