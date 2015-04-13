<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>수정</title>
<script type="text/javascript">
	var xmlHttpReq;

	function numChkAjax(){	//직원번호 중복체크 ajax처리
		var num = document.getElementById("num");
		document.getElementById("chkValue").value = "false";
		//alert(num.value+"\n숫자아님? : "+ (num.value.match(/^\d+$/ig)==null));
		if(3<num.value.length || (num.value.match(/^\d+$/ig)==null)){
			alert("직원번호는 숫자로 최대 3 자릿수까지 입력하실 수 있습니다.");
			num.value="";
			num.focus();
			document.getElementById("chkSpan").innerHTML = "";
			document.getElementById("chkValue").value = "false";
		}else{
			xmlHttpReq = new XMLHttpRequest();
			xmlHttpReq.onreadystatechange = callBack;		//웹서버의 응답 수신 처리(callBack()로 받아오겠단 의미
			xmlHttpReq.open("GET", "/emp/chk.do?num="+num.value, true);	//웹서버에 요청 전송
			xmlHttpReq.send(null);
		}
	}

	function callBack(){						 //readyState==4
		if(xmlHttpReq.readyState == 4) {				 //4 : 서버가 정상 종료되고 응답이 준비된 경우 
			if(xmlHttpReq.status == 200){			 //status가 200이면 응답을 성공적으로 받은 경우
				var receive = xmlHttpReq.responseText.trim();	 //서버의 응답 데이터 받기
				document.getElementById("chkSpan").innerHTML = receive;	   //화면에 아이디 중복 체크 결과 메시지 뿌리기
				if(-1<receive.indexOf("사용 가능")){
					document.getElementById("chkValue").value = "true";
				}
			}
		}
	}
//==========================================================================
	
	function submit_chk(){
		var frm = document.changeForm;
		var chkValue = document.getElementById("chkValue").value;

		if(frm.name.value==""){
			alert("이름을 입력 해 주세요.");
			frm.name.focus();
		}else if(frm.num.value==""){
			alert("직원번호를 입력 해 주세요.");
			frm.num.focus();
		}else if(frm.phone.value==""){
			alert("전화번호를 입력 해 주세요.");
			frm.phone.focus();
		}else if(frm.grade.value==""){
			alert("직급을 입력 해 주세요.");
			frm.grade.focus();
		} else if(frm.email.value==""){
			alert("이메일을 입력 해 주세요.");
			frm.email.focus();
		} else if(chkValue=="false")	{	//직원번호 중복체크 안 했을 경우
			alert("직원번호를 확인 해 주세요.");
		}else{
			frm.submit();
		}
	}
</script>
</head>
<c:choose>
	<c:when test="${msg == '전화번호 형식 불일치'}">
<body onload="alert('전화번호를 형식에 맞춰 입력 해 주세요.'); document.frm.phone.focus();">
	</c:when>
	<c:when test="${msg == '이메일 형식 불일치' }">
<body onload="alert('이메일을 형식에 맞춰 입력 해 주세요.'); document.frm.phone.focus();">
	</c:when>
	<c:otherwise>
<body>
	</c:otherwise>
</c:choose>

<input type="button" value="메인으로" onclick="location.href='/emp/main.do'"/><br/>
<hr/>
<input type="hidden" id="chkValue" value="true"/><!-- 직원번호 중복체크 확인 값 -->
	<form:form modelAttribute="employee" name="changeForm" method="POST" action="/emp/change.do">
	<table>
		<tr>
			<td colspan="2">
				<font color="red">* 전화번호 입력형식 : 010-1234-5678<br/>
				* 이메일 입력형식 : test12@employee.com</font>
			</td>
		</tr>
		<tr>
			<td>직원번호</td>
			<td><form:input path="num" value="${emp.num}" id="num" onkeyup="numChkAjax()"/></td>
			<td width="500px"><span id="chkSpan"></span></td>
		</tr>
		<tr>
			<td>이름</td>
			<td colspan="2"><form:input path="name" value="${emp.name }" /></td>
		</tr>
		<tr>
			<td>전화번호</td>
			<td colspan="2"><form:input path="phone" value="${emp.phone }"/></td>
		</tr>
		<tr>
			<td>직급</td>
			<td colspan="2"><form:input path="grade" value="${emp.grade }"/></td>
		</tr>
		<tr>
			<td>이메일</td>
			<td colspan="2"><form:input path="email" value="${emp.email }"/></td>
		</tr>
		<tr>
			<td colspan="2" align="center">
				<input type="button" value="수정" onclick="submit_chk()"/>
			</td>
			<td></td>
		</tr>
	</table>
	<form:hidden path="pk_num" value="${emp.pk_num }"/>
	</form:form>

</body>
</html>