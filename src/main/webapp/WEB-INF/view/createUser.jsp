<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1" />
<meta name="description" content="" />
<meta name="author" content="" />

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
							<li><a href="addPartnerIp">Map App-Ip</a></li>
							<li><a href="createUser" class="menu-top-active">Partner
									User</a></li>
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
					<h1 class="page-head-line">Partner User</h1>
				</div>
			</div>
			<div class="row">
				<div class="col-md-2"></div>
				<div class="col-md-8">
					<div class="panel panel-default">
						<div class="panel-heading">
							<div class="row">
								<div class="col-md-10">
								<b> Partner User Details </b></div>
								
								<div class="col-md-2">
								 <a href="newUserCreation" class="btn btn-success">Create-User</a></div>
								</div>
							</div>
						
						<div class="panel-body">
							<form>

								<div class="form-group">
								<div id="msg" style="display: none;"></div>
									<p style="padding-bottom: 10px;">
									
									<table style="width: 100%;">
										<tr>
											<td align="left"><b>Partner Name</b></td>
										
											<td align="right"></td>
										</tr>
									</table>
									</p>

									<select class="form-control" id="partnerName" onchange="showUserDiv(value);">
										<option value="-1">Please Select Partner Name</option>
										<c:forEach items="${PartnerList}" var="PartnerList">
										<option value="${PartnerList.partnerId}">${PartnerList.partnerName}
											</option>
										</c:forEach>
									</select>
									<c:forEach items="${PartnerList}" var="PartnerState">
									<input type="hidden" id="${PartnerState.partnerId}" value="${PartnerState.status}"/>
									</c:forEach>
								</div>

								<hr>

								<!-- User Detains -->
								<div id="userDiv" style="display: none;">
									 <div class="form-group">
								  <label>Partner Name </label>
								  		<p align="left" id="partnerNamePara" ><b>Partner Name</b></p>							
								  </div>
								  <div class="form-group">
								  <label> Current Status :  <i id="partnerStatusPara" ><b>Partner Status</b></i>	 </label>
								  								
								  </div>
									

									<div class="row">

										<div class="col-md-12">
											<!--    Hover Rows  -->
											<div class="panel panel-default">
												<div class="panel-heading">User Details </div>
												<div class="panel-body">
													<div class="table-responsive">
														<table class="table table-hover">
															<thead>
																<tr>
																	<th>#</th>
																	<th>Users Name</th>
																	<th>Email</th>
																	<th>Current-Status</th>
																	<th>Action</th>
																</tr>
															</thead>
															<tbody id="userListTbody">
															</tbody>
														</table>
													</div>
												</div>
											</div>
											<!-- End  Hover Rows  -->
										</div>

									</div>
								</div>

							</form>
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
					<div class="row">

						<div class="col-md-12">
						<form action="">
							<div class="form-group">

								<input type="hidden" id="userIdmodel" class="form-control" required="required"  />
							</div>

							<div class="form-group">
								<label><b> User Email</b></label> <input type="email"
									id="emailmodel" class="form-control" disabled="disabled" />
							</div>
							<div class="form-group">
								<label><b> First Name</b></label> <input type="text"
									id="fnamemodel" class="form-control" required="required" maxlength="255"/>
							</div>
							<div class="form-group" >
								<label><b> Last Name</b></label> <input type="text"
									id="lnamemodel" class="form-control" required="required" maxlength="255"/>
							</div>
							<div class="form-group" >
							<label style="margin-left: 0px;" class="radio-inline help-block"><input  type="checkbox" 
							 id="createPassword" name="parterType" ><b>&nbsp;&nbsp;Create Your Own Password</b></label>
							</div>
							<div class="form-group" id="passwordDiv" style="display: none">
								<label><b> Create Password</b></label> <input type="text"
									id="passwordmodel" class="form-control" required="required"/>
							</div>
							<p align="right" class="help-block">press save button to save
								information</p>
							<p align="right">
								<!-- <button type="reset" class="btn btn-danger"> &nbsp;Reset</button>&nbsp; --> <a
									href="#" class="btn btn-success" onclick="submitUserInfo()"><span
									class="glyphicon glyphicon-user"></span>Save </a>&nbsp;
							</p>
						</form>
						</div>

					</div>
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
	<!-- CLAYMOTION CUSTOM JAVASCRIPT -->

	<script>
	$(function(){

		$("#createPassword").click(function(){
				$("#passwordDiv").toggle(1000);
			});		
	});

	
		function showUserDiv(val) {

			var pname;
			if (val !== "-1") {
				var pid = $("#partnerName").val();

				$.ajax({
							type : "GET",
							url : "getUser/" + pid,
							success : function(data) {
								pname = $("#partnerName option:selected")
										.text();
								document.getElementById("partnerNamePara").innerHTML = "";
								document.getElementById("partnerNamePara").innerHTML = ""
										+ pname;
								var statusName=document.getElementById(val).value;
								document.getElementById("partnerStatusPara").innerHTML = "";
								document.getElementById("partnerStatusPara").innerHTML = ""
										+ statusName;
									
								$("#userListTbody").empty();

								if (data.length > 0) {

									for (var i = 0; i < data.length; i++) {

										$("#userListTbody")
												.append(
														"<tr>" + "<td>"
																+ (i + 1)
																+ "</td>"
																+ "<td>"
																+ data[i].fname
																+ " "
																+ data[i].lname
																+ "</td>"
																+ "<td>"
																+ data[i].userEmail
																+ "</td>"
																+ "<td>"
																+ (data[i].statusId == 2 ? "Pause"
																		: "Activate")
																+ "</td>"
																+ "<td>"
																+ (data[i].statusId == 1 ? "<button type='button' style='width:70px;' onclick=statusChange("
																		+ data[i].userId
																		+ ","
																		+ data[i].statusId
																		+ ") class='btn btn-danger'>Pause</button>"
																		: "<button type='button' class='btn btn-success' style='width:70px;' onclick=statusChange("
																				+ data[i].userId
																				+ ","
																				+ data[i].statusId
																				+ ")>Activate</button>")
																+ "<button type='button'  style='margin-left:5px; width:70px;' class='btn btn-warning' onclick=editUserInfo("
																+ data[i].userId
																+ ",'"
																+ data[i].fname
																+ "','"
																+ data[i].lname
																+ "','"
																+ data[i].userEmail
																+ "')> Edit</button>"
																+ "</td>"
																+ "</tr>");
									}

								} else {
									$("#userListTbody")
											.append(
													"<tr> <td colspan='5' align='center' style='color:red;font-size:18px;'> Data Not Found</td><tr>");

								}

							}

						});
				$("#userDiv").show(1000);

			}
			if (val === "-1") {
				$("#userDiv").hide(1000);
			}
		}
		function statusChange(userId, statusId) {
			var value = document.getElementById("partnerName").value;
			$.ajax({
				type : "GET",
				url : "updateStatus/" + statusId + "/" + userId,
				success : function(data) {
					showUserDiv(value);
				}
			});
		}

		function editUserInfo(userId, fname, lname, userEmail) {

			$('#userIdmodel').val(userId);
			$('#emailmodel').val(userEmail);
			$('#fnamemodel').val(fname);
			$('#lnamemodel').val(lname);
			$('#createPassword').attr('checked', false); 
			$('#passwordmodel').val("");
			$("#passwordDiv").hide();
			$('#myModal').modal('toggle');
		}
		function validateForm(){
			var fname = $('#fnamemodel').val().trim();
			var lname = $('#lnamemodel').val().trim();
					
				 if((fname!="") && (lname!="" )){
					 
						return true;
						}
					else{
						return false;
						}
			}
		
		function submitUserInfo() {
			var value = document.getElementById("partnerName").value;
			
			var userData = {};
			userData.userId = $('#userIdmodel').val();
			userData.userEmail = $('#emailmodel').val().trim();
			userData.fname = $('#fnamemodel').val().trim();
			userData.lname = $('#lnamemodel').val().trim();
			userData.password = $('#passwordmodel').val().trim();
			if(validateForm()){
					$.ajax({
						contentType : "application/json",
						type : "POST",
						url : "editUser",
						data : JSON.stringify(userData),
						success : function(data) {
							$('#myModal').modal('hide');
							if (data) {
								$('#msg')
										.html(
												"<div class='alert alert-success fade in'>"
														+ "<a href='#' class='close' data-dismiss='alert'>&times;</a>"
														+ "<strong>Success!</strong> User Info Updated successfully."
														+ "</div>");
								$('#msg').show();
							} else {
								$('#msg')
										.html(
												"<div class='alert alert-danger fade in'>"
														+ "<a href='#' class='close' data-dismiss='alert'>&times;</a>"
														+ "<strong>Error!</strong> A problem has been occurred while submitting your data."
														+ "</div>");
								$('#msg').show();
							}
							showUserDiv(value);
						},
						error : function(e) {
							alert('Error In Processing ');
						}
					});
			}
			else{
				alert("Please Provide Correct Details");
				}

		}
	</script>

</body>
</html>
