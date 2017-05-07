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
	<title>${title}</title>
</head>
<body>
    <%@include file="navbar2.jsp" %>	
 	<link href="${tabStyleCSS}" rel="stylesheet" />
    <h2>Admin Page</h2>
    
  <div style="padding:50px;">
    
 	<ul class="nav nav-tabs">
 		<li role="presentation"><a href="#" onclick="$('#locationTab').hide(); $('#supporterTypeTab').hide(); $('#userRoleTab').show(); $('#supporterSetTab').hide()">Bölüm-Şikayet</a></li>
  		<li role="presentation"><a href="#" onclick="$('#locationTab').show(); $('#supporterTypeTab').hide(); $('#userRoleTab').hide(); $('#supporterSetTab').hide()">Ortalama Çözüm Süresi</a></li>
  		<li role="presentation"><a href="#" onclick="$('#locationTab').hide(); $('#supporterTypeTab').show(); $('#userRoleTab').hide(); $('#supporterSetTab').hide()">Şikayet detayları (progress bar)</a></li>
  		<li role="presentation"><a href="#" onclick="$('#locationTab').hide(); $('#supporterTypeTab').hide(); $('#userRoleTab').hide(); $('#supporterSetTab').show()">Top users(En hızlı çözen vs)</a></li>
	</ul>
 	</br>
 	<p>Total avg process time : ${avgProcess }</p> 
 	<p>Total complaints : ${total }</p> 
 	<!-- Location -->
 	<div class="collapse" id="locationTab">
      <div class="container">
      	<div class="row justify-content-center">
      		<div class="col-md-6 offset-md-3">
      			<div class="form"> <!-- for background transparent color -->
      			
      			</br>
      			
      			<!-- TABLE <<< -->
      			<div class="panel panel-default">
      				<div class="panel-heading">Raporlama</div>
      					<table class="table" width="100%" border="0" cellpadding="0" cellspacing="0">
      						<tr>
					      		<th style="width: 10%;">ID</th>
					      		<th style="width: 40%;">Destek tipi</th>
					      		<th style="width: 20%;">Toplam şikayet</th>
					      		<th style="width: 20%;">Bekleyen şikayetler</th>
					      		<th style="width: 20%;">Aktif şikayetler</th>
      						</tr>
						      <c:forEach items="${supTypeAvgProcess }" var="data">
						      	<tr>
						      		<td>${data.id }</td>
						      		<td>${data.type }</td>
						      		<td>${data.total }</td>
						      		<td>${data.wait }</td>
						      		<td>${data.active }</td>
						      	</tr>
						      </c:forEach>
      					</table>
      			</div>  <!-- TABLE >>> -->
      			</div>
      		</div>
      	</div>
      </div>
     </div>  <!-- LocationTab -->
      
      <!-- SupportType -->
     <div class="collapse" id="supporterTypeTab">
      <div class="container">
      	<div class="row justify-content-center">
      		<div class="col-md-6 offset-md-3">
      			<div class="form"> <!-- for background transparent color -->
      			
      			
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
      </div>
     
      <!-- UserRole set -->
      <div class="collapse" id="userRoleTab">
      <div class="container">
      	<div class="row justify-content-center">
      		<div class="col-md-6 offset-md-3">
      		<div class="form"> <!-- for background transparent color -->
      		
      		
      		<!-- TABLE <<< -->
      			<div class="panel panel-default">
      				<div class="panel-heading">Kullanıcı ID ve Rolleri:</div>
      				<table class="table" width="100%" border="0" cellpadding="0" cellspacing="0">
	      				<tr>
					      	<th style="width: 10%;">ID</th>
					      	<th style="width: 10%;">UserId</th>
					      	<th style="width: 30%;">Role</th>
					    </tr>
	      				<c:forEach items="${userRoleInfos }" var="data">
							<tr>
						      <td>${data.id }</td>
						      <td><c:forEach items="${userInfos }" var="user"><c:if test="${data.userId == user.id }">${user.username }</c:if></c:forEach></td>
						      <td>${data.role }</td>
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
     </div>
     
      <!-- Supporter set -->
      <div class="collapse" id="supporterSetTab">
      <div class="container">
      	<div class="row justify-content-center">
      		<div class="col-md-6 offset-md-3">
      			<div class="form"> <!-- for background transparent color -->
      			
      			
      			
     		<!-- TABLE <<< -->
      			<div class="panel panel-default">
      				<div class="panel-heading">Destek Ekibi:</div>
      				<table class="table" width="100%" border="0" cellpadding="0" cellspacing="0">
	      				<tr>
				      		<th>ID</th>
				      		<th>Destek Personeli</th>
				      		<th>Tipi</th>
				      		<th>İlgili Konum</th>
				      	</tr>
				      <c:forEach items="${supporterInfos }" var="data">
				      	<tr>
				      		<td>${data.id }</td>
				      		<td>${data.userInfo.username }</td>
				      		<td>${data.supportTypeInfo.type }</td>
				      		<td>${data.locationInfo.description }</td>
				      	<td>
						    <a class="btn btn-primary btn-xs" href="#" role="button">Güncelle</a>
						    <a class="btn btn-danger btn-xs" href="#" role="button">Sil</a>
						</td>
						</tr>
				      </c:forEach>
				  	</table>
				</div>
				  <!-- TABLE >>> -->
			</div>
			</div>
      	</div>
      </div>
     </div>
     
      <!--  
    <h3>Welcome : ${pageContext.request.userPrincipal.name}</h3>
 
    <b>This is protected page! Just admins can reach this page.</b>  -->
  </div>
<footer align="bottom"> &copy; Yildiz Teknik Üniversitesi </footer>
  </body>
  	
</html>