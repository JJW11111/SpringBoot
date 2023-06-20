package com.ezen.g15.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.g15.dao.IQnaDao;

@Service
public class QnaService {

	@Autowired
	IQnaDao qdao;
}
