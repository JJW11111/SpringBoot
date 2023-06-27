package com.ezen.g17.dao;

import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IProductDao {

	void getBestNewBannerList(HashMap<String, Object> paramMap);

}
