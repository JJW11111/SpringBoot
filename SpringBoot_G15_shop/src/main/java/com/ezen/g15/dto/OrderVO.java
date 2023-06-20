package com.ezen.g15.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class OrderVO {

	private int odseq;
	private int oseq;
	private String id; 
	private Timestamp indate;
	private String mname;
	private String zip_num;
	private String address1;
	private String address2;
	private String address3;
	private String phone;  
	private int pseq;
	private String pname;
	private int quantity;
	private int price2;  
	private String result;
}
