<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:choose>
	<c:when test="${msg == '직원번호 중복' }">
		<font color="red" size="3px">* 직원번호가 중복됩니다.</font> 
	</c:when>
	<c:otherwise>
		<font color="blue" size="3px">* 사용 가능한 직원번호입니다.</font>
	</c:otherwise>
</c:choose>