<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%!String domainName = "http://www.chaikhokha.in";%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1" />
<meta name="description" content="" />
<meta name="author" content="" />
<!--[if IE]> <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> <![endif]-->
<title>RequestReport</title>
<!-- BOOTSTRAP CORE STYLE  -->

<link href="css/bootstrap.css" rel="stylesheet" />
<!-- FONT AWESOME ICONS  -->
<link href="css/font-awesome.css" rel="stylesheet" />
<!-- CUSTOM STYLE  -->
<link href="css/style.css" rel="stylesheet" />
<link href="css/dataTables.bootstrap.css" rel="stylesheet" type="text/css">
<link href="css/dataTables.responsive.css" rel="stylesheet" type="text/css">


<!-- FONT AWESOME ICONS  -->
<!-- HTML5 Shiv and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<style>
a:link {
	color: red;
	text-decoration: none;
}

a:hover {
	color: blue;
}

a:visited {
	color: green;
}
</style>
<script src="js/jquery.min.js"></script>
<script src="js/jquery.dataTables.min.js"></script>
<script src="js/dataTables.bootstrap.min.js"></script>
<script src="js/dataTables.responsive.js"></script>
<script src="js/bootstrap.js"></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">

<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="js/jquery.tabletoCSV.js" type="text/javascript" charset="utf-8"></script>
<script src="js/report.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">var contextPath= "";</script>
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
							<li><a href="moveToCreateApp" >Manage Apps</a></li>

                            <li><a href="managePartner" >Manage Partner</a></li>
							<li><a  href="addPartnerIp">Map App-Ip</a></li>

                            <li><a href="createUser" >Partner User</a></li>
                            <li><a href="requestReport"  class="menu-top-active">Report</a></li>
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
				<form action="javascript:getConversionOverview()">
					<div class="col-md-3">
						<label>From </label> <input type="text" id="fromDatepicker"
							class="form-control" readonly="readonly">
					</div>

					<div class="col-md-3">
						<label>To </label> <input type="text" id="toDatepicker"
							class="form-control" readonly="readonly">
					</div>
					<div class="col-md-2">
						<label>Partners</label> <select id="partnerId"
							class="form-control">
							<option value="0">Select All</option>

						</select>
					</div>
					<div class="col-md-2">
						<label>Application </label> <select id="appId"
							class="form-control">
							<option value="0">Select All</option>

						</select>
					</div>
					<div class="col-md-2" style="margin-top: 35px;">
						<button type="submit" id="search" value="Search"
							class="btn btn-info" style="border-radius: 5px">Search</button>
					</div>
				</form>
			</div>
			<div class="row">
				<div class="col-md-12">
					<h1 class="page-head-line">Reports</h1>
				</div>
			</div>
			
			<div class="row">
				<!-- <div class="col-md-1"></div>
 -->				<div class="col-md-12">
					<!--    Hover Rows  -->
					<div class="panel panel-default">
						<div class="panel-heading">
							<div class="row">
								<div class="col-md-3"><h4>Conversion Data Report</h4></div>
								<div class="col-md-6">&nbsp;</div>
								<div class="col-md-3"><button type="submit" id="download" value="Download CSV" class="btn btn-info">Download Report(CSV format)</button></div>
							</div>
						</div>
						<div class="panel-body">
							<div class="table-responsive">
								<table class="table table-striped table-hover" id="filterReportTable">
									<thead>
										<tr>
											<!-- <th>#</th> -->
											<th>Partner</th>
											<th align="center">Application Name</th>
											<!-- <th align="center"> IP</th> -->
											<!-- <th># Key</th> -->
											<th align="center"># of conversions</th>
										</tr>
									</thead>
									<tbody id="filterReportTableBody">
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

	<!-- Modal -->
	<div id="conversionDetailModal" class="modal fade" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Application Hit Details</h4>
				</div>
				<div class="modal-body">

					<div style="text-align:right"><button type="submit" id="downloadHits" value="Download CSV" class="btn btn-info">Download Report(CSV format)</button><br>&nbsp;</div>
					<div class="table-responsive">
						<table class="table table-striped table-bordered table-hover" id="hitsReportTable">
							<thead>
								<tr>
									<th>SN</th>
									<th>Conversion Id</th>
									<th>Conversion IP</th>
									<!-- <th>Requested URL</th> -->
									<th>Conversion Timestamp</th>
								</tr>
							</thead>
							<tbody id="conversionReportTabbleBody" style="overflow-x: auto">
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>


</body>
<body>

</body>
</html>
