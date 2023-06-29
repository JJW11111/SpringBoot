package com.ezen.g17.dao;

import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IAdminDao {

	void getAdmin(HashMap<String, Object> paramMap);
	void adminGetAllCount(HashMap<String, Object> cntMap);
	void getProductList(HashMap<String, Object> paramMap);
	void insertProduct(HashMap<String, Object> paramMap);

	
}
