package com.ezen.g15.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ezen.g15.dto.CartVO;

@Mapper
public interface ICartDao {

	void insertCart(CartVO cvo);
	List<CartVO> getCartList(String id);
	void deleteCart(String cseq);

}
