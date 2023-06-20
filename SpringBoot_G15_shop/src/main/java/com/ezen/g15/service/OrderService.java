package com.ezen.g15.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezen.g15.dao.IOrderDao;

@Service
public class OrderService {

	@Autowired
	IOrderDao odao;
	
	
	@Transactional
	public HashMap<String, Object> insertOrder(String id) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		// 여러 데이터 베이스 작업
		
		return result;
	}
}
