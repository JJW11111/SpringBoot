package com.ezen.g11.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ezen.g11.dto.UserDto;

@Mapper
public interface IUserDao {

	public List<UserDto> getList();
	
}
