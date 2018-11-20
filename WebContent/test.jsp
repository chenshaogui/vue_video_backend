<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'test.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<script type="text/javascript">   
		function ToUrl(url)   
		{   
		      location.href=url;   
		}   
	</script>  
  </head>
  
  <body>
  	<center>
		<form:form action="/test/car/add" modelAttribute="shoppingCar">
	    	商品名称：<form:input path="productName"/>
	    	<br/>
	    	商品数量：<form:input path="productNum"/>
	    	<br/>
	    	商品单价：<form:input path="productMoney"/>
	    	<br/>
	    	<input type="submit" value="加入购物车"/>
	    	<br/>
	    	<input type="button" value="清空购物车" onclick="ToUrl('/test/car/delAll?userId=999');"/>
	    	<br/>
	    	<input type="button" value="提交订单" onclick="ToUrl('/test/order/add?userId=999');"/>
	    	<br/>
	    	<input type="button" value="删除订单" onclick="ToUrl('/test/order/del?orderNum=OR999999999');"/>
		</form:form>
	</center>
  </body>
</html>
