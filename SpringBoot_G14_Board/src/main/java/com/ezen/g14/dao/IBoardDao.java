package com.ezen.g14.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ezen.g14.dto.BoardVO;
import com.ezen.g14.dto.Paging;
import com.ezen.g14.dto.ReplyVO;

@Mapper
public interface IBoardDao {

	int getAllCount();
	List<BoardVO> getBoardList(Paging paging);
	int getReplyCount(int num);
	void plusOneReadCount(int num);
	BoardVO getBoard(int num);
	List<ReplyVO> selectReply(int num);
	void insertBoard(BoardVO bvo);
	void insertReply(ReplyVO replyvo);
	void deleteReply(int num);
	void updateBoard(BoardVO boardvo);
	void deleteBoard(int num);

}
