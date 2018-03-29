package top.headtop.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import top.headtop.pojo.CartItem;

public interface CartService {

	void addToCart(long itemId, int num, HttpServletRequest request, HttpServletResponse response);

	List<CartItem> cartCount(HttpServletRequest request);

	void cartDelete(long itemId, HttpServletRequest request, HttpServletResponse response);

}
