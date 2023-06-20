package com.ezen.g15.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.g15.dao.IAdminDao;

@Service
public class AdminService {

	@Autowired
	IAdminDao adao;
}
