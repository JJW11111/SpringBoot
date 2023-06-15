package com.ezen.g13.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezen.g13.dao.ITransactionDao1;
import com.ezen.g13.dao.ITransactionDao2;

@Service
public class MyService {

	@Autowired
	ITransactionDao1 td1;

	@Autowired
	ITransactionDao2 td2;
	
	// @Transactional 에 아무옵션도 없을 때의 기본 속성
	// @Transactional (rollbackFor = {RuntimeException.class, Error.class} ) 이것이 기본 속성
	// RuntimeException 과 Error 가 발생했을 경우에는 기본적으로 rollback 이 된다는 것. 그 외에는 안됨
	
	// try / catch 구문 사용 시 정상 실행으로 작동하는 것으로 인지하여 Transactional 이 작동하지 않게 됨
	@Transactional (rollbackFor = {RuntimeException.class, Exception.class} )
	public int buy(String id, int amount, int error) {
		td1.buy(id, amount);
		if(error==0) {
			int n = 10 / 0;
//			throw new RuntimeErrorException(null);
		}
		td2.buy(id, amount);
		return error;
	}
	
	/*
	@Autowired
	TransactionTemplate tt;

	public int buy(String id, int amount, int error) {
		try {

			tt.execute(new TransactionCallbackWithoutResult() {

				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {

					td1.buy(id, amount);
					if (error == 0) {
						int n = 10 / 0;
					}
					td2.buy(id, amount);
					System.out.println("Transaction Commit");
				}
			});
		} catch (Exception e) {
			System.out.println("Transaction RollBack");
			return 0;
		}
		return 1;
	}

	*/
	
	/*
	 * 
	 * @Autowired ITransactionDao1 td1;
	 * 
	 * @Autowired ITransactionDao2 td2;
	 * 
	 * @Autowired PlatformTransactionManager ptm;
	 * 
	 * @Autowired TransactionDefinition td;
	 * 
	 * public int buy(String id, int amount, int error) { // transaction1 테이블과
	 * transaction2 테이블에 레코드를 insert하는 메서드를 각각의 dao에서 따로 호출
	 * 
	 * // 하나이상의 데이터베이스 작업을 한 단위로 묶어서 하나의 실행단위로 정의된 것을 트랜잭션이라고 함 // 트랜잭션 하나가 모두 다 실행이
	 * 되어 완료되면, commit 이라는 명령으로 작업을 완료하고, // 중간에 에러가 발생하여 트랜잭션을 취소하고자 한다면 rollback
	 * 이라는 명령으로 취소함
	 * 
	 * // 트랜잭션의 시작 TransactionStatus status = ptm.getTransaction(td); // 끝은 commit
	 * 또는 rollback
	 * 
	 * try { // 에러 발생 시 프로그램을 종료하는 것이 아니라 에러 발생 알림과 함께 다음 화면으로 자연스럽게 넘어가게 하려면
	 * try/catch구문 사용
	 * 
	 * td1.buy(id, amount); if (error == 0) { int n = 10 / 0; }
	 * 
	 * td2.buy(id, amount);
	 * 
	 * System.out.println("에러없이 둘다 실행이되었어요"); ptm.commit(status); // 영역 안의 모든 데이터베이스
	 * 작업의 실행적용 - 트랜잭션의 끝
	 * 
	 * } catch (Exception e) {
	 * 
	 * System.out.println("중간에 에러나서 둘 다 실행 안 됨"); ptm.rollback(status); // 영역안의 모든
	 * 데이터베이스 작업의 취소
	 * 
	 * return 0;// 에러발생 }
	 * 
	 * return 1;// 정상실행 }
	 * 
	 */
}
