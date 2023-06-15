package com.ezen.g13.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;

import com.ezen.g13.dao.ITransactionDao1;
import com.ezen.g13.dao.ITransactionDao2;

@Service
public class MyService {
	@Autowired
	ITransactionDao1 td1;

	@Autowired
	ITransactionDao2 td2;

	@Autowired
	PlatformTransactionManager ptm;

	@Autowired
	TransactionDefinition td;

	public int buy(String id, int amount, int error) {
		// transaction1 테이블과 transaction2 테이블에 레코드를 insert하는 메서드를 각각의 dao에서 따로 호출

		try {
			// 에러 발생 시 프로그램을 종료하는 것이 아니라 에러 발생 알림과 함께 다음 화면으로 자연스럽게 넘어가게 하려면 try/catch구문 사용

			td1.buy(id, amount);
			if (error == 0) {
				int n = 10 / 0;
			}

			td2.buy(id, amount);

		} catch (Exception e) {
			System.out.println("예외 발생");
			return 0;// 에러발생
		}

		return 1;// 정상실행
	}
}
