<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>등록</title>
<script type="text/javascript">
	function submit_chk(){
		var frm = document.insertForm;
		if(frm.name.value==""){
			alert("이름을 입력 해 주세요.");
			frm.name.focus();
		}else if(frm.phone.value==""){
			alert("전화번호를 입력 해 주세요.");
			frm.phone.focus();
		}else if(frm.grade.value==""){
			alert("직급을 입력 해 주세요.");
			frm.grade.focus();
		} else if(frm.email.value==""){
			alert("이메일을 입력 해 주세요.");
			frm.email.focus();
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
	<form:form modelAttribute="employee" name="insertForm" method="POST" action="/emp/insert.do">
	<table>
		<tr>
			<td colspan="2">
				<font color="red">* 전화번호 입력형식 : 010-1234-5678<br/>
				* 이메일 입력형식 : test12@employee.com</font>
			</td>
		</tr>
		<tr>
			<td>직원번호</td>
			<td>${num }<form:hidden path="num" value="${num }" /></td>
		</tr>
		<tr>
			<td>이름</td>
			<td><form:input path="name"  /></td>
		</tr>
		<tr>
			<td>전화번호</td>
			<td><form:input path="phone" /></td>
		</tr>
		<tr>
			<td>직급</td>
			<td><form:input path="grade" />
			</td>
		</tr>
		<tr>
			<td>이메일</td>
			<td><form:input path="email"/></td>
		</tr>
		<tr>
			<td colspan="2" align="center">
				<input type="button" value="입력완료" onclick="submit_chk()"/>
			</td>
		</tr>
	</table>
	</form:form>

</body>
</html>