package com.ezen.g15.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.g15.dao.ICartDao;
import com.ezen.g15.dto.CartVO;

@Service
public class CartService {

	@Autowired
	ICartDao cdao;

	public void insertCart(CartVO cvo) {
		cdao.insertCart(cvo);
	}

	public HashMap<String, Object> getCartList(String id) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		List<CartVO> list = cdao.getCartList( id );
		int totalPrice = 0;
		for(CartVO cvo : list)
			totalPrice += cvo.getPrice2() * cvo.getQuantity(); 
		
		result.put("cartList", list);
		result.put("totalPrice", totalPrice);
		return result;
	}

	public void deleteCart(String cseq) {
		cdao.deleteCart( cseq );		
	}
}






