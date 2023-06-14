package com.ezen.g10.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ezen.g10.dto.BbsDto;

@Repository
public class BbsDao implements IBbsDao{
	
	@Autowired
	private JdbcTemplate template;

	@Override
	public List<BbsDto> getList() {
		String sql = "select*from bbs order by id desc";
		List<BbsDto> list = template.query(
				sql, 
				new BeanPropertyRowMapper<BbsDto>(BbsDto.class)
		);
		return list;
	}

	@Override
	public int write(BbsDto bdto) {
		
		return 0;
	}

	@Override
	public int update(BbsDto bdto) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BbsDto view(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
}
