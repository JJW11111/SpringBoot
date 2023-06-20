package com.ezen.g15.dto;

import java.sql.Timestamp;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class QnaVO {

	private Integer qseq; 
	@NotEmpty(message="제목을 입력하세요")
	private String subject;
	@NotEmpty(message="내용을 입력하세요")
	private String content;
	private String reply;
	private String id; 
	private String rep;
	private Timestamp indate;
	
}
