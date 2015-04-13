package com.se.emp.domain;

import org.springframework.stereotype.Component;

@Component
public class Employee {

	private int pk_num;		//pk값
	private String num;		//직원번호 (DB는 int형)
	private String name;	//직원명
	private String phone;	//전화번호(정규식)
	private String grade;	//직급
	private String email;		//이메일주소(정규식)

	public int getPk_num() {
		return pk_num;
	}
	public void setPk_num(int pk_num) {
		this.pk_num = pk_num;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

}
