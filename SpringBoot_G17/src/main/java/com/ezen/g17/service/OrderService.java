package com.ezen.g17.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.g17.dao.ICartDao;
import com.ezen.g17.dao.IOrderDao;

@Service
public class OrderService {

	@Autowired
	IOrderDao odao;

	@Autowired
	ICartDao cdao;
	
	
}













