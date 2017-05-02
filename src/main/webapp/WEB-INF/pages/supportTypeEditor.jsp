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
	<title>7/24 Servisi | Destek Personeli Tipi Yönetimi</title>
</head>
<body>
    <%@include file="navbar2.jsp" %>
      <div style="padding:50px;">
      <!-- SupportType -->
     <div id="supporterTypeTab">
      <div class="container">
      		<div class="col-md-12">
      			<div class="form"> <!-- for background transparent color -->
      			<!-- form -->
      			<form:form action="saveSupportType" method="POST" modelAttribute="supportTypeForm">
					<div class="form-group">
						<div class="input-group">
							<input id="id" name="id" type="hidden" value=""/>
							<span class="input-group-addon">Tipi</span>
   							<input id="type" type="text" class="form-control" name="type" placeholder="Destekçi tipini belirleyiniz"/>
        					<span class="input-group-btn">
        						<button type="submit" class="btn btn-default" value="Ekle" >Ekle</button>
        					</span>
       					</div>
       				</div>
      			</form:form>
      			
      			<c:if test="${not empty supTypeMsgSuccess}">
					<div class="alert alert-success alert-dismissible" role="alert">
						<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						${supTypeMsgSuccess}
					</div>
				</c:if>
				
				<c:if test="${not empty supTypeMsgError}">
					<div class="alert alert-danger alert-dismissible" role="alert">
						<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						${supTypeMsgError}
					</div>
				</c:if>
				
      			<!-- TABLE <<< -->
      			<div class="panel panel-default">
      				<div class="panel-heading">Destek Personeli:</div>
      					<table class="table" width="100%" border="0" cellpadding="0" cellspacing="0">
					      	<tr>
					      		<th style="width: 10%;">ID</th>
					      		<th style="width: 50%;">Tip</th>
					      	</tr>
					      <c:forEach items="${supportTypeInfos }" var="data">
					      	<tr>
					      		<td>${data.id }</td>
					      		<td>${data.type }</td>
					      		<td>
					      			<a class="btn btn-primary btn-xs" href="#" role="button">Güncelle</a>
						      		<a class="btn btn-danger btn-xs" href="#" role="button">Sil</a>
								</td>
					      	</tr>
					      </c:forEach>
					     </table>
      			</div> <!-- TABLE >>> -->
      		</div>
      		</div>
      	</div>
      </div>
     
     </div><!-- style padding -->
     <footer align="bottom"> &copy; Yildiz Teknik Üniversitesi </footer>
  </body>
  	
</html>