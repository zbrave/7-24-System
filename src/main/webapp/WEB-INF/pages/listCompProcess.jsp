<%@page session="false"%>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
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
 
 	<c:if test="${not empty message}">
    	<h1>Message : ${message}</h1>
    </c:if>
    
      <div style="padding: 50px;">
    <div class="panel panel-default">
      	<div class="panel-heading">Şikayet Ağacı ("${main.complaintText }" için)</div>
      	<table class="table" width="100%" border="0" cellpadding="0" cellspacing="0">
	      	<tr>
		      	<th style="width: 8%;">ID</th>
		      	<th style="width: 12%;">Konum</th>
		      	<th style="width: 12%;">Destek türü</th>
		      	<th style="width: 12%;">Şikayet eden</th>
		      	<th style="width: 8%;">Tarih</th>
		      	<th style="width: 35%;">Açıklama</th>
		    </tr>
      		<c:forEach items="${tree }" var="data">
      			<tr>
      				<td>${data.id }</td>
			      	<td>${data.locationInfo.description }</td>
			      	<td>${data.supportTypeInfo.type }</td>
			      	<td>${data.complainantUserInfo.username }</td>
			      	<td>${data.complaintTime }</td>
			      	<td>${data.complaintText }</td>
			      	<!--  <td>${data.percentReported }</td> -->
			    </tr>
			</c:forEach>
      	</table>
      	<div class="progress">
      	<c:forEach items="${tree }" var="data">
      		<c:if test = "${data.percentReported > 0}">
		  <div class="progress-bar progress-bar-success progress-bar-striped" style="width: ${data.percentReported }%">
		    <span class="sr-only">${data.percentReported }% Complete (reported)</span>
		    <fmt:formatNumber value="${data.percentReported }" minFractionDigits="0" maxFractionDigits="0"/>%
		  </div>
		  </c:if>
		  <c:if test = "${data.percentAssign > 0}">
		  <div class="progress-bar progress-bar-danger progress-bar-striped" style="width: ${data.percentAssign }%">
		    <span class="sr-only">${data.percentAssign }% Complete (assign)</span><a href="#myModal${data.id}" data-toggle="modal">
		    <fmt:formatNumber value="${data.percentAssign }" minFractionDigits="0" maxFractionDigits="0"/>% 
		    </a>
		  </div>
		  </c:if>
		  <c:if test = "${data.percentAck > 0}">
		  <div class="progress-bar progress-bar-warning progress-bar-striped" style="width: ${data.percentAck }%">
		    <span class="sr-only">${data.percentAck }% Complete (ack)</span><a href="#myModal${data.id}" data-toggle="modal">
		    <fmt:formatNumber value="${data.percentAck }" minFractionDigits="0" maxFractionDigits="0"/>% 
		    </a>
		  </div>
		  </c:if>
		  <c:if test = "${data.percentResponse > 0}">
		  <div class="progress-bar progress-bar-info progress-bar-striped" style="width: ${data.percentResponse }%">
		    <span class="sr-only">${data.percentResponse }% Complete (response)</span><a href="#myModal${data.id}" data-toggle="modal">
		    <fmt:formatNumber value="${data.percentResponse }" minFractionDigits="0" maxFractionDigits="0"/>%
		    </a>
		  </div>
		  </c:if>
		  </c:forEach>
		</div>
   </div>
   </div>
   <c:forEach items="${tree }" var="data">
   <!-- Modal -->
	<div class="modal fade" id="myModal${data.id}" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
	        <h4 class="modal-title" id="myModalLabel">Toplam şikayet yüzdesi: <fmt:formatNumber value="${data.percentAssign+data.percentAck+data.percentResponse }" minFractionDigits="0" maxFractionDigits="0"/>%</h4>
	      </div>
	      <div class="modal-body center-block">
	        	<p>ID</p>${data.id }
		      	<p>Konum</p>${data.locationInfo.description }
		      	<p>Şikayet türü</p>${data.supportTypeInfo.type }
		      	<p>Şikayet eden</p>${data.complainantUserInfo.username }
		      	<p>Tarih</p>${data.complaintTime }
		      	<p>Şikayet açıklaması</p>${data.complaintText }
		      	<p>Şikayet cevabı</p>${data.responseText }
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">Kapat</button>
	      </div>
	    </div><!-- /.modal-content -->
	  </div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
	</c:forEach>
</body>
	
</html>