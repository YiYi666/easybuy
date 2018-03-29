package top.headtop.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import top.headtop.pojo.CartItem;
import top.headtop.pojo.TbItem;
import top.headtop.service.CartService;
import top.headtop.service.RestItemService;
import top.headtop.utils.CookieUtils;
import top.headtop.utils.JsonUtils;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private RestItemService restItemService;
	@Override
	public void addToCart(long itemId, int num, HttpServletRequest request, HttpServletResponse response) {
		List<CartItem> cartItems = cartCount(request);
		boolean isExist = false;
		for (CartItem cartItem : cartItems) {
			if(cartItem.getId()==itemId){
				cartItem.setNum(num+cartItem.getNum());
				isExist = true;
				break;
			}
		}
		if(!isExist){
			String baseInfo = restItemService.getBaseInfo(itemId);
			if(StringUtils.isNotEmpty(baseInfo)){
				TbItem item = JsonUtils.jsonToPojo(baseInfo, TbItem.class);
				CartItem cartItem = new CartItem();
				cartItem.setId(item.getId());
				cartItem.setImage(item.getImage() == null ? "" : item.getImage().split(",")[0]);
				cartItem.setTitle(item.getTitle());
				cartItem.setPrice(item.getPrice());
				cartItem.setNum(num);
				cartItems.add(cartItem);
			}
		}
		CookieUtils.setCookie(request, response, "eb_cart", JsonUtils.objectToJson(cartItems));
	}

	@Override
	public List<CartItem> cartCount(HttpServletRequest request) {
		String cookieValue = CookieUtils.getCookieValue(request, "eb_cart");
		if(StringUtils.isEmpty(cookieValue)){
			//TODO
			return new ArrayList<>();
		}
		List<CartItem> cartItems = JsonUtils.jsonToList(cookieValue, CartItem.class);
		return cartItems;
	}

	@Override
	public void cartDelete(long itemId, HttpServletRequest request, HttpServletResponse response) {
		List<CartItem> cartItems = cartCount(request);
		for (CartItem cartItem : cartItems) {
			if(cartItem.getId()==itemId){
				cartItems.remove(cartItem);
				break;
			}
		}
		CookieUtils.setCookie(request, response, "eb_cart", JsonUtils.objectToJson(cartItems));
		
	}

}
