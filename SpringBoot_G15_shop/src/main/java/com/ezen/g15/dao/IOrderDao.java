package com.ezen.g15.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ezen.g15.dto.CartVO;
import com.ezen.g15.dto.OrderVO;

@Mapper
public interface IOrderDao {

	void insertOrders(String id);
	int LookupMaxOseq();
	void insertOderDetail(CartVO cvo, int oseq);
	void deleteCart(Integer cseq);
	List<OrderVO> listOrderByOseq(int oseq);
	void insertOderDetailOne(int pseq, int quantity, int oseq);
	List<Integer> selectSeqOrderIng(String id);
	List<Integer> selectSeqOrderAll(String id);
	void updateOrderEnd(int odseq);

}
