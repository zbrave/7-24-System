<%@page session="true"%>
<%@ page pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
	<spring:url value="/resources/css/bootstrap.min.css" var="bootstrapCSS" />
	<spring:url value="/resources/css/style.css" var="styleCSS" />
	<spring:url value="/resources/js/bootstrap.min.js" var="bootstrapJS" />
	<spring:url value="/resources/js/loginForm.js" var="loginJS" />
	<spring:url value="/resources/js/jquery.js" var="jqueryJS" />
	<spring:url value="/resources/ico724.png" var="ico" />
	<link href='http://fonts.googleapis.com/css?family=Titillium+Web:400,300,600' rel='stylesheet' type='text/css'>
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
	<link href="${bootstrapCSS}" rel="stylesheet" />
	<script src="${bootstrapJS}"></script>
	<link href="${styleCSS}" rel="stylesheet" />
	
	<link rel="icon" href="${ico}">
	<title>7/24 Servisi | Login</title>	
</head>
<body>
	<header>
		<div class="anim">
		<h1 class="headerText">7/24 Servisine Hoş Geldiniz!</h1>
		</div>
	</header>
	
	<div class="signin-signup-form">
			<div class="form">
			  
				<ul class="tab-group">
					<li class="tab"><a href="#signup" onclick="$('#signup').show(); $('#login').hide()">Kullanıcı Kayıt</a></li>
					<li class="tab active"><a href="#login" onclick="$('#login').show(); $('#signup').hide()">Kullanıcı Giriş</a></li>
				</ul>
				<div class="tab-content">
				
					<div id="login">   
						<h1>Kullanıcı Giriş</h1>
						<form method="post" action="${pageContext.request.contextPath}/j_spring_security_check" role="login" method='POST'>
							<!-- /login?error=true -->
						     <c:if test="${param.error == 'true'}">
						        <div class="alert alert-danger" role="alert">
						  			<a href="#" class="alert-link">Login Failed!!!<br />
						                Reason :  ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}</a>
								</div>
						    </c:if>
							<div class="field-wrap">
								<label>
									Eposta Adresi<span class="req">*</span>
								</label>
								
								<input type="text"  id="username"  name="username"/>
							</div>
							<div class="field-wrap">
								<label>
									Şifre<span class="req">*</span>
								</label>
								<input type="password" id="password" name='password'/>
							</div>
							<p class="forgot"><a href="#">Şifremi Unuttum?</a></p>
							<button class="button button-block" type="submit" name="go">Giriş Yap</button>
						</form>
					</div>
					
					<div id="signup">   
						<h1>Kullanıcı Kayıt</h1>
				  		<form method="post" action="${pageContext.request.contextPath}/j_spring_security_check" role="signup" method='POST'>
								  <!-- /signup?error=true -->
						     <c:if test="${param.error == 'true'}">
						        <div class="alert alert-danger" role="alert">
						  			<a href="#" class="alert-link">SignUp Failed!!!<br />
						                Reason :  ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}</a>
								</div>
						    </c:if>
							 <div class="top-row">
								<div class="field-wrap">
									<label>
										İsim<span class="req">*</span>
									</label>
									<input type="text" errorContainer="İsminizi Yaziniz" required autocomplete="off" />
								</div>
								<div class="field-wrap">
									<label>
										Soyisim<span class="req">*</span>
									</label>
									<input type="text"required autocomplete="off"/>
								</div>
							</div>

							<div class="field-wrap">
								<label>
									Eposta Adresi<span class="req">*</span>
								</label>
								<input type='text' id="username" name='username'/>
							</div>
							
							<div class="field-wrap">
								<label>
								Yeni şifre<span class="req">*</span>
								</label>
								<input type="password" id="password" name='password'/>
							</div>
							<button type="submit" name="go" class="button button-block">Kaydol</button>

						</form>
					</div>
				</div><!-- tab-content -->
			</div> <!-- /form -->
		</div>
	<script src="${loginJS}"></script>
</body>
</html>