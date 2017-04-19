<%@page session="false"%>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
	<spring:url value="/resources/css/bootstrap.min.css" var="bootstrapCSS" />
	<spring:url value="/resources/css/bootstrap-theme.min.css" var="bootstrapThemeCSS" />
	<spring:url value="/resources/css/style.css" var="styleCSS" />
	<spring:url value="/resources/ico724.png" var="ico" />
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
	<link href='http://fonts.googleapis.com/css?family=Titillium+Web:400,300,600' rel='stylesheet' type='text/css'>
	<link href="${bootstrapCSS}" rel="stylesheet" />
	<link href="${bootstrapThemeCSS}" rel="stylesheet" />
	<link href="${styleCSS}" rel="stylesheet" />	
	<title>${title}</title>
</head>
<body>
	<%@include file="navbar2.jsp" %>	
    
    <table>
      	<tr>
      		<th>ID</th>
      		<th>locinfo</th>
      		<th>Suptype</th>
      		<th>Comp user</th>
      		<th>Date</th>
      		<th>Text</th>
      		<th>Action</th>
      	</tr>
      <c:forEach items="${complaintInfos }" var="data">
      	<tr>
      		<td>${data.id }</td>
      		<td>${data.locationInfo.description }</td>
      		<td>${data.supportTypeInfo.type }</td>
      		<td>${data.complaintTime }</td>
      		<td>${data.complaintText }</td>
      		<td>Yönlendir Çöz</td>
      	</tr>
      </c:forEach>
      </table>
<footer align="bottom"> &copy; Yildiz Teknik Üniversitesi </footer>
  </body>
	<script src='js/jquery.min.js'></script>
	<script src='js/bootstrap.min.js'></script>
</html>