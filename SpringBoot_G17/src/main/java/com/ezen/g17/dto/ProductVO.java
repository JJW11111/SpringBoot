package com.ezen.g17.dto;

import java.sql.Timestamp;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ProductVO {

	private Integer pseq;
	@NotNull(message="상품명을 입력하세요")
	@NotEmpty(message="상품명을 입력하세요")
    private String name;
    private String kind;   
    private Integer price1;
    @NotNull(message="판매가격을 입력하세요")
    private Integer price2;
    private Integer price3;
    @NotNull(message="상품상세설명을 입력하세요")
	@NotEmpty(message="상품상세설명을 입력하세요")
    private String content;
    @NotNull(message="상품이미지를 입력하세요")
	@NotEmpty(message="상품이미지를 입력하세요")
    private String image;
    private String useyn;
    private String bestyn;
    private Timestamp indate;
    
}
