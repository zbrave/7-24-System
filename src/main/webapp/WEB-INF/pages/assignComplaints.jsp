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
	<div class="text-center">	
    	<p style="font-size: 30px; color: white; text-shadow: 1px 1px 2px black, 0 0 25px blue, 0 0 5px darkblue;">
    		Yönetici sayfası
    	</p>
    </div>
    <div style="padding: 50px;">
    <div class="panel panel-default">
      	<div class="panel-heading">Şikayetler</div>
      	<table class="table" width="100%" border="0" cellpadding="0" cellspacing="0">
	      	<tr>
		      	<th style="width: 8%;">ID</th>
		      	<th style="width: 12%;">Konum</th>
		      	<th style="width: 12%;">Şikayet türü</th>
		      	<th style="width: 12%;">Şikayet eden</th>
		      	<th style="width: 8%;">Tarih</th>
		      	<th style="width: 35%;">Açıklama</th>
		      	<th style="width: 10%;">Eylem</th>
		    </tr>
      		<c:forEach items="${complaintInfos }" var="data">
      			<tr>
      				<td>${data.id }</td>
			      	<td>${data.locationInfo.description }</td>
			      	<td>${data.supportTypeInfo.type }</td>
			      	<td>${data.complainantUserInfo.username }</td>
			      	<td>${data.complaintTime }</td>
			      	<td>${data.complaintText }</td>
			      	<td>
			      	<a class="btn btn-danger btn-xs" href="${pageContext.request.contextPath}/unifyComplaints?id=${data.id }" role="button">unify</a>
						<a class="btn btn-danger btn-xs" href="${pageContext.request.contextPath}/assignComplaint?id=${data.id }" role="button">Yönlendir</a>
					</td>
			    </tr>
			</c:forEach>
      	</table>
   </div>
   </div>
<footer align="bottom"> &copy; Yildiz Teknik Üniversitesi </footer>
  </body>
	
</html>