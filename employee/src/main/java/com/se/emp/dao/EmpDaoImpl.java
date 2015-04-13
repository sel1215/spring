package com.se.emp.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.se.emp.domain.Employee;

@Component("eDao")
public class EmpDaoImpl implements EmpDao {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public int numChk(String num) {	//직원번호 중복체크
		System.out.println("---EmpDaoImpl.numChk()");
		return sqlSession.selectOne("numChk", num);
	}

	@Override
	public String maxNum() {			//직원번호 받아오기
		System.out.println("--EmpDaoImpl.maxNum()");
		return sqlSession.selectOne("maxNum");
	}
	
	@Override
	public int insert(Employee emp) {		//직원등록
		System.out.println("---EmpDaoImpl.insert()");
		return sqlSession.insert("empInsert", emp);
	}

	@Override
	public List<Employee> selectAll() {	//직원리스트
		System.out.println("---EmpDaoImpl.selectAll()");
		return sqlSession.selectList("empList");
	}

	@Override
	public List<Employee> search(Employee emp) {	//직원검색
		System.out.println("---EmpDaoImpl.search()");
		return sqlSession.selectList("empSearch", emp);
	}

	@Override
	public int update(Employee emp) {	//직원정보수정
		System.out.println("---EmpDaoImpl.update()");
		return sqlSession.update("empChange", emp);
	}

	@Override
	public int delete(int pk_num) {	//직원정보삭제
		System.out.println("---EmpDaoImpl.delete()");
		return sqlSession.delete("empDelete", pk_num);
	}


}
