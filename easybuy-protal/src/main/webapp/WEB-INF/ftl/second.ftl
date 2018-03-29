<html>
<head>
</head>
<body>
<#include "first.ftl"/>


<table border="1">
<#list students as student>
	<#if student_index %2 ==0> 
		<tr style="color:red">
	<#else>
		<tr>
	</#if>
		<td>${student_index}</td>
		<td>${student.stuNo}</td>
		<td>${student.stuName}</td>
		<td>${student.stuSex}</td>
	</tr>
</#list>

${cur_time?string("yyyy-MM-dd HH:mm")}
${helloyou!"我是默认值"}

	<#if demo ??>
	当前日期:${demo?string("yyyy/MM/dd HH:mm:ss")}
	<#else>
	demo属性为null
	</#if>

</table>
	
</body>
</html>