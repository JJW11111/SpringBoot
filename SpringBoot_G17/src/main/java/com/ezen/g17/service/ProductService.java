package com.ezen.g17.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.g17.dao.IProductDao;

@Service
public class ProductService {

	@Autowired
	IProductDao pdao;

	public void getBestNewBannerList(HashMap<String, Object> paramMap) {
		pdao.getBestNewBannerList( paramMap );		
	}

	public void getKindList(HashMap<String, Object> paramMap) {
		pdao.getKindList( paramMap );
		
	}

	public void getProduct(HashMap<String, Object> paramMap) {
		pdao.getProduct( paramMap );
		
	}

	
}














