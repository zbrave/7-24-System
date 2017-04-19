<%@page session="false"%>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
	<spring:url value="/resources/css/bootstrap.css" var="bootstrapCSS" />
	<spring:url value="/resources/js/bootstrap.js" var="bootstrapJS" />
	<spring:url value="/resources/others/ams.css" var="amsCSS" />
	<spring:url value="/resources/others/ams.js" var="amsJS" />
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
	<link href="${bootstrapCSS}" rel="stylesheet" />
	<script src="${bootstrapJS}"></script>
	<link href="${amsCSS}" rel="stylesheet" />
	<script src="${amsJS}"></script>
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
</body>
</html>