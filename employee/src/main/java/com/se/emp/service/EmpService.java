package com.se.emp.service;

import java.util.List;

import com.se.emp.domain.Employee;

public interface EmpService {
	public boolean numChk(String num);	//직원번호 중복체크
	public String maxNum();						//직원번호 새로 받아오기
	public boolean insert(Employee emp);	//직원등록
	public List<Employee> selectAll();//직원리스트
	public List<Employee> search(Employee emp);//직원검색
	public boolean update(Employee emp);//직원정보수정
	public boolean delete(int pk_num);//직원정보삭제
	
}
