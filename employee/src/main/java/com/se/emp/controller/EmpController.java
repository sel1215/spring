package com.se.emp.controller;

import java.util.List;
import java.util.regex.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.se.emp.domain.Employee;
import com.se.emp.service.EmpService;

@Controller
@RequestMapping("/emp")
public class EmpController {
	
	@Autowired
	private Employee emp;
	@Autowired
	private EmpService service;
/*	
	//메인페이지로 이동
	@RequestMapping("/main.do")
	public String mainPage(){
		System.out.println("---EmpController.mainPage()");
		return "/main";
	}
*/
	//직원정보 리스트 조회
	@RequestMapping("/list.do")
	public String list(Model model){
		System.out.println("---EmpController.list(리스트조회)");
		List<Employee> empList = service.selectAll();
		if(empList.isEmpty()){	//직원정보 없을 경우
			model.addAttribute("msg", "직원정보 리스트조회 실패");
			model.addAttribute("eList", "");
		}else{
			//model.addAttribute("msg", "리스트조회 성공");
			model.addAttribute("eList", empList);
		}
		
		String[][] k_data = {{"num","직원번호"},{"name","이름"},{"phone","전화번호"},{"grade","직급"},{"email","이메일"}};
		model.addAttribute("k_data", k_data);
		model.addAttribute("key", "");
		model.addAttribute("data", "");
		model.addAttribute("chart", "yes");
		return "/list";
	}
	
	//직원등록 페이지로 이동
	@RequestMapping("/insertPage.do")
	public String insertPage(Employee employee, Model model){
		System.out.println("---EmpController.insertPage()");
		String num = service.maxNum();	//max(직원번호)+1 값
		model.addAttribute("num", num);
		return "/insertPage";
	}
	
	//직원등록 처리
	@RequestMapping("/insert.do")
	public String insert(@ModelAttribute Employee emp, Model model){
		System.out.println("---EmpController.insert(등록)");
/*		System.out.println("[입력정보]");
		System.out.println("번호: " + emp.getNum());
		System.out.println("이름: " + emp.getName());
		System.out.println("전화번호: " + emp.getPhone());
		System.out.println("이메일 : " + emp.getEmail());
		System.out.println("직급 : " + emp.getGrade());
*/		
		//전화번호, 정규식
		if(!Pattern.matches("(0[0-9]{1,2})-([0-9]{3,4})-([0-9]{4})", emp.getPhone())){
			model.addAttribute("msg", "전화번호 형식 불일치");
			model.addAttribute("num", emp.getNum());
			return "/insertPage";
		}
		//이메일, 정규식		
		if(!Pattern.matches("^[a-zA-Z0-9_.]*[@]{1}[a-zA-Z0-9]*[.]{1}[a-zA-Z0-9]{2,3}[.]{0,1}[a-zA-Z0-9]{0,3}$", emp.getEmail())){
			model.addAttribute("msg", "이메일 형식 불일치");
			model.addAttribute("num", emp.getNum());
			return "/insertPage";
		}

		if(service.insert(emp)){
			model.addAttribute("msg", "직원정보 등록 성공");
			System.out.println("---EmpController.insert(결과: 성공)");
		}else{
			model.addAttribute("msg", "직원정보 등록 실패");
			System.out.println("---EmpController.insert(결과: 실패)");
		}
		return "../../index";
	}

	//직원정보 삭제 처리
	@RequestMapping("/delete.do")
	public String delete(@RequestParam int pnum, Model model){
		System.out.println("---EmpController.delete(삭제)");
//		System.out.println("넘어온 PK값 : " + pnum);
		if(service.delete(pnum)){
			model.addAttribute("msg", "직원정보 삭제 성공");
			System.out.println("---EmpController.delete(결과: 성공)");
		}else{
			model.addAttribute("msg", "직원정보 삭제 실패");
			System.out.println("---EmpController.delete(결과: 실패)");
		}
		return "../../index";
	}
	
	//직원정보 검색 처리
	@RequestMapping("/search.do")
	public String search(@RequestParam String key, @RequestParam String data, Model model){
		System.out.println("---EmpController.search(검색)");
		//System.out.println("key=data: " + key+"="+data);

		String[][] k_data = {{"num","직원번호"},{"name","이름"},{"phone","전화번호"},{"grade","직급"},{"email","이메일"}};
		model.addAttribute("k_data", k_data);
		model.addAttribute("key", key);
		model.addAttribute("data", data);
		
		emp.setPk_num(0);
		emp.setEmail("");
		emp.setGrade("");
		emp.setName("");
		emp.setNum("");
		emp.setPhone("");
		if(key.equals("name"))			//직원명으로 검색 경우
			emp.setName(data);
		else if(key.equals("grade"))	//직급으로 검색 경우
			emp.setGrade(data);		
		else if(key.equals("phone"))	//전화번호로 검색 경우
			emp.setPhone(data);
		else if(key.equals("email"))	//이메일로 검색 경우
		emp.setEmail(data);
		else if(key.equals("num")){	//직원번호로 검색 경우
			if(!Pattern.matches("[0-9]*", data)){
				model.addAttribute("msg", "직원번호 형식 불일치");
				return "/list";
			}
			emp.setNum(data);
		}

		System.out.println("[검색정보]");
		System.out.println("PK: " + emp.getPk_num());
		System.out.println("번호: " + emp.getNum());
		System.out.println("이름: " + emp.getName());
		System.out.println("전화번호: " + emp.getPhone());
		System.out.println("이메일 : " + emp.getEmail());
		System.out.println("직급 : " + emp.getGrade());

		List<Employee> empList = service.search(emp);
		if(empList.isEmpty()){	//검색 해당하는 정보 없을 경우
			model.addAttribute("msg", "직원정보 검색 실패");
			model.addAttribute("eList", "");
			System.out.println("---EmpController.search(결과: 실패)");
		}else{
			//model.addAttribute("msg", "검색 성공");
			model.addAttribute("eList", empList);
			System.out.println("---EmpController.search(결과: 성공)");
		}

		return "/list";
	}

	//직원정보 수정 페이지로 이동
	@RequestMapping("/changePage.do")
	public String changePage(@RequestParam int pnum, @ModelAttribute Employee emp, Model model){
		System.out.println("---EmpController.changePage(수정-직원정보조회)");

		emp.setPk_num(pnum);
		emp.setEmail("");
		emp.setGrade("");
		emp.setName("");
		emp.setNum("");
		emp.setPhone("");

		emp = service.search(emp).get(0);	//리스트로 넘어오는 정보에서 첫번째 employee
		System.out.println("[수정 전 정보]");
		System.out.println("PK번호: " + emp.getPk_num());
		System.out.println("번호:" + emp.getNum());
		System.out.println("이름: " + emp.getName());
		System.out.println("전화번호: " + emp.getPhone());
		System.out.println("이메일 : " + emp.getEmail());
		System.out.println("직급 : " + emp.getGrade());

		model.addAttribute("emp", emp);
		return "/changePage";
	}

	//직원번호 중복 체크(ajax)
	@RequestMapping("/chk.do")
	public String chk(@RequestParam String num, Model model){
		System.out.println("---EmpController.chk(직원번호중복체크)");
		if(service.numChk(num)){	//중복
			System.out.println("---EmpController.chk(결과: 중복)");
			model.addAttribute("msg", "직원번호 중복");
		}else{	//중복 아님
			System.out.println("---EmpController.chk(결과: 중복아님)");
			model.addAttribute("msg", "직원번호 중복 아님");
		}
		return "/chkAjaxPage";
	}

	
	//직원정보 수정 처리
	@RequestMapping("/change.do")
	public String change(@ModelAttribute Employee emp, Model model){
		System.out.println("---EmpController.change(수정)");

		if(!Pattern.matches("(0[0-9]{1,2})-([0-9]{3,4})-([0-9]{4})", emp.getPhone())){
			model.addAttribute("msg", "전화번호 형식 불일치");
			return "/changePage";
		}
		//이메일, 정규식		
		if(!Pattern.matches("^[a-zA-Z0-9_.]*[@]{1}[a-zA-Z0-9]*[.]{1}[a-zA-Z0-9]{2,3}[.]{0,1}[a-zA-Z0-9]{0,3}$", emp.getEmail())){
			model.addAttribute("msg", "이메일 형식 불일치");
			return "/changePage";
		}
		if(service.update(emp)){
			System.out.println("---EmpController.change(결과: 성공)");
			model.addAttribute("msg", "직원정보 수정 성공");
		}else{
			System.out.println("---EmpController.change(결과: 실패)");
			model.addAttribute("msg", "직원정보 수정 실패");
		}
		return "../../index";
	}

}
