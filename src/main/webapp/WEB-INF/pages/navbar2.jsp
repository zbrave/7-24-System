<%@ page pageEncoding="UTF-8" %>

<script type="text/javascript">
$(document).ready(function(){
	var path = window.location.pathname.split("/").pop();
	$('#' + path).addClass("active");
});
</script>

<nav class="navbar navbar-default">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="${pageContext.request.contextPath}/welcome">
      		<span class="glyphicon glyphicon-home"></span>
      		Ana Sayfa</a>
    </div>
    <ul class="nav navbar-nav">
    	<li><a href="${pageContext.request.contextPath}/admin" >Admin page</a></li>
		<li><a href="${pageContext.request.contextPath}/manager" >Manager page</a></li>
		<li><a href="${pageContext.request.contextPath}/supporter" >Supporter page</a></li>
		<li><a href="${pageContext.request.contextPath}/userInfo" >UserInfo page</a></li>
    </ul>
	<c:if test="${pageContext.request.userPrincipal.name != null}">
		<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
			<c:forEach var="role" items="${pageContext['request'].userPrincipal.principal.authorities}">
	    		
	    		<c:if test="${role.authority == 'ROLE_USER'}">
					<ul class="nav navbar-nav">
						<li><a href="#tableInfo" data-toggle="collapse">İşlem geçmişim</a></li>
					</ul>
	      			<ul class="nav navbar-nav">
	        			<li><a href="#newComplaint" data-toggle="collapse">Şikayet Oluştur</a></li>
	      			</ul>
				</c:if>
				
				<c:if test="${role.authority == 'ROLE_ADMIN'}">
					<!--  <li><a href="${pageContext.request.contextPath}/admin" >Admin page</a></li>
					<li><a href="${pageContext.request.contextPath}/manager" >Manager page</a></li>
					<li><a href="${pageContext.request.contextPath}/supporter" >Supporter page</a></li> -->
					<ul class="nav navbar-nav">
				    	<li class="dropdown">
				          	<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
				          		Şikayetler <span class="caret"></span>
				          	</a>
				          		<ul class="dropdown-menu">
				            		<li><a href="#">Görüntüleme</a></li>
									<li role="separator" class="divider"></li>
				            		<li><a href="#">Ekleme</a></li>
				            		<li><a href="#">Yönetme</a></li>
				          		</ul>
				        </li>
						<li class="dropdown">
				          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
				          	Destek Ekibi <span class="caret"></span>
				          </a>
				          	<ul class="dropdown-menu">
				            	<li><a href="#"></a></li>
				            	<li><a href="#">Ekleme</a></li>
				            	<li><a href="#">Yönetme</a></li>
				            	<li role="separator" class="divider"></li>
				            	<li><a href="#">Raporlama</a></li>
				          	</ul>
				        </li>
						<li><a href="#">Rapor</a></li>
				      </ul>
				</c:if>
				
				<c:if test="${role.authority == 'ROLE_MANAGER'}">
					<ul class="nav navbar-nav">
				        <li><a href="#newComplaint" data-toggle="collapse">Şikayet Oluştur</a></li>
						<li><a href="#tableInfo" data-toggle="collapse">Takip</a></li>
						<li><a href="#">Raporlar</a></li>
				    </ul>
				</c:if>
				
			</c:forEach>
			
	      	<ul class="nav navbar-nav navbar-right">
	        	<li class="dropdown">
	          		<a href="" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
	          			Profil <span class="caret"></span>
	          		</a>
	          		
	          		<ul class="dropdown-menu">
		            	<li><a href="${pageContext.request.contextPath}/userInfo">
		            		<span><i class="glyphicon glyphicon-user"></i> Ayarlar</span>
		            		</a>
		            	</li>
		            	<li role="separator" class="divider"></li>
						<li><a href="${pageContext.request.contextPath}/logout">
							<span><i class="glyphicon glyphicon-log-out"></i> Çıkış yap</span>
							</a>
						</li>
	          		</ul>
	        	</li>
	      	</ul>
	     </div>
	    </c:if>	 
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>