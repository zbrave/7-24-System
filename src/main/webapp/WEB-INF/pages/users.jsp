<%@page session="true"%>
<%@ page pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
	<spring:url value="/resources/css/bootstrap.min.css" var="bootstrapCSS" />
	<spring:url value="/resources/css/bootstrap-theme.min.css" var="bootstrapThemeCSS" />
	<spring:url value="/resources/css/style.css" var="styleCSS" />
	<spring:url value="/resources/css/tabStyle.css" var="tabStyleCSS" />
	<spring:url value="/resources/ico724.png" var="ico" />
	<spring:url value="/resources/js/jquery.min.js" var="jqueryJS" />
	<spring:url value="/resources/js/bootstrap.min.js" var="bootstrapJS" />
	<link href='http://fonts.googleapis.com/css?family=Titillium+Web:400,300,600' rel='stylesheet' type='text/css'>
	<link href="${bootstrapCSS}" rel="stylesheet" />
	<link href="${bootstrapThemeCSS}" rel="stylesheet" />
	<link href="${styleCSS}" rel="stylesheet" />
	<script src="${jqueryJS}"></script>
	<script src="${bootstrapJS}"></script>
	<title>7/24 Servisi | Mekan Yönetimi</title>
</head>
<body>
    <%@include file="navbar2.jsp" %>
      <div style="padding:50px;">
      
 	<!-- Location -->
 	<div id="locationTab">
      <div class="container">
      		<div class="col-md-12">
      			<div class="form"> <!-- for background transparent color -->
				
      			<!-- TABLE <<< -->
      			<div class="panel panel-default">
      				<div class="panel-heading">Kullanıcılar:</div>
      					<table class="table" width="100%" border="0" cellpadding="0" cellspacing="0">
      						<tr>
					      		<th style="width: 10%;">ID</th>
					      		<th style="width: 40%;">Kullanıcı adı</th>
					      		<th style="width: 20%;">E-mail</th>
					      		<th>Enabled</th>
					      		<th>Banlı mı ?</th>
					      		<th>Eylem</th>
      						</tr>
						      <c:forEach items="${users }" var="data">
						      	<tr>
						      		<td>${data.id }</td>
						      		<td>${data.username }</td>
						      		<td>${data.email }</td>
						      		<td>${data.enabled }</td>
						      		<td>${data.banned }</td>
						      		<td><a class="btn btn-primary btn-xs" href="${pageContext.request.contextPath}/banUser?id=${data.id}" role="button">Banla</a>
						      			<a class="btn btn-danger btn-xs" href="#" role="button">Sil</a>
									</td>
						      	</tr>
						      </c:forEach>
      					</table>
      			</div>  <!-- TABLE >>> -->
      		</div>
      	</div>
      </div>
     </div>  <!-- LocationTab -->
     
     </div><!-- style padding -->
     <footer align="bottom"> &copy; Yildiz Teknik Üniversitesi </footer>
  </body>
  	
</html>