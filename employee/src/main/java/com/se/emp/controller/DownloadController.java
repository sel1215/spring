package com.se.emp.controller;

import java.io.*;
import java.util.List;

import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.se.emp.domain.Employee;
import com.se.emp.service.EmpService;

@Controller
@RequestMapping("/file")
public class DownloadController {

	@Autowired
	private Employee emp;
	@Autowired
	private EmpService service;

	private String fileName = "D://employee";	//확장자를 제외한 파일경로

	public List<Employee> downSearch(String key, String data){
		if(!data.isEmpty() || !data.equals("")){	//조회하는 데이터가 없을 경우
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
			else if(key.equals("num"))	//직원번호로 검색 경우
				emp.setNum(data);
			
			return service.search(emp);
		}else
			return service.selectAll();
	}
	
	//CSV 파일 저장
	@RequestMapping("/csv.do")
	public String csvDownload(Model model, @RequestParam String key, @RequestParam String data){
		System.out.println("---DownloadController.csvDownload(CSV파일저장)");
		String[][] k_list = {{"num","직원번호"},{"name","이름"},{"phone","전화번호"},{"grade","직급"},{"email","이메일"}};
		model.addAttribute("k_data", k_list);
		model.addAttribute("key", key);
		model.addAttribute("data", data);
		if(data=="")	//검색데이터가 없는 경우==리스트조회인 경우
			model.addAttribute("chart", "yes");	//chart 그리기
		
		List<Employee> eList = downSearch(key, data);

		if(eList.isEmpty()){	//검색 해당하는 정보 없을 경우
			model.addAttribute("msg", "csv파일 저장을 위한 직원정보 검색 실패");
			model.addAttribute("eList", "");
			System.out.println("---EmpController.search(결과: 실패)");
		}else{
			//model.addAttribute("msg", "검색 성공");
			model.addAttribute("eList", eList);
			System.out.println("---EmpController.search(결과: 성공)");
		}

		//현재 인코딩 확인
		//String enc = new java.io.OutputStreamWriter(System.out).getEncoding();
		//System.out.println("현재 인코딩 : " + enc);
		
		try{
			System.out.println("---DownloadController.csvDownload(CSV파일저장)");
			BufferedWriter writer = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(fileName+".csv"), "MS949")
			);

			writer.write("직원번호, 직원명, 전화번호, 직급, 이메일");
			if(eList.size()==0)
				writer.write("직원 정보가 없습니다.");
			else{
				for(int i=0; i<eList.size(); i++){
					String file_data = "\n" + eList.get(i).getNum();
					file_data += "," + eList.get(i).getName();
					file_data += "," + eList.get(i).getPhone();
					file_data += "," + eList.get(i).getGrade();
					file_data += "," + eList.get(i).getEmail();
//					System.out.println(file_data);
					writer.write(file_data);
				}
			}
			writer.close();
			model.addAttribute("msg", "직원정보, csv파일 저장 완료");
			System.out.println("다운로드 경로: " + fileName +".csv");
		}catch(IOException e){
			model.addAttribute("msg", "직원정보, csv파일 저장 실패");
			e.printStackTrace();
		}
		return "/list";
	}
	
	@RequestMapping("xls.do")
	 public String xlsDownload(Model model, @RequestParam String key, @RequestParam String data) {
		System.out.println("---DownloadController.xlsDownload(excel파일저장)");
		String[][] k_list = {{"num","직원번호"},{"name","이름"},{"phone","전화번호"},{"grade","직급"},{"email","이메일"}};
		model.addAttribute("k_data", k_list);
		model.addAttribute("key", key);
		model.addAttribute("data", data);
		if(data=="")	//검색데이터가 없는 경우==리스트조회인 경우
			model.addAttribute("chart", "yes");	//chart 그리기

		List<Employee> eList = downSearch(key, data);

		if(eList.isEmpty()){	//검색 해당하는 정보 없을 경우
			model.addAttribute("msg", "csv파일 저장을 위한 직원정보 검색 실패");
			model.addAttribute("eList", "");
			System.out.println("---EmpController.search(결과: 실패)");
		}else{
			//model.addAttribute("msg", "검색 성공");
			model.addAttribute("eList", eList);
			System.out.println("---EmpController.search(결과: 성공)");
		}

		try{
			System.out.println("---DownloadController.xlsDownload(excel파일저장)");
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("emp");		//emp Sheet 생성
			HSSFRow row = sheet.createRow(0);
			HSSFCell cell = row.createCell(0);
			cell.setCellValue("직원번호");
			row.createCell(1).setCellValue("직원명");
			row.createCell(2).setCellValue("전화번호");
			row.createCell(3).setCellValue("직급");
			row.createCell(4).setCellValue("이메일");
			System.out.println("===========================");			
			if(eList.size()==0){
				row = sheet.createRow(1);
				cell = row.createCell(0);
				cell.setCellValue("직원 정보가 없습니다.");
			} else{
				for(int i=0; i<eList.size(); i++){
					row = sheet.createRow(i+1);
					row.createCell(0).setCellValue(eList.get(i).getNum());
					row.createCell(1).setCellValue(eList.get(i).getName());
					row.createCell(2).setCellValue(eList.get(i).getPhone());
					row.createCell(3).setCellValue(eList.get(i).getGrade());
					row.createCell(4).setCellValue(eList.get(i).getEmail());
				}
			}

			FileOutputStream fos = new FileOutputStream(new File(fileName+".xls"));
			wb.write(fos);	//파일 생성
			fos.close();

			model.addAttribute("msg", "직원정보, xls파일 저장 완료");
			System.out.println("다운로드 경로: " + fileName +".csv");

		}catch(IOException e){
			model.addAttribute("msg", "직원정보, xls파일 저장 실패");
			e.printStackTrace();
		}
		
		return "/list";

	}


	
}
