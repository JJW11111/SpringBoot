package com.ezen.g17.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class BannerVO {

	private int bseq;
	private String subject;
	private int order_seq;
	private String useyn;
	private Timestamp indate;
	private String image;
	
}
