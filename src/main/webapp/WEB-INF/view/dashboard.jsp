<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
    <meta name="description" content="" />
    <meta name="author" content="" />
    <!--[if IE]>
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <![endif]-->
    <title>Claymotion</title>
    <jsp:include page="resources.jsp"></jsp:include>
    <style>
     a:hover{
         color: blue;
         text-decoration: none;
     }
    </style>
 
</head>
<body>
    <header>
        <div class="container">
            <div class="row">
                <div class="col-md-12">
                 
                    <strong>Email: </strong>adops@claymotion.in
                    &nbsp;&nbsp;
                    <strong>Support: </strong>+919818900229
                    &nbsp;&nbsp;
                    <strong>Copyright: </strong>http://claymotion.in
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

                             <li><a href="getPartnerList" class="menu-top-active">Dashboard</a></li>
							<li><a href="moveToCreateApp" >Manage Apps</a></li>

                            <li><a href="managePartner" >Manage Partner</a></li>
							<li><a  href="addPartnerIp">Map App-Ip</a></li>

                            <li><a href="createUser" >Partner User</a></li>
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
                        <h1 class="page-head-line">Welcome .. &nbsp;${userSessionObject.fname}</h1>
                    </div>
                    
                </div>
               
                <div class="row">
				<div class="col-md-1"></div>
                <div class="col-md-10">
                     <!--    Hover Rows  -->
                    <div class="panel panel-default">
                        <div class="panel-heading">
                           Partner Details
                        </div>
                        <div class="panel-body">
                            <div class="table-responsive">
                                <table class="table table-hover">
                                    <thead>
                                        <tr>
                                            <th>#</th>
                                            <th>Partner Name</th>
                                            <th># Users </th>
                                            <th># Applications</th>
											<th>Current-Status</th>
											<th>Action</th>
                                        </tr>
                                    </thead><tbody id="partnerDetailsTbody">
                                    <c:set var="SNo"  value="1"/>
                                    <c:forEach items="${PartnerList}" var="PartnerList" varStatus="SNo">
                                        <tr>
                                            <td>${SNo.count}</td>
                                            <td><c:choose>
                                           		<c:when test="${PartnerList.status=='ACTIVE'}">
                                           		<a  href="#"  onclick="partnerDetail(${PartnerList.partnerId})"><i class="glyphicon glyphicon-tasks"></i>&nbsp;${PartnerList.partnerName}</a>
                                           		</c:when> 
                                            	<c:otherwise>
                                            	<a  href="#" style="color:red;text-decoration:"><i class="glyphicon glyphicon-tasks"></i>&nbsp;${PartnerList.partnerName}</a>
                                            	</c:otherwise>
                                            </c:choose>
                                            </td>
                                            <td>${PartnerList.userCount}</td>
                                            <td>${PartnerList.appCount}</td>
											<td>${PartnerList.status}</td>
											<td>
											<c:if test="${PartnerList.viewStatus=='Activate'}"> <button type="button" class="btn btn-success" style="width:75px;" onclick="changePartnerStatus(${PartnerList.partnerId},'${PartnerList.viewStatus}')"> &nbsp;${PartnerList.viewStatus}</button> </c:if>
                                            <c:if test="${PartnerList.viewStatus=='Pause'}"><button type="button" class="btn btn-danger" style="width:75px;"  onclick="changePartnerStatus(${PartnerList.partnerId},'${PartnerList.viewStatus}')"> &nbsp;${PartnerList.viewStatus}</button></c:if>
                                            </td>
                                        </tr>
                                       
                                     </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    <!-- End  Hover Rows  -->
                </div>
                <div class="col-md-1"></div>
            </div>

        </div>
    </div>
	<!-- Modal -->
<div id="myModal" class="modal fade" role="dialog">
	<div class="modal-dialog">
		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Partner Application Details</h4>
			</div>
			<div class="modal-body">
				
				
			<div class="table-responsive" id="searchResult">
				<table  class="table table-striped table-bordered table-hover"  >
					<thead>
						<tr>
							
							<th>Application Name</th>
							 <th>IP Address</th>
							<th>Current Status</th>
							<!-- <th>Action</th> -->
							
						</tr>
					</thead>
					<tbody id="AppDtl">
					          
						 <tr class="odd gradeX">
							<td> Wynk </td>
							<td>21.03.2017 </td>
							<td>Active</td>
                            <td><button  class="btn btn-info" > &nbsp;Pause</button></td>                                          
						</tr>
						
						
					</tbody>
				</table>

			</div>
			
			</div>
		</div>
	</div>
</div>
    <!-- CONTENT-WRAPPER SECTION END-->
    <footer>
        <div class="container">
            <div class="row">
                <div class="col-md-12">
                    &copy; | By : <a href="http://claymotion.in" target="_blank">Claymotion</a>
                </div>

            </div>
        </div>
    </footer>
    <!-- FOOTER SECTION END-->
    <!-- Custom Script -->
    <script>
    function partnerDetail(id){
     		$.ajax({url: "getPartnerAppIp/"+id+"",
         		success: function(data){
         			$("#AppDtl").empty();
         			
             		if(data.length >0){
                 		
         			 for (var i=0; i<data.length; i++) {
             			 
         				$("#AppDtl").append("<tr>"
        						+"<td>"+data[i].application+"</td>"
        						+"<td>"+data[i].ip+"</td>"
        						+"<td>"+data[i].status+"</td>"
        						/* +"<td>"+(data[i].viewStatus=='Pause'?"<button type='button' class='btn btn-danger'>"+data[i].viewStatus+"</button>":
        							"<button type='button' class='btn btn-success' >"+data[i].viewStatus+"</button>"
        						)+"</td>" */
        						+"</tr>");
         				} 
     	       			$("#myModal").modal('toggle'); 
         		 		}
             		else{
                 			alert(" Data Not Found ");
     					
                 		}
              		 }
            }); 
    };


    function changePartnerStatus( id,viewStatus){
        
    	$.ajax({url:"setPartnerStatus/"+id+"/"+viewStatus+"",
    		success: function(data){
     			$("#partnerDetailsTbody").empty();
     			
         		if(data.length >0){
             		
     			 for (var i=0; i<data.length; i++) {
         			 view=data[i].viewStatus;
     				$("#partnerDetailsTbody").append("<tr>"
    						+"<td>"+(i+1)+"</td>"
     						+"<td>"+(data[i].status=='ACTIVE'?"<a  href='#'  onclick='partnerDetail("+data[i].partnerId+")'><i class='glyphicon glyphicon-tasks'></i>&nbsp;"+data[i].partnerName+"</a>":
     							"<a  href='#' style='color:red;text-decoration:' ><i class='glyphicon glyphicon-tasks'></i>&nbsp;"+data[i].partnerName+"</a>"
    						)+"</td>"
    						+"<td>"+data[i].userCount+"</td>"
    						+"<td>"+data[i].appCount+"</td>"
    						+"<td>"+data[i].status+"</td>"
    						+"<td>"+(data[i].viewStatus=='Pause'?"<button type='button' class='btn btn-danger' style='width:75px;' onclick=changePartnerStatus("+data[i].partnerId+",'"+data[i].viewStatus+"')>"+data[i].viewStatus+"</button>":
    							"<button type='button' class='btn btn-success' style='width:75px;' onclick=changePartnerStatus("+data[i].partnerId+",'"+data[i].viewStatus+"')>"+data[i].viewStatus+"</button>"
    						)+"</td>"
    						+"</tr>");
     				} 
 	       			
     		 		}
         		else{
             			alert(" Data Not Found ");
 					
             		}
          		 }
        }); 
};
    </script>
 
</body>
</html>
