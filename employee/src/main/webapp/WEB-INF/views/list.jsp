<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>직원정보 페이지</title>
<script type="text/javascript" src="http://code.jquery.com/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="https://www.google.com/jsapi"></script><!-- chart api -->
<script type="text/javascript">
	function search(){	//직원검색
		location.href="/emp/search.do?key="+ $("#data_key").val()
						+ "&data=" + $("#data_value").val();
	}
	function download(url){
		location.href= url + "?key=" + $("#data_key").val()
						+ "&data=" + $("#data_value").val();
	}
</script>
<script type="text/javascript">
	function drawChart() {		//chart 그리는 function
		var jsonObj = [];	//chart 표현할 데이터 담을 json

		for(var num=1;; num++){
			var $grade = $("#grade_"+num).html();	//document.getElementById("grade_"+num).innerHTML;
			if(typeof($grade)=="undefined")
				break;

			var state = false;	//json에 grade정보 없음
			for(var i=0; i<jsonObj.length; i++){
				if(jsonObj[i].grade == $grade){	//json에 grade 정보가 이미 있을 경우
					jsonObj[i].num++;
					state = true;	//json에 grade정보 있음
					break;
				}
			}
			if(!state)	//json에 grade정보가 없었을 경우
				jsonObj.push({grade:$grade, num:1});
    	}	//jsonObj에 데이터 넣기 종료

    	var data = "google.visualization.arrayToDataTable([";
      	data += "['Task', 'Hours per Day']";
		for(var i=0; i<jsonObj.length; i++){
	        data += ",['"+jsonObj[i].grade+"',    "+jsonObj[i].num+"]";
		}
        data += "]);";
//alert("data:" + data);
        var chart = new google.visualization.PieChart(document.getElementById('piechart'));
        chart.draw(eval(data), { title: '직급'});
	}
</script>

</head>
<c:if test="${chart == 'yes' }"><!-- 직원정보 리스트조회일 경우 -->
	<script type="text/javascript">
		google.load("visualization", "1", {packages:["corechart"]});
		google.setOnLoadCallback(drawChart);
	</script>	
</c:if>
<c:choose>
	<c:when test="${msg == '직원번호 형식 불일치'}">
<body onload="alert('직원번호는 숫자로 입력 해 주세요.');">
	</c:when>
	<c:when test="${!empty msg}">
<body onload="alert('${msg}');">
	</c:when>
	<c:otherwise>
<body>
	</c:otherwise>
</c:choose>
 <table>
 	<tr>
	 	<td colspan="2"><a href="/emp/list.do">리스트</a>
	 		<input type="button" value="등록" onclick="location.href='/emp/insertPage.do'"/></td>
		<td align="center" colspan="3">
			<select id="data_key">
<c:forEach items="${k_data}" var="kD">
	<c:choose>
		<c:when test="${(!empty key) && (kD[0]==key)}">
				<option value="${kD[0]}" selected="selected">${kD[1]}</option>
		</c:when>
		<c:otherwise>
				<option value="${kD[0]}">${kD[1]}</option>
		</c:otherwise>
	</c:choose>
</c:forEach>
			</select>
<c:choose>
	<c:when test=" ${empty data }">
		<input type="text" id="data_value" value="" size="15px"/>
	</c:when>
	<c:otherwise>
		<input type="text" id="data_value" value="${data}" size="15px"/>
	</c:otherwise>
</c:choose>
		<input type="button" value="검색" onclick="search()"/></td>
		<td align="right" colspan="3">
			<input type="button" value="csv파일 저장" onclick="if(confirm('csv파일로 저장하시겠습니까?')){download('/file/csv.do');}"/>
			<input type="button" value="xls파일 저장" onclick="if(confirm('xls파일로 저장하시겠습니까?')){download('/file/xls.do');}"/>
		</td>
 	</tr>
 	<tr>
 		<th width="40px">no.</th>
 		<th width="80px">직원번호</th>
 		<th width="100px">이름</th>
 		<th width="150px">전화번호</th>
 		<th width="80px">직급</th>
 		<th width="150px">이메일</th>
 		<th width="40px">&nbsp;</th>
 		<th width="40px">&nbsp;</th>
	</tr>
<c:choose>
	<c:when test="${msg=='리스트조회 실패' && empty eList}">
	<tr><td colspan="8">
			&nbsp;&nbsp;&nbsp;등록된 직원정보가 없습니다. 
	</td></tr>
	</c:when>
	<c:when test="${msg=='검색 실패' && empty eList}">
	<tr><td colspan="8" align="center">
			검색조건에 알맞는 직원정보가 없습니다. 
	</td></tr>
	</c:when>
<c:otherwise>
<c:forEach items="${eList}" var="emp" varStatus="i">
	<tr>
 		<td align="center">${i.count }</td>
 		<td align="center">${emp.num }</td>
 		<td>&nbsp;${emp.name }</td>
 		<td>&nbsp;${emp.phone }</td>
 		<td id="grade_${i.count}" align="center">${emp.grade }</td>
 		<td>&nbsp;${emp.email }</td>
 		<td><input type="button" value="수정" onclick="location.href='/emp/changePage.do?pnum=${emp.pk_num}'"/></td>
 		<td><input type="button" value="삭제" onclick="if(confirm('정말 삭제하시겠습니까?')){location.href='/emp/delete.do?pnum=${emp.pk_num}'}"/></td>
 	</tr>
</c:forEach>
</c:otherwise>
</c:choose> 	
 </table>
	<div id="piechart" style="width: 900px; height: 500px;"></div>
 </body>
</html>