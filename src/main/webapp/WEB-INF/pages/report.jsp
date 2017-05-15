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
<script type="text/javascript">
$(document).ready(function(){
	$('#loc').on('change',function(){
		$.get("${pageContext.request.contextPath}/getComplaintList?id="+ $(this).children("option").filter(":selected").attr("id") +"&id2="+$('#sup').children("option").filter(":selected").attr("id"), null, function (data) {
	        $('#comps > tbody:last-child').html(data);
	    });
	});
	$('#sup').on('change',function(){
		$.get("${pageContext.request.contextPath}/getComplaintList?id="+ $('#loc').children("option").filter(":selected").attr("id") +"&id2="+$(this).children("option").filter(":selected").attr("id"), null, function (data) {
	        $('#comps > tbody:last-child').html(data);
	    });
	});
	$('#loc2').on('change',function(){
		$.get("${pageContext.request.contextPath}/getLocSupComplaints?id="+ $(this).children("option").filter(":selected").attr("id") +"&id2="+$('#sup2').children("option").filter(":selected").attr("id"), null, function (data) {
	        $('#comps3 > tbody:last-child').html(data);
	    });
	});
	$('#sup2').on('change',function(){
		$.get("${pageContext.request.contextPath}/getLocSupComplaints?id="+ $('#loc2').children("option").filter(":selected").attr("id") +"&id2="+$(this).children("option").filter(":selected").attr("id"), null, function (data) {
	        $('#comps3 > tbody:last-child').html(data);
	    });
	});
	$('#loc3').on('change',function(){
		getdata(0);
		$.get("${pageContext.request.contextPath}/getSupporterInfo?id="+ $(this).children("option").filter(":selected").attr("id") +"&id2="+$('#sup3').children("option").filter(":selected").attr("id"), null, function (data) {
	        $('#comps4 > tbody:last-child').html(data);
	    });
	});
	$('#sup3').on('change',function(){
		getdata(0);
		$.get("${pageContext.request.contextPath}/getSupporterInfo?id="+ $('#loc3').children("option").filter(":selected").attr("id") +"&id2="+$(this).children("option").filter(":selected").attr("id"), null, function (data) {
	        $('#comps4 > tbody:last-child').html(data);
	    });
	});
});</script>
<script type="text/javascript">
function getdata(id) {
	$.get("${pageContext.request.contextPath}/getSupportAllComplaints?id="+ id, null, function (data) {
        $('#comps2 > tbody:last-child').html(data);
	});
}
function getalldata(id) {
	$.get("${pageContext.request.contextPath}/getSupportAllComplaints?id="+ id, null, function (data) {
        $('#comps5 > tbody:last-child').html(data);
	});
}
function getactivedata(id) {
	$.get("${pageContext.request.contextPath}/getSupportAllComplaints?id="+ id, null, function (data) {
        $('#comps5 > tbody:last-child').html(data);
	});
}
function getinactivedata(id) {
	$.get("${pageContext.request.contextPath}/getSupportAllComplaints?id="+ id, null, function (data) {
        $('#comps5 > tbody:last-child').html(data);
	});
}
</script>
<body>
    <%@include file="navbar2.jsp" %>	
 	<link href="${tabStyleCSS}" rel="stylesheet" />
    
  <div style="padding:50px;">
    
 	<ul class="nav nav-tabs">
 		<li role="presentation"><a href="#" onclick="$('#locationTab').hide(); $('#supporterTypeTab').hide(); $('#userRoleTab').show(); $('#supporterSetTab').hide()">Konum/Hizmet tipi şikayetleri</a></li>
  		<li role="presentation"><a href="#" onclick="$('#locationTab').show(); $('#supporterTypeTab').hide(); $('#userRoleTab').hide(); $('#supporterSetTab').hide()">Tüm bölümlerin şikayet istatistikleri</a></li>
  		<li role="presentation"><a href="#" onclick="$('#locationTab').hide(); $('#supporterTypeTab').show(); $('#userRoleTab').hide(); $('#supporterSetTab').hide()">Şikayet detayları (progress bar)</a></li>
  		<li role="presentation"><a href="#" onclick="$('#locationTab').hide(); $('#supporterTypeTab').hide(); $('#userRoleTab').hide(); $('#supporterSetTab').show()">Personel şikayet yoğunluğu</a></li>
	</ul>
 	</br>
 	
 	<!-- Location -->
 	<div class="collapse" id="locationTab">
      <div class="container">
      	<div class="row justify-content-center">
      		<div class="col-md-6 offset-md-3">
      			<div class="form"> <!-- for background transparent color -->
      			
      			<div class="input-group">
		    	<span class="input-group-addon"><i class="glyphicon glyphicon-wrench"></i> Konum</span>         
		        <select id="loc2" class="form-control" name="loc2" >
		        	<option id="0" value="0">Konum seçin.</option>
			        <c:forEach items="${loc }" var="data">
			        	<option id="${data.id }" value="${data.id }">${data.description }</option>
			        </c:forEach>
		        </select>
	        </div>
	        <div class="input-group">
		    	<span class="input-group-addon"><i class="glyphicon glyphicon-wrench"></i>Destek tipi</span>         
		        <select id="sup2" class="form-control" name="sup2" >
		        	<option id="0" value="0">Destek tipi seçin.</option>
			        <c:forEach items="${sup }" var="data">
			        	<option id="${data.id }" value="${data.id }">${data.type }</option>
			        </c:forEach>
		        </select>
	        </div>
      			<!-- TABLE <<< -->
      			<div class="panel panel-default">
      				<div class="panel-heading"><p>Toplam süreç : ${total }</p> </div>
      					<table id="comps3" class="table" width="100%" border="0" cellpadding="0" cellspacing="0">
      						<thead>
      						<tr>
					      		<th style="width: 10%;">Konum</th>
					      		<th style="width: 40%;">Destek tipi</th>
					      		<th style="width: 20%;">Toplam şikayet</th>
					      		<th style="width: 20%;">Onay bekleyen şikayetler</th>
					      		<th style="width: 20%;">Atanma bekleyen şikayetler</th>
					      		<th style="width: 20%;">Çocuğunu bekleyen şikayetler</th>
					      		<th style="width: 20%;">Aktif şikayetler</th>
					      		<th style="width: 20%;">Sonlanmış şikayetler</th>
					      		<th style="width: 20%;">Raporlanan şikayetler</th>
      						</tr>
      						</thead>
      						<tbody>
						      <c:forEach items="${LocSupTypeInfo }" var="data">
						      	<tr>
						      		<td>${data.locationInfo.description }</td>
						      		<td>${data.supportTypeInfo.type }</td>
						      		<td onclick="getalldata(${data.locationInfo.id}, ${data.supportTypeInfo.id})">${data.total }</td>
						      		<td onclick="getwaitackdata(${data.locationInfo.id}, ${data.supportTypeInfo.id})">${data.waitAck }</td>
						      		<td onclick="getwaitasgdata(${data.locationInfo.id}, ${data.supportTypeInfo.id})">${data.waitAsg }</td>
						      		<td onclick="getwaitchilddata(${data.locationInfo.id}, ${data.supportTypeInfo.id})">${data.waitChild }</td>
						      		<td onclick="getactivedata(${data.locationInfo.id}, ${data.supportTypeInfo.id})">${data.active }</td>
						      		<td onclick="getendeddata(${data.locationInfo.id}, ${data.supportTypeInfo.id})">${data.ended }</td>
						      		<td onclick="getreportdata(${data.locationInfo.id}, ${data.supportTypeInfo.id})">${data.report }</td>
						      	</tr>
						      </c:forEach>
						    </tbody>
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
      		<div class="input-group">
		    	<span class="input-group-addon"><i class="glyphicon glyphicon-wrench"></i> Konum</span>         
		        <select id="loc" class="form-control" name="loc" >
		        	<option id="0" value="0">Konum seçin.</option>
			        <c:forEach items="${loc }" var="data">
			        	<option id="${data.id }" value="${data.id }">${data.description }</option>
			        </c:forEach>
		        </select>
	        </div>
	        <div class="input-group">
		    	<span class="input-group-addon"><i class="glyphicon glyphicon-wrench"></i>Destek tipi</span>         
		        <select id="sup" class="form-control" name="sup" >
		        	<option id="0" value="0">Destek tipi seçin.</option>
			        <c:forEach items="${sup }" var="data">
			        	<option id="${data.id }" value="${data.id }">${data.type }</option>
			        </c:forEach>
		        </select>
	        </div>
      			<div class="panel panel-default">
      				<div class="panel-heading">Şikayet listesi</div>
      				<table id="comps" class="table table-striped custab" style="background-color: #FFF;">
				    <thead>
				        <tr>
				            <th>ID</th>
				            <th>Konumu</th>
				            <th>Destek tipi</th>
				            <th>Sorumlu kişi</th>
				            <th>Kayıt tarihi</th>
				            <th>Eylem</th>
				        </tr>
				    </thead>
				   	<tbody>
				   		
				   	</tbody>
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
     		<div class="input-group">
		    	<span class="input-group-addon"><i class="glyphicon glyphicon-wrench"></i> Konum</span>         
		        <select id="loc3" class="form-control" name="loc3" >
		        	<option id="0" value="0">Konum seçin.</option>
			        <c:forEach items="${loc }" var="data">
			        	<option id="${data.id }" value="${data.id }">${data.description }</option>
			        </c:forEach>
		        </select>
	        </div>
	        <div class="input-group">
		    	<span class="input-group-addon"><i class="glyphicon glyphicon-wrench"></i>Destek tipi</span>         
		        <select id="sup3" class="form-control" name="sup3" >
		        	<option id="0" value="0">Destek tipi seçin.</option>
			        <c:forEach items="${sup }" var="data">
			        	<option id="${data.id }" value="${data.id }">${data.type }</option>
			        </c:forEach>
		        </select>
	        </div>
      			<div class="panel panel-default">
      				<div class="panel-heading">Destek Ekibi</div>
      				<table id="comps4" class="table" width="100%" border="0" cellpadding="0" cellspacing="0">
      				<thead>
	      				<tr>
				      		<th>Personel adı</th>
				      		<th>Çalıştığı konum</th>
				      		<th>Görevi</th>
				      		<th>Toplam şikayet sayısı</th>
				      		<th>Ort. farkındalık süresi</th>
				      		<th>Ort. cevap süresi</th>
				      		<th>Onay bekleyen şikayet sayısı</th>
				      		<th>Aktif şikayet sayısı</th>
				      		<th>Çocuğunu bekleyen şikayet sayısı</th>
				      		<th>Raporlanan şikayet sayısı</th>
				      		<th>Tamamlanan şikayet sayısı</th>
				      	</tr>
			      	</thead>
			      	<tbody>
				      <c:forEach items="${SupporterRepInfo }" var="data">
				      	<tr>
				      		<td>${data.userInfo.username }</td>
				      		<td>${data.locationInfo.description }</td>
				      		<td>${data.supportTypeInfo.type }</td>
				      		<td onclick="getdata(${data.userInfo.id})">${data.total }</td>
				      		<td>${data.avgAwarenessTime/3600000 }</td>
				      		<td>${data.avgResponseTime/3600000 }</td>
				      		<td>${data.waitingAck }</td>
				      		<td>${data.active }</td>
				      		<td>${data.waitingChild }</td>
				      		<td>${data.reported }</td>
				      		<td>${data.ended }</td>
				      		
						</tr>
				      </c:forEach>
				      </tbody>
				  	</table>
				</div>
				  <!-- TABLE >>> -->
				  <table id="comps2"  class="table table-striped custab" style="background-color: #FFF;">
	    <thead>
	        <tr>
	            <th>ID</th>
	            <th>Konumu</th>
	            <th>Destek tipi</th>
	            <th>Sorumlu kişi</th>
	            <th>Kayıt tarihi</th>
	            <th>Eylem</th>
	        </tr>
	    </thead>
	   	<tbody>
	   		
	   	</tbody>
	    </table>
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