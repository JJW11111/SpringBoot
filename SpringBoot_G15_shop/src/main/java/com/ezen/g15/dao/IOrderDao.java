package com.ezen.g15.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ezen.g15.dto.CartVO;

@Mapper
public interface IOrderDao {

	void insertOrders(String id);

	int LookupMaxOseq();

	void insertOrderDetail(CartVO cvo, int oseq);
	
	void deleteCart(Integer cseq);

}
