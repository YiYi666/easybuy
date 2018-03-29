package top.headtop.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import top.headtop.pojo.CartItem;
import top.headtop.service.CartService;

@Controller
public class CartController {
	@Autowired
	private CartService cartService;
	
	
	@RequestMapping("/cart/add/{itemId}")
	public String addToCart(@PathVariable long itemId,@RequestParam(defaultValue="1") int num,
			HttpServletRequest request, HttpServletResponse response){
		cartService.addToCart(itemId,num,request,response);
		return "cartSuccess";
		
	}
	@RequestMapping("/cart/count")
	public String cartCount(Model model,HttpServletRequest request){
		List<CartItem> cartCount = cartService.cartCount(request);
		model.addAttribute("cartList", cartCount);
		return "cart";
		
	}
	
	@RequestMapping("/cart/delete/{itemId}")
	public String cartDelete(@PathVariable long itemId,HttpServletRequest request,HttpServletResponse response){
		cartService.cartDelete(itemId, request, response);
		return "redirect:/cart/count.html";
		
	}
}
