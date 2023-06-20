package com.ezen.g14.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ezen.g14.dto.MemberVO;

@Mapper
public interface IMemberDao {

	MemberVO getMember(String userid);
	void insertMember(MemberVO mvo);
	void updateMember(MemberVO membervo);

}
