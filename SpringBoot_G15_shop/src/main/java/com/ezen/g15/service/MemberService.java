package com.ezen.g15.service;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.g15.dao.IMemberDao;
import com.ezen.g15.dto.MemberVO;

@Service
public class MemberService {

	@Autowired
	IMemberDao mdao;

	public MemberVO getMember(String id) {
		return mdao.getMember(id);
	}

	public void joinKakao(MemberVO mvo) {
		mdao.joinKakao( mvo );		
	}

	public void insertMember(MemberVO membervo) {
		mdao.insertMember(membervo);
	}

	public void updateMember(MemberVO membervo) {
		mdao.updateMember( membervo );
		
	}

	public void withdrawalMember(String id) {
		mdao.withDrawalMember(id);		
	}
}
