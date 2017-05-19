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
	<spring:url value="/resources/js/jquery.min.js" var="jqueryJS" />
	<spring:url value="/resources/js/bootstrap.min.js" var="bootstrapJS" />
	<link href='http://fonts.googleapis.com/css?family=Titillium+Web:400,300,600' rel='stylesheet' type='text/css'>
	<link href="${bootstrapCSS}" rel="stylesheet" />
	<link href="${bootstrapThemeCSS}" rel="stylesheet" />
	<link href="${styleCSS}" rel="stylesheet" />
	<script src="${jqueryJS}"></script>
	<script src="${bootstrapJS}"></script>	
	<title>${title}</title>
</head>
<body>
	<%@include file="navbar2.jsp" %>	
 
 	Kullanıcı adı: ${userInfo.username }
 	E-mail: ${userInfo.email }
 	Şifre : ${userInfo.password }
 	Yapılan toplam şikayet : 
 	<form:form action="changePass" method="POST" modelAttribute="userForm">
		<div class="form-group">
			<div class="input-group">
				<input id="id" name="id" type="hidden" value=""/>
				<span class="input-group-addon">Şifre:</span>
							<input id="password" name="password" type="password" />
     				</div>
     				<div class="input-group">
				<input id="id" name="id" type="hidden" value=""/>
				<span class="input-group-addon">Şifre tekrar:</span>
							<input id="passwordConf" name="passwordConf" type="password" />
     				</div>
 					<div class="input-group">
 					
		        <span class="input-group-btn">
     						<button type="submit" class="btn btn-default" value="Ekle" >Değiştir</button>
     					</span>
       				</div>
       			</div> <!-- form-group -->
    </form:form>
</body>
	
</html>