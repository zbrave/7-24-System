<%@page session="true"%>
<%@ page pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
 
    <h2>Admin Page</h2>
 
 	<form:form action="saveLocation" method="POST" modelAttribute="locationForm">
	<div class="form-group">
		<input id="id" name="id" type="hidden" value=""/>
		
		<label class="control-label">Konum adı</label>
					 			
   		<input id="description" type="text" class="form-control" name="description" />
    
    	<label class="control-label">Parent</label>
                       
        <select id="parent" class="form-control" name="parent" ></select>
          
        
        
        <button type="submit" class="btn btn-default" value="Ekle" >Ekle</button>
			        
        <c:if test="${not empty message5}">
		   <div class="alert alert-success alert-dismissible" role="alert"><button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>${message5}
		   </div>
		</c:if> 
       </div>
      </form:form>
    <h3>Welcome : ${pageContext.request.userPrincipal.name}</h3>
 
    <b>This is protected page! Just admins can reach this page.</b>  
</body>
</html>