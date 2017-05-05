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
	<title>7/24 Servisi | Destek Personeli Yönetimi</title>
</head>
<body>
    <%@include file="navbar2.jsp" %>
      <div style="padding:50px;">
          <!-- Supporter set -->
      <div id="supporterSetTab">
      <div class="container">
      	
      		<div class="col-md-12">
      			<div class="form"> <!-- for background transparent color -->
      			
      			<form:form action="saveSupporter" method="POST" modelAttribute="supporterForm">
					<div class="form-group">
						<div class="input-group">
							<input id="id" name="id" type="hidden" value=""/>
								<span class="input-group-addon">Kullanıcı: &ensp;&ensp;&ensp;</span>
   								<select id="userId" class="form-control" name="userId" >
   									<option id="" value="">Seçin.</option>
							   		<c:forEach items="${userInfos }" var="data">
							        	<option id="${data.id }" value="${data.id }">${data.username }</option>
							        </c:forEach>
						        </select>
						</div>
						
    					<div class="input-group">
    						<span class="input-group-addon">Tipi: &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;</span>
					        <select id="supportTypeId" class="form-control" name="supportTypeId" >
						        <option id="" value="">Destek tipi seçin.</option>
						   		<c:forEach items="${supportTypeInfos }" var="data">
						        	<option id="${data.id }" value="${data.id }">${data.type }</option>
						        </c:forEach>
					        </select>
          				</div>
          				
          				<div class="input-group">
				        	<span class="input-group-addon">İlgili Konumu:</span>
				        	<select id="locationId" class="form-control" name="locationId" ><option id="" value="">Alt konum seçin.</option>
				   				<c:forEach items="${locationInfos }" var="data">
				        			<option id="${data.id }" value="${data.id }">${data.description }</option>
				        		</c:forEach>
				        	</select>
				        	<span class="input-group-btn">
        						<button type="submit" class="btn btn-default" value="Ekle" >Ekle</button>
        					</span>
        				</div>
       				</div> <!-- form-group -->
      			</form:form>
      			
      			<c:if test="${not empty supMsgSuccess}">
					<div class="alert alert-success alert-dismissible" role="alert">
						<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						${supMsgSuccess}
					</div>
				</c:if>
				
				<c:if test="${not empty supMsgError}">
					<div class="alert alert-danger alert-dismissible" role="alert">
						<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						${supMsgError}
					</div>
				</c:if>
				
     		<!-- TABLE <<< -->
      			<div class="panel panel-default">
      				<div class="panel-heading">Destek Ekibi:</div>
      				<table class="table" width="100%" border="0" cellpadding="0" cellspacing="0">
	      				<tr>
				      		<th style="width: 10%;">ID</th>
				      		<th style="width: 25%;">Destek Personeli</th>
				      		<th style="width: 25%;">Tipi</th>
				      		<th style="width: 20%;">İlgili Konum</th>
				      	</tr>
				      <c:forEach items="${supporterInfos }" var="data">
				      	<tr>
				      		<td>${data.id }</td>
				      		<td>${data.userId }</td>
				      		<td>${data.supportTypeId }</td>
				      		<td>${data.locationId }</td>
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
     
     </div><!-- style padding -->
     <footer align="bottom"> &copy; Yildiz Teknik Üniversitesi </footer>
  </body>
  	
</html>