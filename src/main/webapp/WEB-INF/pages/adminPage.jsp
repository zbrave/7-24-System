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
	<spring:url value="/resources/ico724.png" var="ico" />
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
	<link href='http://fonts.googleapis.com/css?family=Titillium+Web:400,300,600' rel='stylesheet' type='text/css'>
	<link href="${bootstrapCSS}" rel="stylesheet" />
	<link href="${bootstrapThemeCSS}" rel="stylesheet" />
	<!--<link href="${styleCSS}" rel="stylesheet" />	-->
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
      <div class="container">
      	<div class="row justify-content-center">
      		<div class="col-md-6 offset-md-3">
      			<form:form action="saveLocation" method="POST" modelAttribute="locationForm">
					<div class="input-group">
						<input id="id" name="id" type="hidden" value=""/>
						<span class="input-group-addon">Konum adı</span>
						<input id="description" type="text" class="form-control" name="description"/>
    				</div>
    				<div class="input-group">
    					<span class="input-group-addon">Parent &ensp;&ensp;&ensp; </span>
        				<select id="parentId" class="form-control" name="parentId" >
        					<option id="" value="">Alt konum seçin.</option>
   							<c:forEach items="${locationInfos }" var="data">
        						<option id="${data.id }" value="${data.id }">${data.description }</option>
        					</c:forEach>
        				</select>
        				<span class="input-group-btn">
        					<button type="submit" class="btn btn-default" value="Ekle" >Ekle</button>
        				</span>
          			</div>
        			<c:if test="${not empty message5}">
		   				<div class="alert alert-success alert-dismissible" role="alert"><button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>${message5}
		   				</div>
					</c:if> 
      			</form:form>
      			</br>
      			<!-- TABLE ///// -->
      			<div class="panel panel-default">
      				<div class="panel-heading">Locations:</div>
      					<table class="table" width="100%" border="0" cellpadding="0" cellspacing="0">
      						<tr>
					      		<th style="width: 5%;">ID</th>
					      		<th style="width: 40%;">Tanım</th>
					      		<th style="width: 5%;">Parent</th>
      						</tr>
						      <c:forEach items="${locationInfos }" var="data">
						      	<tr>
						      		<td>${data.id }</td>
						      		<td>${data.description }</td>
						      		<td>${data.parent.id }</td>
						      		<td><a class="btn btn-primary btn-xs" href="#" role="button">Güncelle</a>
						      			<a class="btn btn-danger btn-xs" href="#" role="button">Sil</a>
									</td>
						      	</tr>
						      </c:forEach>
      					</table>
      			</div>
      		</div>
      	</div>
      </div>
      
      <!-- SupportType -->
      
      <div class="container">
      	<div class="row justify-content-center">
      		<div class="col-md-6 offset-md-3">
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
      			<div class="panel panel-default">
      				<div class="panel-heading">Supporters:</div>
      					<table class="table" width="100%" border="0" cellpadding="0" cellspacing="0">
					      	<tr>
					      		<th style="width: 5%;">ID</th>
					      		<th style="width: 50%;">Tip</th>
					      	</tr>
					      <c:forEach items="${supportTypeInfos }" var="data">
					      	<tr>
					      		<td>${data.id }</td>
					      		<td>${data.type }</td>
					      		<td><a class="btn btn-primary btn-xs" href="#" role="button">Güncelle</a>
						      			<a class="btn btn-danger btn-xs" href="#" role="button">Sil</a>
								</td>
					      	</tr>
					      </c:forEach>
					     </table>
      			</div>
      		</div>
      	</div>
      </div>
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
<footer align="bottom"> &copy; Yildiz Teknik Üniversitesi </footer>
  </body>
	<script src='js/jquery.min.js'></script>
	<script src='js/bootstrap.min.js'></script>
</html>