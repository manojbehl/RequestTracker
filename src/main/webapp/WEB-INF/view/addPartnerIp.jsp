<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
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
							<li><a href="moveToCreateApp">Manage Apps</a></li>
							<li><a href="managePartner">Manage Partner</a></li>
							<li><a class="menu-top-active" href="addPartnerIp">Map App-Ip</a></li>
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
					<h1 class="page-head-line">Partner Ip</h1>
				</div>
			</div>
			<div class="row">
				<div class="col-md-2"></div>
				<div class="col-md-8">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4>
								<b> Insert Partner Ip-Address Details</b> <br><p align="center" style="color:red">${Errmsg}</p>
								<p align="center" style="color:green">${msg}</p>
							</h4>
						</div>
						<div class="panel-body">

							<div class="form-group">
								<label><b> Partner Name</b></label> <select class="form-control"
									id="partnerName" onchange="showUserDiv(value);">
									<option value="-1">Please Select Partner Name</option>
									<c:forEach items="${PartnerList}" var="PartnerList">
										<option value="${PartnerList.partnerId }">${PartnerList.partnerName}
										</option>
									</c:forEach>
								</select>
							</div>
							<hr>
							<!-- User Detains -->

							<div id="userDiv" style="display: none;">
								<p align="center" id="partnerNam"
									style="font-size: 25px; color: #ff6666">
									<b>Partner Name</b>
								</p>
								<form method="post" action="mapAppIps">
									<input type="hidden" id="partnerId" name="partnerId" value="">
									<div class="form-group" id="PartnerApps"></div>

									<p align="right" class="help-block">press save button to
										save information</p>
									<p align="right">
										<button type="reset" class="btn btn-danger" onclick='javascript:resetIP();' >&nbsp;Reset</button>
										&nbsp;
										<button type="submit" class="btn btn-success">
											<span class="glyphicon glyphicon-user"></span> &nbsp;Save
										</button>
										&nbsp;
									</p>
								</form>
								<hr />
							</div>

						</div>
					</div>
				</div>
				<div class="col-md-2"></div>
			</div>
		</div>
	</div>
	<!-- CONTENT-WRAPPER SECTION END-->
	
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

	<!-- CLAYMOTION CUSTOM JAVASCRIPT -->
	<script>
		function showUserDiv(val){
			var pid=$("#partnerName").val();
			document.getElementById("partnerId").value= pid;
		var pname;
			if(val!== "-1")	{
				$.ajax({
					 type : "POST", 
				     url : "Applist/"+pid,
				     success : function(data){
					     $("#PartnerApps").empty();
					     console.log(data);
					     for(var count=0;count<data.length;count++)
						     {
						     $("#PartnerApps").append("<input type='hidden' name='appId' value='"+data[count].appId+"'/><label><b>"+data[count].app_Name+"</b>&nbsp;&nbsp;&nbsp;(<span style='color:red'>http://trackback.freeapps.link/sniff/"+data[count].appKey+"</span>)</label>"
						    		+ "<textarea class='form-control ipList' name='appIps'  rows='2'  placeholder='Insert IP-Addresses (IP-v4) with comma separated'  >"+data[count].appIps+"</textarea>");						     
						     }
				     		},
					 error : function(e) {
				      		 alert('Error In Processing ' ); 
				         } 
				     });
			     
				pname=$( "#prtName option:selected" ).text(); 
				document.getElementById("partnerNam").innerHTML = "";
				document.getElementById("partnerNam").innerHTML = ""+pname;
				$("#userDiv").show(1000);	
			}
			if(val=== "-1")	{
					$("#userDiv").hide(1000);
			}
		}
		function resetIP(){
			 var x = document.getElementByName("appIps");
			 var i;
			 for (i = 0; i < x.length; i++) {
			     	alert(x[i].value);
			         x[i].value = "";
			 }
			
			}
					var el=document.getElementById("copyButton");
				if(el)	{
					addEventListener("click", function() {
					copyToClipboard(document.getElementById("copyTarget"));
					});
				}

			
  </script>

</body>
</html>
