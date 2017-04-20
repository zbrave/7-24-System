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
 
 
    <h1>Message : ${message}</h1>
    
    <!-- Complaint set -->
      <form:form action="saveComplaint" method="POST" modelAttribute="complaintForm">
	<div class="form-group">
		<input id="id" name="id" type="hidden" value=""/>
		
		<label class="control-label">Loc</label>
					 			
   		<select id="locationId" class="form-control" name="locationId" >
   		<c:forEach items="${locationInfos }" var="data">
        	<option id="${data.id }" value="${data.id }">${data.description }</option>
        </c:forEach>
        </select>
    
    	<label class="control-label">supporttype</label>
                       
        <select id="supportTypeId" class="form-control" name="supportTypeId" >
        <c:forEach items="${supportTypeInfos }" var="data">
        	<option id="${data.id }" value="${data.id }">${data.type }</option>
        </c:forEach>
        </select>
        
        <label class="control-label">comp user id</label>
                       
        <input id="complainantUserId" class="form-control" name="complainantUserId" value="${userInfo.id }" />
        
        <label class="control-label">comp text</label>
                       
        <input id="complaintText" class="form-control" name="complaintText" />
        
        
        
        <button type="submit" class="btn btn-default" value="Ekle" >Ekle</button>
			        
        <c:if test="${not empty compMsg}">
		   <div class="alert alert-success alert-dismissible" role="alert"><button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>${compMsg}
		   </div>
		</c:if> 
       </div>
      </form:form>
</body>
	
</html>