<%@page session="true"%>
<%@ page pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
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
function getalldata(id,id2,id3,id4,id5) {
	$.get("${pageContext.request.contextPath}/getComplexComplaintList?id="+ id + "&id2=" + id2 + "&id3=" + id3 + "&id4=" + id4 + "&id5=" + id5, null, function (data) {
        $('#comps5 > tbody:last-child').html(data);
        console.log("id: "+id + id2 + id3 + id4 + id5)
	});
}
function getalldata2(id,id2,id3,id4,id5) {
	$.get("${pageContext.request.contextPath}/getComplexComplaintList?id="+ id + "&id2=" + id2 + "&id3=" + id3 + "&id4=" + id4 + "&id5=" + id5, null, function (data) {
        $('#comps2 > tbody:last-child').html(data);
        console.log("id: "+id + id2 + id3 + id4 + id5)
	});
}
</script>
<body>
    <%@include file="navbar2.jsp" %>	
 	<link href="${tabStyleCSS}" rel="stylesheet" />
    
  <div style="padding:50px;">
    <!-- *******  TAB MENU   ******* -->
 	<ul class="nav nav-tabs">
 		<li role="presentation">
 			<a href="#" onclick="$('#allDepartmentStatistics').hide(); $('#locationServiceComplaints').show(); $('#supportPersonnelDensity').hide()">
 				Konum/Hizmet tipi şikayetleri
 			</a>
 		</li>
  		<li role="presentation">
  			<a href="#" onclick="$('#allDepartmentStatistics').show(); $('#locationServiceComplaints').hide(); $('#supportPersonnelDensity').hide()">
  			Tüm bölümlerin şikayet istatistikleri
  			</a>
  		</li>
  		<li role="presentation">
  			<a href="#" onclick="$('#allDepartmentStatistics').hide(); $('#locationServiceComplaints').hide(); $('#supportPersonnelDensity').show()">
  		Personel şikayet yoğunluğu
  			</a>
  		</li>
	</ul>
	<!-- *******  TAB MENU END HERE   ******* -->
	
 	</br>
 	
 	<!---- Tüm bölümlerin şikayet istatistikleri ---->
 	<div class="collapse" id="allDepartmentStatistics">
      <div class="container">
      	<div class="row justify-content-center">
      		<div class="col-md-12">
      			<div class="form" style="max-width: 900px;"> <!-- for background transparent color -->
      				<div class="input-group">
		    			<span class="input-group-addon"><i class="glyphicon glyphicon-globe"></i> Konum</span>         
		        		<select id="loc2" class="form-control" name="loc2" >
				        	<option id="0" value="0">Konum seçin.</option>
					        <c:forEach items="${loc }" var="data">
					        	<option id="${data.id }" value="${data.id }">${data.description }</option>
					        </c:forEach>
				        </select>
	        		</div>
			        <div class="input-group">
				    	<span class="input-group-addon"><i class="glyphicon glyphicon-wrench"></i> Destek tipi</span>         
				        <select id="sup2" class="form-control" name="sup2" >
				        	<option id="0" value="0">Destek tipi seçin.</option>
					        <c:forEach items="${sup }" var="data">
					        	<option id="${data.id }" value="${data.id }">${data.type }</option>
					        </c:forEach>
				        </select>
			        </div>
			        
			        </br>
      			<!-- >>> TABLE <<< -->
      			<div class="panel panel-default">
      				<div class="panel-heading"><p>Toplam süreç : ${total }</p> </div>
      					<table id="comps3" class="table" width="100%" border="0" cellpadding="0" cellspacing="0">
      						<thead>
	      						<tr>
						      		<th style="width: 10%;">Konum</th>
						      		<th style="width: 10%;">Destek tipi</th>
						      		<th style="width: 10%;">Toplam şikayet</th>
						      		<th style="width: 10%;">Onay bekleyen şikayetler</th>
						      		<th style="width: 10%;">Atanma bekleyen şikayetler</th>
						      		<th style="width: 10%;">Çocuğunu bekleyen şikayetler</th>
						      		<th style="width: 10%;">Aktif şikayetler</th>
						      		<th style="width: 10%;">Sonlanmış şikayetler</th>
						      		<th style="width: 10%;">Raporlanan şikayetler</th>
	      						</tr>
      						</thead>
      						<tbody>
						      <c:forEach items="${LocSupTypeInfo }" var="data">
						      	<tr>
						      		<td>${data.locationInfo.description }</td>
						      		<td>${data.supportTypeInfo.type }</td>
						      		<td onclick="getalldata(${data.locationInfo.id}, ${data.supportTypeInfo.id}, 0, 0, 1)">
						      			<c:if test="${data.total != 0 }"><a data-toggle="modal" data-target="#childTable"> <!--  onclick="$('#tab').show();"--> ${data.total }</a></c:if>
						      			<c:if test="${data.total == 0 }">${data.total }</c:if>
						      		</td>
						      		<td onclick="getalldata(${data.locationInfo.id}, ${data.supportTypeInfo.id}, 0, 0, 2)">
						      			<c:if test="${data.waitAck != 0 }"><a data-toggle="modal" data-target="#childTable">${data.waitAck }</a></c:if>
						      			<c:if test="${data.waitAck == 0 }">${data.waitAck }</c:if>
						      		</td>
						      		<td onclick="getalldata(${data.locationInfo.id}, ${data.supportTypeInfo.id}, 0, 0, 3)">
						      			<c:if test="${data.waitAsg != 0 }"><a data-toggle="modal" data-target="#childTable">${data.waitAsg }</a></c:if>
						      			<c:if test="${data.waitAsg == 0 }">${data.waitAsg }</c:if>
						      		</td>
						      		<td onclick="getalldata(${data.locationInfo.id}, ${data.supportTypeInfo.id}, 0, 0, 4)">
						      			<c:if test="${data.waitChild != 0 }"><a data-toggle="modal" data-target="#childTable">${data.waitChild }</a></c:if>
						      			<c:if test="${data.waitChild == 0 }">${data.waitChild }</c:if>
						      		</td>
						      		<td onclick="getalldata(${data.locationInfo.id}, ${data.supportTypeInfo.id}, 0, 0, 5)">
						      			<c:if test="${data.active != 0 }"><a data-toggle="modal" data-target="#childTable">${data.active }</a></c:if>
						      			<c:if test="${data.active == 0 }">${data.active }</c:if>
						      		</td>
						      		<td onclick="getalldata(${data.locationInfo.id}, ${data.supportTypeInfo.id}, 0, 0, 6)">
						      			<c:if test="${data.ended != 0 }"><a data-toggle="modal" data-target="#childTable">${data.ended }</a></c:if>
						      			<c:if test="${data.ended == 0 }">${data.ended }</c:if>
						      		</td>
						      		<td onclick="getalldata(${data.locationInfo.id}, ${data.supportTypeInfo.id}, 0, 0, 7)">
						      			<c:if test="${data.report != 0 }"><a data-toggle="modal" data-target="#childTable">${data.report }</a></c:if>
						      			<c:if test="${data.report == 0 }">${data.report }</c:if>
						      		</td>
						      	</tr>
						      </c:forEach>
						    </tbody>
      					</table>

      			</div>
      			<!-- <<< TABLE >>> -->
      			
      			  <!-- Modal  **POP** -->
				  <div class="modal fade" id="childTable" role="dialog">
				    <div class="modal-dialog">
				    
				      <!-- Modal content-->
				      <div class="modal-content">
				        <div class="modal-header">
				          <button type="button" class="close" data-dismiss="modal">&times;</button>
				          <h4 class="modal-title">Detaylı Liste</h4>
				        </div>
				        <div class="modal-body">
				        
      			
      			
	      			<table id="comps5" class="table table-striped custab" style="background-color: #FFF;">
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
				        <div class="modal-footer">
				          <button type="button" class="btn btn-default" data-dismiss="modal">Kapat</button>
				        </div>
				      </div>
				      
				    </div>
				  </div>
				
				
      			</div><!-- div-form -->
      		</div>
      	</div>
      </div>
     </div>  <!-- Tüm bölümlerin şikayet istatistikleri -->
      
      
     
      <!---- Konum/Hizmet tipi şikayetleri ---->
      <div class="collapse" id="locationServiceComplaints">
      <div class="container">
      	<div class="row justify-content-center">
      		<div class="col-md-12">
      		<div class="form" style="max-width: 900px;"> <!-- for background transparent color -->
      		
      		
      		<!-- TABLE <<< -->
      		<div class="input-group">
		    	<span class="input-group-addon"><i class="glyphicon glyphicon-globe"></i> Konum</span>         
		        <select id="loc" class="form-control" name="loc" >
		        	<option id="0" value="0">Konum seçin.</option>
			        <c:forEach items="${loc }" var="data">
			        	<option id="${data.id }" value="${data.id }">${data.description }</option>
			        </c:forEach>
		        </select>
	        </div>
	        <div class="input-group">
		    	<span class="input-group-addon"><i class="glyphicon glyphicon-wrench"></i> Destek tipi</span>         
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
     
      <!---- Personel şikayet yoğunluğu ---->
      <div class="collapse" id="supportPersonnelDensity">
      <div class="container">
      	<div class="row justify-content-center">
      		<div class="col-md-12">
      			<div class="form" style="max-width: 900px;"> <!-- for background transparent color -->
      			
      			
      			
     		<!-- TABLE <<< -->
     		<div class="input-group">
		    	<span class="input-group-addon"><i class="glyphicon glyphicon-globe"></i> Konum</span>         
		        <select id="loc3" class="form-control" name="loc3" >
		        	<option id="0" value="0">Konum seçin.</option>
			        <c:forEach items="${loc }" var="data">
			        	<option id="${data.id }" value="${data.id }">${data.description }</option>
			        </c:forEach>
		        </select>
	        </div>
	        <div class="input-group">
		    	<span class="input-group-addon"><i class="glyphicon glyphicon-wrench"></i> Destek tipi</span>         
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
				      		<td onclick="getalldata2(${data.locationInfo.id}, ${data.supportTypeInfo.id}, 0, ${data.userInfo.id}, 1)">${data.total }</td>
				      		<td><fmt:formatNumber value="${data.avgAwarenessTime/3600000 }" minFractionDigits="0" maxFractionDigits="2"/></td>
				      		<td><fmt:formatNumber value="${data.avgResponseTime/3600000 }" minFractionDigits="0" maxFractionDigits="2"/></td>
				      		<td onclick="getalldata2(${data.locationInfo.id}, ${data.supportTypeInfo.id}, 0, ${data.userInfo.id}, 2)">${data.waitingAck }</td>
				      		<td onclick="getalldata2(${data.locationInfo.id}, ${data.supportTypeInfo.id}, 0, ${data.userInfo.id}, 5)">${data.active }</td>
				      		<td onclick="getalldata2(${data.locationInfo.id}, ${data.supportTypeInfo.id}, 0, ${data.userInfo.id}, 4)">${data.waitingChild }</td>
				      		<td onclick="getalldata2(${data.locationInfo.id}, ${data.supportTypeInfo.id}, 0, ${data.userInfo.id}, 7)">${data.reported }</td>
				      		<td onclick="getalldata2(${data.locationInfo.id}, ${data.supportTypeInfo.id}, 0, ${data.userInfo.id}, 6)">${data.ended }</td>
				      		
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