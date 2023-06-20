package com.ezen.g15.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ezen.g15.dto.ProductVO;

@Mapper
public interface IProductDao {

	List<ProductVO> getNewList();
	List<ProductVO> getBestList();

}
