package com.ezen.g15.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ezen.g15.dto.CartVO;

@Mapper
public interface ICartDao {

	void insertCart(CartVO cvo);

}
