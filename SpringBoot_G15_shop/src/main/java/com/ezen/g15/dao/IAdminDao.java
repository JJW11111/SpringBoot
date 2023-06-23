package com.ezen.g15.dao;

import java.util.List;

import javax.validation.Valid;

import org.apache.ibatis.annotations.Mapper;

import com.ezen.g15.dto.BannerVO;
import com.ezen.g15.dto.MemberVO;
import com.ezen.g15.dto.OrderVO;
import com.ezen.g15.dto.Paging;
import com.ezen.g15.dto.ProductVO;
import com.ezen.g15.dto.QnaVO;

@Mapper
public interface IAdminDao {

	String getPwd(String workId);
	int getAllCount(String string, String string2, String key);
	List<ProductVO> listProduct(Paging paging, String key);
	void insertProduct(ProductVO productvo);
	void updateProduct(@Valid ProductVO productvo);
	List<OrderVO> listOrder(Paging paging, String key);
	List<QnaVO> listQna(Paging paging, String key);
	List<MemberVO> listMember(Paging paging, String key);
	void updateOrderResult(int odseq);
	void memberReinsert(String id, String useyn);
	void updateQna(int qseq, String reply);
	List<BannerVO> getBannerList();
	void insertBanner(BannerVO bannervo);
	void updateSeq(int changeval, String useyn, int bseq);

	
}
