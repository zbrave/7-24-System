<%@ page pageEncoding="UTF-8" %>

<script type="text/javascript">
$(document).ready(function(){
	var path = window.location.pathname.split("/").pop();
	$('#' + path).addClass("active");
});
</script>

<!--  <nav class="navbar">
	<div class="menu">
	    <div class="container-fluid">
			<div class="navbar-header">
				<a href="${pageContext.request.contextPath}">IYS</a>
			</div>
			<div>
				<ul class="nav navbar-nav navbar-right" id="nav">
					<li id="home"><a href="${pageContext.request.contextPath}/welcome" ><span class="glyphicon glyphicon-user"></span> Anasayfa</a></li>
					
					<c:if test="${pageContext.request.userPrincipal.name != null}">
						<li id ="userInfo"><a href="${pageContext.request.contextPath}/userInfo"><span class="glyphicon glyphicon-log-in"></span> UserInfo</a></li>
						<li id="admin"><a href="${pageContext.request.contextPath}/admin" ><span class="glyphicon glyphicon-user"></span> Admin</a></li>
						<c:forEach var="role"
					items="${pageContext['request'].userPrincipal.principal.authorities}">
						<c:if test="${role.authority == 'ROLE_SUPER_ADMIN' }">
							<li class="dropdown">
								<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">CRUD <span class="caret"></span></a>
									<ul class="dropdown-menu">
										<li><a href="${pageContext.request.contextPath}/uniList">Üniversiteler</a></li>
										<li><a href="${pageContext.request.contextPath}/deptList">Bölümler</a></li>
										<li><a href="${pageContext.request.contextPath}/takingLessonList">Alınan Dersler</a></li>
										<li><a href="${pageContext.request.contextPath}/substituteLessonList">Sayılan Dersler</a></li>
									</ul>
							</li>
						</c:if>
						</c:forEach>
						<li><a href="${pageContext.request.contextPath}/logout"><span class="glyphicon glyphicon-log-in"></span> Çıkış</a></li>
					</c:if>
					
				</ul>
			</div>
		</div>
	</div>
</nav>
-->
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
				
				<c:if test="${role.authority == 'ROLE_SUPER_ADMIN'}">
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
				
				<c:if test="${role.authority == 'ROLE_ADMIN'}">
					<ul class="nav navbar-nav">
						<li><a href="${pageContext.request.contextPath}/admin" >Admin page</a></li>
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