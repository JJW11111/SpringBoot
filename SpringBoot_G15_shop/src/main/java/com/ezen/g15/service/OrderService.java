package com.ezen.g15.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezen.g15.dao.ICartDao;
import com.ezen.g15.dao.IOrderDao;
import com.ezen.g15.dto.CartVO;

@Service
public class OrderService {

	@Autowired
	IOrderDao odao;
	
	@Autowired
	ICartDao cdao;
	
	@Transactional
	public HashMap<String, Object> insertOrder(String id) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		int oseq = 0;
		// 여러 데이터 베이스 작업
		// 1. Orders 테이블에 현재 아이디로 레코드를 추가
		odao.insertOrders(id);
		// 2. 방금 추가된 Orders 테이블의 레코드의 주문번호(oseq)를 조회함(가장 큰 주문번호)
		oseq = odao.LookupMaxOseq();
		// 3. 그 주문번호와 함께 카트리스트에 있는 상품들을 하나하나 order_detail 테이블에 추가함
		//    -CartList 를 아이디로 한번 더 조회함
		// 4. 카트리스트에서 방금 주문한 상품을 삭제함
		List<CartVO> list = cdao.getCartList(id);
		for(CartVO cvo : list) {
			odao.insertOrderDetail(cvo, oseq);
			odao.deleteCart(cvo.getCseq());
		}
		
		// 5. 주문리스트와 주문번호를 해시맵에 담음
		result.put("oseq", oseq);
		
		return result;
	}
}
