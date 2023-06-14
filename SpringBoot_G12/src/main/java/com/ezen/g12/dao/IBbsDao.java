package com.ezen.g12.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ezen.g12.dto.BbsDto;

@Mapper
public interface IBbsDao {
	
	public List<BbsDto> getList();
	public void write(BbsDto bbsdto);
//	public void write(String writer, String title, String content);
	public BbsDto view(int id);
	public void update(BbsDto bbsdto);
	public void delete(int id);
} 