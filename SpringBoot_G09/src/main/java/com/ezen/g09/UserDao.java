package com.ezen.g09;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository // @Autowired 쓰려면 써야함
public class UserDao {
	
	@Autowired
	private JdbcTemplate template;
	
	public List<UserDto> selectAll() {
//		ArrayList<UserDto> list = new ArrayList<UserDto>();
		String sql = "select*from myuser";
		List<UserDto> list = template.query(
				sql, 
				new BeanPropertyRowMapper<UserDto>(UserDto.class)
		);
		// ResultSet 사용없이 검색 결과 레코드의 필드를 Dto 변수에 넣고 list 에 add 동작을 실행함
		// 결과 레코드 갯수 만큼 실행함
		return list;
	}

	
	
}
