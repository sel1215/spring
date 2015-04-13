package com.se.emp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.se.emp.dao.EmpDao;
import com.se.emp.domain.Employee;

@Service
@Transactional
public class EmpServiceImpl implements EmpService {

	@Autowired
	EmpDao eDao;
	
	@Override
	public boolean numChk(String num) {	//직원번호 중복체크
		System.out.println("--EmpServiceImpl.numChk(중복체크)");
		return eDao.numChk(num)==0?false:true;	//중복아님/중복임
	}

	@Override
	public String maxNum() {	//직원번호 새로받아오기
		System.out.println("--EmpServiceImpl.maxNum(직원번호받아오기)");
		return eDao.maxNum();
	}
	
	@Override
	public boolean insert(Employee emp) {		//직원등록
		System.out.println("--EmpServiceImpl.insert(정보등록)");
		return eDao.insert(emp)==0?false:true;	//등록실패/성공
	}

	@Override
	public List<Employee> selectAll() {	//직원리스트
		System.out.println("--EmpServiceImpl.selectAll(리스트조회)");
		return eDao.selectAll();
	}

	@Override
	public List<Employee> search(Employee emp) {	//직원검색
		System.out.println("--EmpServiceImpl.search(직원검색)");
		return eDao.search(emp);
	}

	@Override
	public boolean update(Employee emp) {	//직원정보수정
		System.out.println("--EmpServiceImpl.update(정보수정)");
		return eDao.update(emp)==0?false:true;	//수정실패/성공
	}

	@Override
	public boolean delete(int pk_num) {	//직원정보삭제
		System.out.println("--EmpServiceImpl.delete(정보삭제)");
		return eDao.delete(pk_num)==0?false:true;	//삭제실패/성공
	}

}
