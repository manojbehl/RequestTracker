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
    
  <!-- Custom Script -->
 
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
                             <li><a href="partnerDashboard" class="menu-top-active">Dashboard</a></li>
                             <li><a href="partnerReport" >Report</a></li>
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
                        <h1 class="page-head-line">Welcome  : &nbsp;${userSessionObject.fname}</h1>
                    </div>
                    
                </div>
               
                <div class="row">
				<div class="col-md-1"></div>
                <div class="col-md-10">
                     <!--    Hover Rows  -->
                    <div class="panel panel-default">
                        <div class="panel-heading">
                          Active Application  Details 
                        </div>
                        <div class="panel-body">
                            <div class="table-responsive">
                                <table class="table table-hover">
                                    <thead>
                                        <tr>
                                            <th>#</th>
                                            <th> Applications </th>
                                            <th>IP-Addresses</th>
                                            <th>App-Url</th>
											<!--<th>Current-Status</th>
											 <th>Action</th> -->
                                        </tr>
                                    </thead>
                                    <tbody id="partnerDetailsTbody">
                                    <c:forEach items="${PartnerAppIpInfo}" var="PartnerAppIpInfo" varStatus="SNo">
                                        <tr>
                                            <td>${SNo.count}</td>
                                            <td>${PartnerAppIpInfo.app_Name}</td>
                                            <td>${PartnerAppIpInfo.appIps}</td>
                                            <td>http://trackback.freeapps.link/sniff/${PartnerAppIpInfo.appKey}</td>
											<%--<td>${PartnerAppIpInfo.status}</td>
											 <td>
											<c:if test="${PartnerAppIpInfo.viewStatus=='Active'}"><button type="button" class="btn btn-success" > &nbsp;${PartnerAppIpInfo.viewStatus}</button> </c:if>
                                            <c:if test="${PartnerAppIpInfo.viewStatus=='Pause'}"><button type="button" class="btn btn-danger" > &nbsp;${PartnerAppIpInfo.viewStatus}</button></c:if>
                                            </td> --%>
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
 
     
</body>
</html>
