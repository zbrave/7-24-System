<%@page session="false"%>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
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
	<%@include file="navbar.jsp" %>	
 
 	<label>Şikayet ekle</label>
 	<select><option>Konum</option></select>
 	<select><option>İlgili birim</option></select>
 	<input type="text" value="tanım"/>
 	<button>Ekle</button>
    <h1>Message : ${message}</h1>
</body>
</html>