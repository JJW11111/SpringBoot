package com.ezen.g10.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.ezen.g10.dto.BbsDto;

@Repository
public class BbsDao implements IBbsDao {

	@Autowired
	private JdbcTemplate template;

	@Override
	public List<BbsDto> getList() {
		String sql = "select*from bbs order by id desc";
		List<BbsDto> list = template.query(sql, new BeanPropertyRowMapper<BbsDto>(BbsDto.class)
//				, 물음표가 sql에 있다면 현재 위치에 물음표에 들어갈 항목들을 순서대로 나열함
		);

		// ** RowMapper 이용
//		List<BbsDto> list = template.query(sql, new RowMapper<BbsDto>() {
//
//			@Override
//			public BbsDto mapRow(ResultSet rs, int rowNum) throws SQLException {
//				// select 의 결과는 rs 에 있으니 BbsDto 와의 연결은 직접 해결해야함
//				BbsDto bdto = new BbsDto();
//				bdto.setId(rs.getInt("id"));
//				bdto.setWriter(rs.getString("writer"));
//				bdto.setContent(rs.getString("content"));
//				bdto.setTitle(rs.getString("title"));
//				return bdto; // 리턴된 bdto 는 list 에 차곡차곡 쌓임
//			}
//
//		}
//		, 물음표가 sql에 있다면 현재 위치에 물음표에 들어갈 항목들을 순서대로 나열함
//	);
		return list;
	}

	@Override
	public int write(BbsDto bdto) {
		String sql = "insert into bbs values(bbs_seq.nextVal, ?, ?, ?)";
		int result = template.update(sql, bdto.getWriter(), bdto.getTitle(), bdto.getContent());
		return result;
	}

	@Override
	public int update(BbsDto bdto) {
		String sql = "update bbs set writer=?, title=?, content=? where id=?";
		int result = template.update(sql, bdto.getWriter(), bdto.getTitle(), bdto.getContent(), bdto.getId());
		return result;
	}

	@Override
	public int delete(int id) {
		String sql = "delete from bbs where id=?";
		int result = template.update(sql, id);
		return result;
	}

	@Override
	public BbsDto view(int id) {
		String sql = "select*from bbs where id=?";
//		BbsDto bdto = template.queryForObject(
//				sql,
//				new BeanPropertyRowMapper<BbsDto>(BbsDto.class),
//				id
//		);
		List<BbsDto> list = template.query(sql, new RowMapper<BbsDto>() {

			@Override
			public BbsDto mapRow(ResultSet rs, int rowNum) throws SQLException {
				// select의 결과는 rs에 있으니 BbsDto 와의 연결은 직접 해결하세요
				BbsDto bdto = new BbsDto();
				bdto.setId(rs.getInt("id"));
				bdto.setWriter(rs.getString("writer"));
				bdto.setContent(rs.getString("content"));
				bdto.setTitle(rs.getString("title"));
				return bdto; // 리턴된 bdto 는 list 에 차곡차곡 쌓임
			}
		}, 
				id
		);
		if (list.size() == 0)
			return null;
		else
			return list.get(0);
//		return bdto;
	}

}
