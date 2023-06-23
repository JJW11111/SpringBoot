package com.ezen.g15.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.g15.dao.IProductDao;
import com.ezen.g15.dto.BannerVO;
import com.ezen.g15.dto.ProductVO;

@Service
public class ProductService {

	@Autowired
	IProductDao pdao;

	public HashMap<String, Object> getBestNewList() {
		HashMap<String, Object> result = new HashMap<String, Object>();

		List<ProductVO> newList = pdao.getNewList();
		List<ProductVO> bestList = pdao.getBestList();
		
		result.put("newProductList", newList);
		result.put("bestProductList", bestList);
		
		return result;
	}

	public List<ProductVO> getKindList(String kind) {
		return pdao.getKindList( kind );
	}

	public ProductVO getProduct(int pseq) {
		return pdao.getProduct( pseq );
	}

	public List<BannerVO> getBannerList() {
		return pdao.getBannerList();
	}
}














