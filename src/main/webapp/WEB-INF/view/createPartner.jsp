<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
    <meta name="description" content="" />
    <meta name="author" content="" />
    <!--[if IE]>
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <![endif]-->
        <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>
    <title>Claymotion</title>
    <jsp:include page="resources.jsp"></jsp:include>
  <style>
.tDiv {
  float: left;
  width: 49%;
		}
table{
  height: 30px;
  width:auto;
	}
  #applicationDropDown, #selectedAppsDropDown { list-style-type: none; margin: 0; float: left; margin-right: 10px; background: #eee; padding: 5px; width: 143px;}
  #applicationDropDown tr, #selectedAppsDropDown tr { margin: 5px; padding: 5px; font-size: 1.2em; width: 120px; }
  </style>
 
  
</head>
<body>
 
    <header>
        <div class="container">
            <div class="row">
                <div class="col-md-12">
                    <strong>Email: </strong>support@picsaxis.com
                    &nbsp;&nbsp;
                    <strong>Support: </strong>+91 9717477123
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
							<li><a href="moveToCreateApp" >Manage Apps</a></li>
                            <li><a href="managePartner" class="menu-top-active">Manage Partner</a></li>
							<li><a  href="addPartnerIp">Map App-Ip</a></li>
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
                        <h1 class="page-head-line">Partner</h1>
                    </div>
                </div>
			<div class="row">
				<div class="col-lg-2"></div>
				<div class="col-lg-8">
				<p align="center" style="color:red;"><b>${Errmsg}</b></p>
				<p align="center" style="margin-bottom: 20px; color:green; "><b>${msg}</b></p>
					<div class="form-group">
						<label>Select Action........ &nbsp;&nbsp;&nbsp;&nbsp; </label>
						<label class="radio-inline"><input type="radio" checked="checked" id="newpartnerType" name="parterType" ><b>Create New Partner</b></label>
						<label class="radio-inline"><input type="radio" id="oldpartnerType" name="parterType"><b>Update Existing Partners </b></label>
					</div>
				</div>
				<div class="col-lg-2"></div>
			</div>
				<hr>
				<!-------------------------------- EXISTING PARTNER UPDATE SECTION	------------------------------------>
				
                <div class="row" id="oldpartnerDiv" style="display:none;">
				<div class="col-md-2"></div>
                    <div class="col-md-8">
							<div class="panel panel-default">
								<div class="panel-heading"><h4>
								  <b> Insert Partner Details</b></h4>
								</div>
								<div class="panel-body">
							   <%-- <form action="" method="get"> --%>
								  <div class="form-group">
									<label><b> Partner Name</b></label>
									<select name="indtype" class="form-control" id="partnerDropDown" onchange="showoldPartnerDiv(value)">
										<option selected="selected" value="-1">Select Active Partner</option>
										<c:forEach items="${partnerList}" var="partnerList" >
                                        <option value='${partnerList.partnerId }'>${partnerList.partnerName} </option>
                                        </c:forEach> 
										
									</select>
								  </div>
								  <hr>
								  <div id="oldpartnerDivSection" style="display:none;">
									 
								  <div class="form-group">
								  <label>Partner Name</label>
								  		<p align="left" id="partnerName"><b>Partner Name</b></p>							
								  </div>
								  <div class="form-group">
								  
									<label >Partner Logo</label>
									<!-- <input type='file' onchange="readURL(this);"/> --> <img style="display:none; width:130px; height:150px" id="photo" src="#" alt="your image"/>
									
								  </div>
								 
								  <div class="form-group">
									
									<div class="col-md-6 tDiv" ><p align="left" class="help-block" style="color: red">Drag Required Application</p>
												<table> <tr><th >&nbsp;&nbsp;Available Application </th></tr>
												<tbody id="applicationDropDown" class="droptrue">
													  </tbody>
											  </table>
									</div>
									<form:form action="partnerUpdate" modelAttribute="partner" method="post">
									<div class="col-md-6 tDiv">
									<p align="left" class="help-block" style="color: red">Drag Here Required Application</p>
									<input type="hidden" id="partnerId" value="#" name="partnerId" />
									<table><tr><th><input type=hidden value="P1">Selected Apps</th></tr>
												<tbody id="selectedAppsDropDown" class="droptrue">	
												<tbody>
											 </table>
									</div>
									
									<p align="right" class="help-block">press save button to save information</p>
									<p align="right"><button type="reset" onclick="reloadExistingPartnerSection()" class="btn btn-danger" > &nbsp;Undo</button>&nbsp;
									<button type="submit" class="btn btn-success"><span class="glyphicon glyphicon-user"></span> &nbsp;Save</button>&nbsp;</p>
										<hr />
									</form:form>
								  </div>
								</div>			   
							  
								</div>
                            </div>
                    </div>
                   <div class="col-md-2"></div> 
                </div>
                
				<!------------------------------------------ New partner registration --------------------------------->
				
				<div class="row" id="newpartnerDiv" >
				<div class="col-md-2"></div>
                    <div class="col-md-8">
							<div class="panel panel-default">
								<div class="panel-heading"><h4>
								  <b> Insert Partner Details</b></h4>
								</div>
								<div class="panel-body" ng-app="">
								  <div class="form-group">
									<label><b> Partner Name</b><span id=partnerNameError style="color:red"></span></label>
									<input type="text" id="newPartnerName" maxlength="225" ng-model="name" class="form-control" required="required" placeholder="Enter Partner Name" onfocusout="isPartnerNameDuplicate()" autofocus="autofocus" />
								  </div>
								   <div class="form-group">
								   
								  
									<div class="col-md-6 tDiv" > 
									<img  id="photo1" src="img/required.png" alt="Partner image" style="width: 160px; height: 150px"/>
									<br><p align="left" class="help-block" style="color: red">Drag Required Application</p>
												<table > <tr><th>&nbsp;&nbsp;Available Applications </th></tr>
												<tbody id="applicationDropDown" class="droptrue">
													<c:forEach items="${appList}" var="app">
                                  			    		 <tr>
			                                         		  <td ><input type=hidden name="appIdAray" value="${app.appId}">${app.app_Name}</td>
														</tr>
			                                        </c:forEach>
                              					</tbody>
											    </table>
									</div>
									
									<div class="row">
									
									<form:form action="partner"  modelAttribute="partner" method="post" enctype="multipart/form-data">
									<div class="col-md-6 tDiv">
									<div class="form-group">
									<div class="row" style=" height: 135px;">
										<label>Partner Logo</label>
										<p style="margin-top: 20px"><input type='file' name="partnerLogo" id="fUpload" onchange="newUserLogo(this);" required="required"/></p> 
										
									</div>
									</div>
									<div class="row">
									<div class="col-md-12">
									<p align="left" class="help-block" style="color: red">Drag Here Required Application</p>
								  	<table>	<tr> <th ><input type=hidden id="newPartnerName1" value={{name}} name="partnerName"  required/>&nbsp;&nbsp;Selected Apps</th></tr>
												<tbody id="selectedAppsDropDown" class="droptrue">	
												
												<tbody>
										</table>
										</div>
									</div>
								  </div>
									<p align="right" class="help-block" >press save button to save information</p>
									<p align="right"><button type="button" class="btn btn-danger" onclick="winReload()"> &nbsp;Clear</button>&nbsp;
									<button class="btn btn-success" type="submit"><span class="glyphicon glyphicon-user"></span> &nbsp;Save </button>&nbsp;</p>
									</form:form>
									
									</div>
									</div>
										<hr />
												   
							  
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
					<h4 style="color: #ff6666;">Partner Already Exists</h4>
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
   
	<script>
	
		function readURL(input) {
			
			$("#photo").show();
            if (input.files && input.files[0]) {
                var reader = new FileReader();

                reader.onload = function (e) {
                    $('#photo')
                        .attr('src', e.target.result)
                        .width(160)
                        .height(150);
                };

                reader.readAsDataURL(input.files[0]);
            }
            
        }
		function newUserLogo(input) {
			var fileInput =  document.getElementById("fUpload");
            var sizeOfFile=null
             sizeOfFile =(fileInput.files[0].size)/1024;
             if(sizeOfFile <= 60){
			$("#photo1").show();
            if (input.files && input.files[0]) {
                var reader = new FileReader();

                reader.onload = function (e) {
                    $('#photo1')
                        .attr('src', e.target.result)
                        .width(160)
                        .height(150);
                };

                reader.readAsDataURL(input.files[0]);
            } }else{
           	 alert(sizeOfFile+" Kb   size big");
             $('#fUpload').val("");
             $("#photo1").hide();
         }
        }
	 
		$(function(){

			$("#newpartnerType").click(function(){
					$("#oldpartnerDiv").hide(500);
					$("#newpartnerDiv").show(1000);
					
				});
				$("#oldpartnerType").click(function(){
					$("#newpartnerDiv").hide(500);
					$("#oldpartnerDiv").show(1000);
					
				});
					
		});
		
		function showoldPartnerDiv(val){
			document.getElementById("partnerId").value=val;
		
		/*  $('#photo')
        .attr('src', "api/image/"+val)
        .width(160)
        .height(150).
        attr("style","display:block");  */
        $('#photo')
        .attr('src', "api/image/"+val)
        .attr("style","display:block ;height:160px; width:150px;");
        /* document.getElementById("photo").style.height =  parseInt(160) ;
        document.getElementById("photo").style.width =  parseInt(150) ; */    
        //readUrl()
				
		var pname;
			if(val!== "-1")
			{
				pname=$("#partnerDropDown option:selected" ).text(); 
				document.getElementById("partnerName").innerHTML = "";
				document.getElementById("partnerName").innerHTML = ""+pname;
					$("#oldpartnerDivSection").show(1000);
					
					/*  Calling partner Apps  */
					
					$.ajax({
						url : 'partnerApps/'+val,
						success : function(data) {

							var partnerAppCounter = data.myApps.length;
							if (partnerAppCounter > 0) {
								var tableBody = $("#selectedAppsDropDown");
								tableBody.empty();
								for (var i = 0; i < partnerAppCounter; i++) {
									tableBody.append("<tr><td><input type=hidden name='appIdAray' value="+data.myApps[i].appId +">"+data.myApps[i].app_Name+"</td></tr>");
								}
							}
						else {

							var tableBody = $("#selectedAppsDropDown");
							tableBody.empty();
							//tableBody.append("<p><b> Not Found </b></p>");
						}
					/* ---------------------------------Updating the Rest Application List ---------------------------------*/
						var partnerAppCounter = data.restAppList.length;
							if (partnerAppCounter > 0) {
								var tableBody = $("#applicationDropDown");
								tableBody.empty();
								for (var i = 0; i < partnerAppCounter; i++) {
									tableBody.append("<tr><td><input type=hidden name='appIdAray' value="+data.restAppList[i].appId +">"+data.restAppList[i].app_Name+"</td></tr>");
								}
							}
						else {

							var tableBody = $("#applicationDropDown");
							tableBody.empty();
							//tableBody.append("<p><b> Not Found </b></p>");
						}
						}

					});
					
			}
			if(val=== "-1")
			{
					$("#oldpartnerDivSection").hide(1000);
			}
		}
		function submittPartnerDetails()
		{
			var	partner	=	{};
			alert(document.getElementsByName("app").value);
			var tbodyContent	=	document.getElementsByName("app").value;
			///alert("The tbodyContent is : "+tbodyContent);
		}
		
		function isPartnerNameDuplicate()
		{
			var partnerName =document.getElementById("newPartnerName").value;
			if(partnerName	!=	null){
				//alert("the partner name is : "+partnerName);
				
				$.ajax({
						url : 'partner/name/'+partnerName,
						success : function(data) {
							if(data	==	true){

								 $('#newPartnerName').val("");
								 $('#newPartnerName1').val("");
								 $("#myModal").modal('toggle');
							}
							else{
								document.getElementById("partnerNameError").innerHTML="";
							}
						}
						}); 
				}
		}
		function winReload()
		{
			location.reload(true);
		}

		function reloadExistingPartnerSection()
		{
			var andUSE=document.getElementById("partnerDropDown").value;
			showoldPartnerDiv(andUSE);
		}
	 
		</script> 
		
		
	 <!-- MULTI SELECT -->
<script>
  $(function() {
    $( "tbody.droptrue" ).sortable({
      connectWith: "tbody"
    });
  } );
  </script>
	
</body>
</html>
