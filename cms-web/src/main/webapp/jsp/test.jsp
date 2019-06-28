<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/core/jquery.cms.core.js"></script>
<script type="text/javascript">
	$(function(){
		$("div").myaccordion({selectedClz:"selected"});
	});
</script>
</head>
<body>
<div>
	<ul class="selected">
		<h3>aaaaaaaaa</h3>
		<li>aaaaaaaaaaaaaaaaaaa</li>
		<li>aaaaaaaaaaaaaaaaaaa</li>
		<li>aaaaaaaaaaaaaaaaaaa</li>
		<li>aaaaaaaaaaaaaaaaaaa</li>
		<li>aaaaaaaaaaaaaaaaaaa</li>
		<li>aaaaaaaaaaaaaaaaaaa</li>
		<li>aaaaaaaaaaaaaaaaaaa</li>
	</ul>
	<ul>
		<h3>aaaaaaaaa</h3>
		<li>aaaaaaaaaaaaaaaaaaa</li>
	</ul>
	<ul>
		<h3>aaaaaaaaa</h3>
		<li>aaaaaaaaaaaaaaaaaaa</li>
	</ul>
	<ul>
		<h3>aaaaaaaaa</h3>
		<li>aaaaaaaaaaaaaaaaaaa</li>
	</ul>

</div>
</body>
</html>