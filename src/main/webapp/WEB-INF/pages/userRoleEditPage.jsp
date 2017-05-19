<%@page session="true"%>
<%@ page pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tag" uri="/WEB-INF/taglibs/customTaglib.tld"%>
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
	<title>7/24 Servisi | Kullanıcı Rolü Yönetimi</title>
</head>
<body>
    <%@include file="navbar2.jsp" %>
      <div style="padding:50px;">
      <!-- UserRole set -->
      <div id="userRoleTab">
      <div class="container">
      		<div class="col-md-12">
      		<div class="form"> <!-- for background transparent color -->
      		<!-- form -->
      			<form:form action="saveUserRole" method="POST" modelAttribute="userRoleForm">
					<div class="form-group">
						<div class="input-group">
							<input id="id" name="id" type="hidden" value=""/>
							<span class="input-group-addon">Kullanıcı:</span>
   							<select id="userId" class="form-control" name="userId" >
   								<option id="" value="">Seçin.</option>
   								<c:forEach items="${userInfos }" var="data">
        							<option id="${data.id }" value="${data.id }">${data.username }</option>
       							</c:forEach>
        					</select>
        				</div>
    					<div class="input-group">
    						<span class="input-group-addon">Rolü: &ensp;&ensp;&ensp;</span>
					        <select id="role" class="form-control" name="role" >
					        	<option value="ADMIN">ADMIN</option>
					        	<option value="USER">USER</option>
					        	<option value="SUPPORT">SUPPORT</option>
					        </select>
					        <span class="input-group-btn">
        						<button type="submit" class="btn btn-default" value="Ekle" >Ekle</button>
        					</span>
          				</div>
          			</div> <!-- form-group -->
			    </form:form>
			    
      			<c:if test="${not empty userRoleMsgSuccess}">
					<div class="alert alert-success alert-dismissible" role="alert">
						<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						${userRoleMsgSuccess}
					</div>
				</c:if>
				
				<c:if test="${not empty userRoleMsgError}">
					<div class="alert alert-danger alert-dismissible" role="alert">
						<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						${userRoleMsgError}
					</div>
				</c:if>
      		
      		<!-- TABLE <<< -->
      			<div class="panel panel-default">
      				<div class="panel-heading">Kullanıcı ID ve Rolleri:</div>
      				<table class="table" width="100%" border="0" cellpadding="0" cellspacing="0">
	      				<tr>
					      	<th style="width: 10%;">ID</th>
					      	<th style="width: 20%;">Kullanıcı adı</th>
					      	<th style="width: 30%;">Rolü</th>
					      	<th style="width: 10%;">Eylem</th>
					    </tr>
	      				<c:forEach items="${userRoleInfos }" var="data" varStatus="itr">
							<tr>
						      <td>${offset + itr.index +1 }</td>
						      <!--  <td>${data.id }</td> -->
						      <td><c:forEach items="${userInfos }" var="data2">
        							<c:if test="${data2.id == data.userId }">${data2.username }</c:if>
       							</c:forEach></td>
						      <td>${data.role }</td>
						      <td>
						      	  <a class="btn btn-danger btn-xs" href="${pageContext.request.contextPath}/deleteUserRole?id=${data.id}" role="button" onclick="confirmDelete()">Sil</a>
							  </td>
						    </tr>
						</c:forEach>
					</table>
				</div> <!-- TABLE >>> -->
     			<tag:paginate max="15" offset="${offset}" count="${count}"
						uri="userRoleEdit" next="&raquo;" previous="&laquo;" />
			</div>
			</div>
      	</div>
      </div>
     
     </div><!-- style padding -->
     <footer align="bottom"> &copy; Yildiz Teknik Üniversitesi </footer>
  </body>
  	<script>
		function confirmDelete() {
		    confirm("Silmek istediğinize emin misiniz?");
		}
	</script>
</html>