<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1" />
<meta name="description" content="" />
<meta name="author" content="" />
<!--[if IE]>
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <![endif]-->
<title>Claymotion</title>
<jsp:include page="resources.jsp"></jsp:include>

</head>
<body>
	<header>
		<div class="container">
			<div class="row">
				<div class="col-md-12">
					<strong>Email: </strong>support@picsaxis.com &nbsp;&nbsp; <strong>Support:
					</strong>+91 9717477123
				</div>

			</div>
		</div>
	</header>

	<section class="menu-section">
		<div class="container">
			<div class="row">
				<div class="col-md-12">
					<div class="navbar-collapse collapse ">
						<ul id="menu-top" class="nav navbar-nav navbar-right">
							<li><a href="getPartnerList">Dashboard</a></li>
							<li><a href="moveToCreateApp" class="menu-top-active">Manage
									Apps</a></li>
							<li><a href="managePartner">Manage Partner</a></li>
							<li><a href="addPartnerIp">Map App-Ip</a></li>
							<li><a href="createUser">Partner User</a></li>
							<li><a href="requestReport" >Report</a></li>
							<li><a href="logout">Logout</a></li>
						</ul>
						
					</div>
				</div>

			</div>
		</div>
	</section>
	<!-- MENU SECTION END-->
	<div class="content-wrapper">
		<div class="container">
			<div class="row">
				<div class="col-md-12">
					<h1 class="page-head-line">Application</h1>
				</div>
			</div>
			<div class="row">
				<div class="col-md-2"></div>
				<div class="col-md-8">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4>
								<b> Create New Application </b>
							</h4>
						</div>
						<div class="panel-body">
				<c:choose>
	             		<c:when test="${msg!=null}">
	             		<p align="center"><label style="color: green" >${msg}</label></p>
	             		</c:when> 
	              	<c:when test="${Errmsg!=null}">
	              	<p align="center"><label style="color: red" >${Errmsg}</label></p>
	              	</c:when>
              	</c:choose>	
                 			 <form:form  action="createApp" modelAttribute="application" method="post" enctype="multipart/form-data">
							   <!-- Application Detains -->
								 
								  <div class="form-group">
									<label><b> Application Name</b></label>
									<input type="text" name="app_Name" id="appName" class="form-control" required placeholder="Enter Application Name" maxlength="225"  onblur="checkAppNmae(this)"/>
								  </div>
								  
								  <div class="form-group"><p align="left" class="help-block" style="color:red;">Logo Size should be less then 200-KB</p>
									<label >Application Logo </label>
									<input type='file' required name="appLogo"  onchange="readURL(this)" id="fUpload" /> <img style="display:none;" id="photo" src="#" alt="your image"/>
									
								  </div>
									<div class="form-group">
										<label><b> URL </b></label>
										<input type="text" name="appURL" class="form-control" required maxlength="225" placeholder="Enter Application URL" />
									</div>
								  
									<p align="right" class="help-block">press save button to save information</p>
									<p align="right"><button type="reset" class="btn btn-danger" >&nbsp;Clear</button>&nbsp;
									<button type="submit" class="btn btn-success" ><span class="glyphicon glyphicon-user"></span> &nbsp;Save </button>&nbsp;</p>
										<hr />
							</form:form>
						</div>
					</div>
				</div>
				<div class="col-md-2"></div>
			</div>
		</div>
	</div>
	<!-- CONTENT-WRAPPER SECTION END-->
	<!-- Modal contant -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true"
		style="display: none;">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h4 class="modal-title" id="myModalLabel">Claymotion</h4>
				</div>
				<div class="modal-body">
					<h4 style="color: #ff6666;">Application Name Already Exists <br>   Please Retry With 
					Another Name......... </h4>
					<hr>

				</div>

				<div class="modal-footer"></div>
			</div>
		</div>
	</div>
	<!-- Modal contant End -->
	<footer>
		<div class="container">
			<div class="row">
				<div class="col-md-12">
					&copy; | By : <a href="http://www.picsaxis.com" target="_blank">Claymotion</a>
				</div>

			</div>
		</div>
	</footer>
	<!-- FOOTER SECTION END-->

								
									 				  
							  
	
   

	<script>
		function readURL(input)
		 {
			 var fileInput =  document.getElementById("fUpload");
			var sizeOfFile=null
			 sizeOfFile =(fileInput.files[0].size)/1024;
			 if(sizeOfFile <= 201){
				
			 
			$("#photo").show();
            if (input.files && input.files[0]) {
                var reader = new FileReader();
                reader.onload = function (e) {
                    $('#photo')
                        .attr('src', e.target.result)
                        .width(130)
                        .height(150);
                };
                reader.readAsDataURL(input.files[0]);
            }
			 }
			 else{
				 alert(sizeOfFile+" Kb   size big");
				 $('#fUpload').val("");
				 $("#photo").hide();
				 }
		 }

		 function checkAppNmae(AppName){
			 if(AppName.value.trim()==""){
				 $('#appName').val(""); 
				 }
				if(AppName.value.trim()!=""){
				$.ajax({url: "checkAppName/"+AppName.value+"",
	         		success: function(data){
							if (data){	
								
								}
							else {
								$('#appName').val("");
								$("#myModal").modal('toggle');
							}
		         		}});
			}
			
		 }

	</script>
</body>
</html>
