package com.ezen.g17.dao;

import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IQnaDao {

	void listQna(HashMap<String, Object> paramMap);

	void getQna(HashMap<String, Object> paramMap);

	void insertQna(HashMap<String, Object> paramMap);

	

}
