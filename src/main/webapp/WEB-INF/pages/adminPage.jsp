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
<script type="text/javascript">
$(document).ready(function(){
	$.get("${pageContext.request.contextPath}/getUserList", null, function (data) {
        $("#userId").html(data);
    });
});</script>
<body>
    <%@include file="navbar2.jsp" %>	
 
    <h2>Admin Page</h2>
 
 	<!-- Location -->
 	<form:form action="saveLocation" method="POST" modelAttribute="locationForm">
	<div class="form-group">
		<input id="id" name="id" type="hidden" value=""/>
		
		<label class="control-label">Konum adı</label>
					 			
   		<input id="description" type="text" class="form-control" name="description" />
    
    	<label class="control-label">Parent</label>
                       
        <select id="parentId" class="form-control" name="parentId" >
        <option id="" value="">Alt konum seçin.</option>
   		<c:forEach items="${locationInfos }" var="data">
        	<option id="${data.id }" value="${data.id }">${data.description }</option>
        </c:forEach>
        </select>
          
        
        
        <button type="submit" class="btn btn-default" value="Ekle" >Ekle</button>
			        
        <c:if test="${not empty message5}">
		   <div class="alert alert-success alert-dismissible" role="alert"><button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>${message5}
		   </div>
		</c:if> 
       </div>
      </form:form>
      
      
      <table>
      	<tr>
      		<th>ID</th>
      		<th>Tanım</th>
      		<th>Parent</th>
      	</tr>
      <c:forEach items="${locationInfos }" var="data">
      	<tr>
      		<td>${data.id }</td>
      		<td>${data.description }</td>
      		<td>${data.parent.id }</td>
      	</tr>
      </c:forEach>
      </table>
      
      <!-- SupportType -->
      <form:form action="saveSupportType" method="POST" modelAttribute="supportTypeForm">
	<div class="form-group">
		<input id="id" name="id" type="hidden" value=""/>
		
		<label class="control-label">Tip</label>
					 			
   		<input id="type" type="text" class="form-control" name="type" />
          
        
        
        <button type="submit" class="btn btn-default" value="Ekle" >Ekle</button>
			        
        <c:if test="${not empty message5}">
		   <div class="alert alert-success alert-dismissible" role="alert"><button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>${message5}
		   </div>
		</c:if> 
       </div>
      </form:form>
      <table>
      	<tr>
      		<th>ID</th>
      		<th>Tip</th>
      	</tr>
      <c:forEach items="${supportTypeInfos }" var="data">
      	<tr>
      		<td>${data.id }</td>
      		<td>${data.type }</td>
      	</tr>
      </c:forEach>
      </table>
      
      <!-- UserRole set -->
      <form:form action="saveUserRole" method="POST" modelAttribute="userRoleForm">
	<div class="form-group">
		<input id="id" name="id" type="hidden" value=""/>
		
		<label class="control-label">User</label>
					 			
   		<select id="userId" class="form-control" name="userId" ></select>
    
    	<label class="control-label">Role</label>
                       
        <select id="role" class="form-control" name="role" >
        	<option value="ADMIN">ADMIN</option>
        	<option value="USER">USER</option>
        	<option value="MANAGER">MANAGER</option>
        	<option value="SUPPORT">SUPPORT</option>
        </select>
          
        
        
        <button type="submit" class="btn btn-default" value="Ekle" >Ekle</button>
			        
        <c:if test="${not empty message5}">
		   <div class="alert alert-success alert-dismissible" role="alert"><button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>${message5}
		   </div>
		</c:if> 
       </div>
      </form:form>
     <table>
      	<tr>
      		<th>ID</th>
      		<th>UserId</th>
      		<th>Role</th>
      	</tr>
      <c:forEach items="${userRoleInfos }" var="data">
      	<tr>
      		<td>${data.id }</td>
      		<td>${data.userInfo.id }</td>
      		<td>${data.role }</td>
      	</tr>
      </c:forEach>
      </table>
    <h3>Welcome : ${pageContext.request.userPrincipal.name}</h3>
 
    <b>This is protected page! Just admins can reach this page.</b>  
</body>
</html>