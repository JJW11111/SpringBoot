package com.ezen.bt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.bt.dao.ITestDao;
import com.ezen.bt.dto.BannerVO;

@Service
public class TestService {

	@Autowired
	ITestDao tdao;

	public List<BannerVO> getBannerList() {
		return tdao.getBannerList();
	}

	public void insertBanner(BannerVO bannervo) {
		tdao.insertBanner(bannervo);
	}

	public List<BannerVO> getBannerListFive() {
		return tdao.getBannerListFive();
	}
}
